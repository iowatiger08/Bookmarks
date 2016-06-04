package com.tigersndragons.docbookmarks;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import liquibase.Liquibase;
import liquibase.exception.CommandLineParsingException;
import liquibase.exception.LiquibaseException;
import liquibase.integration.spring.SpringLiquibase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

@Configuration
public class GenerateChangeLog {
	@Autowired
	private ApplicationContext context;
	
	@Bean
	public DataSource dataSource() {
		SimpleDriverDataSource dataSource;
		try {
			dataSource = new SimpleDriverDataSource(new com.mysql.jdbc.Driver(), "jdbc:mysql://localhost:3306/docbookmarks");		
			dataSource.setUsername("root");
			dataSource.setPassword("root");
			return dataSource;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@Bean
	public Liquibase liquibase() throws SQLException, LiquibaseException{
		String changelog = "liquibase/deploy_1.x.xml";
		System.setProperty(Liquibase.SHOULD_RUN_SYSTEM_PROPERTY, Boolean.FALSE.toString());
		LiquibaseFactory liquibase = new LiquibaseFactory();
		liquibase.setResourceLoader(context);
		//.setShouldRun(false);
		liquibase.setDataSource(dataSource());
		liquibase.setChangeLog(changelog);
		return liquibase.buildLiquibase();
	}
	
	private static class LiquibaseFactory extends SpringLiquibase {
		public Liquibase buildLiquibase() throws SQLException, LiquibaseException{
			Connection c = getDataSource().getConnection();
			return super.createLiquibase(c);
		}
	}
	
	public static void main(String[] args) throws CommandLineParsingException, IOException, LiquibaseException {
		ApplicationContext context = new AnnotationConfigApplicationContext(GenerateChangeLog.class);
		Liquibase liquibase = context.getBean(Liquibase.class);
		
		Writer writer = new PrintWriter(System.out);
		liquibase.changeLogSync(null, writer);
		
	}
}
