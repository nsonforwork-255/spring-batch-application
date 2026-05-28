package com.son.config;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import jakarta.persistence.EntityManagerFactory;

@Configuration
public class DatabaseCofig {

	@Bean
	@ConfigurationProperties(prefix = "spring.datasource")
	@Primary
	public DataSource dataSource() {
		return DataSourceBuilder.create().build();
	}

	@Bean
	@ConfigurationProperties(prefix = "spring.unversitydatasource")
	public DataSource universitydatasource() {
		return DataSourceBuilder.create().build();
	}

	@Bean
	@ConfigurationProperties(prefix = "spring.postgresdatasource")
	public DataSource postgresdatasource() {
		return DataSourceBuilder.create().build();
	}

	@Bean
	public PlatformTransactionManager transactionManager(DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}

	@Bean
	public EntityManagerFactory postgresEntityManager() {

		Map<String, Object> props = new HashMap<>();
		props.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
	
		LocalContainerEntityManagerFactoryBean lem = new LocalContainerEntityManagerFactoryBean();

		lem.setDataSource(postgresdatasource());
		lem.setPackagesToScan("com.son.postgres.entity");
		lem.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		lem.setPersistenceProviderClass(HibernatePersistenceProvider.class);
		lem.setJpaPropertyMap(props);
		lem.afterPropertiesSet();

		return lem.getObject();
	}

	@Bean
	public EntityManagerFactory mysqlEntityManager() {

		
		Map<String, Object> props = new HashMap<>();
		props.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
		
		LocalContainerEntityManagerFactoryBean lem = new LocalContainerEntityManagerFactoryBean();

		lem.setDataSource(universitydatasource());
		lem.setPackagesToScan("com.son.mysql.entity");
		lem.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		lem.setPersistenceProviderClass(HibernatePersistenceProvider.class);
		lem.setJpaPropertyMap(props);
		lem.afterPropertiesSet();

		return lem.getObject();
	}
	
	@Bean
	@Primary
	public EntityManagerFactory entityManagerFactory() {

		
		Map<String, Object> props = new HashMap<>();
		props.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
		
		LocalContainerEntityManagerFactoryBean lem = new LocalContainerEntityManagerFactoryBean();

		lem.setDataSource(dataSource());
		lem.setPackagesToScan("com.son.mysql.entity");
		lem.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		lem.setPersistenceProviderClass(HibernatePersistenceProvider.class);
		lem.setJpaPropertyMap(props);
		lem.afterPropertiesSet();

		return lem.getObject();
	}


	@Bean
	@Primary
	public JpaTransactionManager jpaTransactionManager() {
		JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
		jpaTransactionManager.setDataSource(universitydatasource());
		jpaTransactionManager.setEntityManagerFactory(mysqlEntityManager());

		return jpaTransactionManager;
	}
}
