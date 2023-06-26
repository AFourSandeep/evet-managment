package com.afour.emgmt.config;

import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import org.modelmapper.ModelMapper;
import org.modelmapper.module.jsr310.Jsr310Module;
import org.modelmapper.module.jsr310.Jsr310ModuleConfig;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
@ComponentScan(basePackages = "com.afour.emgmt")
@PropertySource("classpath:application.properties")
@EnableWebMvc
public class EventMgmtConfiguration implements WebMvcConfigurer {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
		registry.addResourceHandler("/swagger-ui/**")
				.addResourceLocations("classpath:/META-INF/resources/webjars/springfox-swagger-ui/");
	}

	@Bean
	public MessageSource messageSource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasename("messages");
		return messageSource;
	}
	
	@Bean
	ModelMapper mpdelMapper() {
		ModelMapper modelMapper = new ModelMapper();
		Jsr310ModuleConfig config = Jsr310ModuleConfig.builder()
			    .dateTimeFormatter(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
			    .dateFormatter(DateTimeFormatter.ISO_LOCAL_DATE)
			    .zoneId(ZoneOffset.UTC)
			    .build();
		return modelMapper.registerModule(new Jsr310Module(config));
	}


}
