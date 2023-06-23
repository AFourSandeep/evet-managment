/**
 * 
 */
package com.afour.emgmt.config;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;


/**
 * 
 */
@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = "com.afour.emgmt")
@PropertySource(value = { "classpath:hibernate.properties" })
@EnableJpaRepositories(basePackages = { "com.afour.emgmt.repository" })
public class SpringDataJPAConfiguration {

	@Autowired
	private Environment environment;

	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(environment.getRequiredProperty("jdbc.driverClassName"));
		dataSource.setUrl(environment.getRequiredProperty("jdbc.url"));
		dataSource.setUsername(environment.getRequiredProperty("jdbc.username"));
		dataSource.setPassword(environment.getRequiredProperty("jdbc.password"));
		return dataSource;
	}
	
	private Properties hibernateProperties() {
		Properties properties = new Properties();
		properties.put("hibernate.dialect", environment.getRequiredProperty("hibernate.dialect"));
		properties.put("hibernate.show_sql", environment.getRequiredProperty("hibernate.show_sql"));
		properties.put("hibernate.format_sql", environment.getRequiredProperty("hibernate.format_sql"));
		return properties;
	}

	@Bean
	public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory);

		return transactionManager;
	}
	

	@Bean
	public EntityManagerFactory entityManagerFactory( DataSource dataSource){
		
	    final LocalContainerEntityManagerFactoryBean factory‎ = new LocalContainerEntityManagerFactoryBean();
	    factory‎.setDataSource( dataSource );
	    factory‎.setPackagesToScan( new String[] { "com.afour.emgmt.entity" } );
	    factory‎.setJpaVendorAdapter( new HibernateJpaVendorAdapter() );
	    factory‎.setJpaProperties( hibernateProperties() );
	    factory‎.setPersistenceProviderClass(HibernatePersistenceProvider.class);
	    factory‎.afterPropertiesSet();

	    return factory‎.getObject();
	}

}
