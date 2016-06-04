package com.tigersndragons.docbookmarks;

import com.tigersndragons.docbookmarks.core.CoreContextConfiguration;
import org.springframework.context.annotation.Configuration;

@Configuration
//@EnableTransactionManagement
public class TestContextConfiguration extends CoreContextConfiguration{

//	@Bean
//	@Override
//	public DataSource dataSource() {
//		return new EmbeddedDatabaseBuilder()
//			.setType(EmbeddedDatabaseType.H2)
//			.build();
//		/*BasicDataSource dataSource = new BasicDataSource();
//		dataSource.setUrl("jdbc:h2:mem:testDB");
//		dataSource.setDriverClassName("org.h2.Driver");
//		dataSource.setUsername("sa");
//		dataSource.setPassword("");
//		return dataSource;*/
//	}
//	
//	/**
//	 * Used for testing
//	 * @return
//	 */
//	@Bean
//	public JdbcTemplate jdbcTemplate(){
//		return new JdbcTemplate(dataSource());
//	}
}
