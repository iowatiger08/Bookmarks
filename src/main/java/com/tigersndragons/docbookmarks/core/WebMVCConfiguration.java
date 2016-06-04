package com.tigersndragons.docbookmarks.core;

import java.util.Arrays;
import java.util.Properties;

import com.tigersndragons.docbookmarks.formactions.LoginFlowActions;
import com.tigersndragons.docbookmarks.formactions.DocMapFormModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;
import org.springframework.web.servlet.mvc.support.ControllerClassNameHandlerMapping;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.webflow.mvc.builder.MvcViewFactoryCreator;


@Configuration
public class WebMVCConfiguration extends WebMvcConfigurerAdapter {


	@Bean 
	public ControllerClassNameHandlerMapping controllerClassNameHandlerMapping(){
		ControllerClassNameHandlerMapping mapping  =  new ControllerClassNameHandlerMapping();
		 mapping.setPathPrefix("/view");
		return mapping;
	}
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations("/", "classpath:/META-INF/web-resources/");
		registry.addResourceHandler("/resources/**").addResourceLocations("/", "/resources/");
	}

	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {		
	}

/**/	
	@Bean 
	public SimpleUrlHandlerMapping urlMappings(){
		SimpleUrlHandlerMapping simpleUrlHandlerMapping = new SimpleUrlHandlerMapping();
		Properties urlProperties = new Properties();

		simpleUrlHandlerMapping.setMappings(urlProperties);
		simpleUrlHandlerMapping.setAlwaysUseFullPath(true);
		return simpleUrlHandlerMapping;
	}
/**/
	
	@Bean
	public ViewResolver viewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setViewClass(org.springframework.web.servlet.view.JstlView.class);
		viewResolver.setPrefix("/WEB-INF/view/");
		viewResolver.setSuffix(".jsp");
		return viewResolver;

	}
	
	@Bean
	public MvcViewFactoryCreator mvcViewFactoryCreator() {
		MvcViewFactoryCreator factoryCreator = new MvcViewFactoryCreator();
		factoryCreator.setDefaultViewSuffix(".jsp");
		factoryCreator.setViewResolvers(Arrays.<ViewResolver>asList(viewResolver()));
		factoryCreator.setUseSpringBeanBinding(true);
		return factoryCreator;
	}
/*	@Bean
	public SimpleMappingExceptionResolver simpleMappingExceptionResolver(){
		SimpleMappingExceptionResolver resolver = new SimpleMappingExceptionResolver();
		Properties props = new Properties();
		props.put("org.ipers.docbookmarks.exception.CurrentErrorException", "error/generic_error");
		props.put("java.lang.Exception", "errorMessage");//"error/exception_error");
		resolver.setExceptionMappings(props);
		return resolver;
	}*/

	@Bean
	public LoginFlowActions loginActionFlows(){
		return new LoginFlowActions();
	}
	@Bean
	public DocMapFormModel docFlowActions(){
		return new DocMapFormModel();
	}


	

}
