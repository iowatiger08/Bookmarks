package com.tigersndragons.docbookmarks.core;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;

import javax.sql.DataSource;

import com.tigersndragons.docbookmarks.service.impl.CustomUserDetailsService;
import liquibase.integration.spring.SpringLiquibase;

import org.apache.commons.dbcp.BasicDataSource;
import org.hibernate.SessionFactory;
import org.hibernate.cache.ehcache.SingletonEhCacheRegionFactory;
import com.tigersndragons.AuditTrailInterceptor;
import com.tigersndragons.docbookmarks.controller.HomeController;
import com.tigersndragons.docbookmarks.dao.DocBookmarksDAO;
import com.tigersndragons.docbookmarks.dao.impl.DocBookmarksDAOImpl;
import com.tigersndragons.docbookmarks.service.DocBookmarkService;
import com.tigersndragons.docbookmarks.service.DocShellService;
import com.tigersndragons.docbookmarks.service.LoginService;
import com.tigersndragons.docbookmarks.service.impl.DocBookmarkServiceImpl;
import com.tigersndragons.docbookmarks.service.impl.DocShellServiceImpl;
import com.tigersndragons.docbookmarks.service.impl.LoginServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jndi.JndiObjectFactoryBean;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


@EnableWebMvc
@Configuration
@EnableTransactionManagement
@ComponentScan("com.ipers.docbookmarks.*")
@Import({
	SecurityConfig.class,
	WebMVCConfiguration.class
})
public class CoreContextConfiguration {
	// implements TransactionManagementConfigurer {
	private static Logger logger = LoggerFactory.getLogger(CoreContextConfiguration.class);

	@Bean
	static public PropertySourcesPlaceholderConfigurer  propertyPlaceholderConfigurator() throws UnknownHostException{
		String hostName = InetAddress.getLocalHost().getHostName();
		Resource propertiesResource = new ClassPathResource("props/" + hostName + ".properties");
		logger.info("using properties file: {}", propertiesResource);
		
		PropertySourcesPlaceholderConfigurer  configurator = new PropertySourcesPlaceholderConfigurer();
		configurator.setLocations(new Resource[] {
				new ClassPathResource("props/default.properties"),
				propertiesResource
		});
		return configurator;
	}
	
	@Bean
	public BasicDataSource getDefaultDataSource(){
		BasicDataSource defaultds = new org.apache.commons.dbcp.BasicDataSource();
		
		defaultds.setDriverClassName("com.mysql.jdbc.Driver");
		defaultds.setPassword("D0cu5Er.");
		defaultds.setUrl("jdbc:mysql://localhost:3306/docbookmarks");
		defaultds.setUsername("docuser");//docuser//D0cu5Er.
		defaultds.setInitialSize(30);
		defaultds.setPoolPreparedStatements(true);
		return defaultds;
	}
	
	@Bean
	public DataSource dataSource(){
		try {
			JndiObjectFactoryBean factoryBean = new JndiObjectFactoryBean();
			factoryBean.setDefaultObject(getDefaultDataSource());
			factoryBean.setExpectedType(DataSource.class);
			factoryBean.setJndiName("jdbc/MyQueDatasource");
			factoryBean.setResourceRef(true);
			factoryBean.afterPropertiesSet();
			return (DataSource) factoryBean.getObject();
		}catch(Exception e){
			throw new BeanCreationException("dataSource", e);
		}
	}
	@Bean
	public SpringLiquibase liquibase(){
		SpringLiquibase liquibase = new SpringLiquibase();
		liquibase.setChangeLog("classpath:liquibase/changelog.xml");
		liquibase.setDataSource(dataSource());
		return liquibase;
	}
	@Bean
	public SessionFactory sessionFactory(){
		String[] packages = {
			"com.tigersndragons.docbookmarks.model"
		};
		
		Properties hibernateProperties = new Properties();
		hibernateProperties.put("hibernate.cache.use_query_cache", true);
		hibernateProperties.put("hibernate.cache.use_structured_entries", true);
		hibernateProperties.put("hibernate.cache.use_second_level_cache", true);
		hibernateProperties.put("hibernate.cache.region.factory_class", SingletonEhCacheRegionFactory.class.getName());
		hibernateProperties.put("hibernate.order_updates", true);
		hibernateProperties.put("hibernate.order_inserts", true);
		hibernateProperties.put("jadira.usertype.autoRegisterUserTypes", "true");
		hibernateProperties.put("jadira.usertype.databaseZone", "UTC");
		hibernateProperties.put("jadira.usertype.javaZone", "UTC");
		hibernateProperties.put("hibernate.show_sql", true);
		hibernateProperties.put("hibernate.connection.autocommit", true);
		hibernateProperties.put("hibernate.flush_mode", "COMMIT");
		
		LocalSessionFactoryBuilder builder = new LocalSessionFactoryBuilder(dataSource());

		builder.scanPackages(packages);
		builder.addPackages(packages);
		builder.addProperties(hibernateProperties);

		builder.setProperty("show.sql", Boolean.TRUE.toString());
		builder.setInterceptor(new AuditTrailInterceptor());
		builder.setProperty("hibernate.dialect","org.hibernate.dialect.MySQLDialect" );
		return builder.buildSessionFactory();
	}
	
	@Bean
//	@Override
	public PlatformTransactionManager annotationDrivenTransactionManager() {
		return  new HibernateTransactionManager(sessionFactory());
	}
	
	@Bean 
	public DocBookmarksDAO docBookmarksDAO(){
		DocBookmarksDAOImpl di = new DocBookmarksDAOImpl(sessionFactory());
		return di;
	}
	
	@Bean
	public LoginService loginService(){
		LoginServiceImpl ls = new LoginServiceImpl();
		ls.setDbDAO(docBookmarksDAO());
		return ls;
	}
	
	@Bean 
	public DocShellService docShellService(){
		DocShellServiceImpl ds = new DocShellServiceImpl ();
		ds.setDbDAO(docBookmarksDAO());
		return ds;
	}
	
	@Bean 
	public DocBookmarkService docBookmarkService(){
		DocBookmarkServiceImpl ds = new DocBookmarkServiceImpl ();
		ds.setDbDAO(docBookmarksDAO());
		return ds;
	}
	
	@Bean 
	public CustomUserDetailsService userDetailsService(){
		CustomUserDetailsService userDetailsService = new CustomUserDetailsService();
		userDetailsService.setLoginService(loginService());
		return userDetailsService;
	}
	
	//MappedDocBookmark
	
	//Controller
	@Bean
	public HomeController homeController(){
		HomeController hc = new HomeController();
		hc.setLoginService(loginService());
		hc.setDocShellService(docShellService());
		hc.setDocBookmarkService(docBookmarkService());
		return hc;
	}

}
