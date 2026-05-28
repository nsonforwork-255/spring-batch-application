package com.son.config;

import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.job.parameters.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.infrastructure.item.database.JpaCursorItemReader;
import org.springframework.batch.infrastructure.item.database.JpaItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;

import com.son.postgres.entity.Student;
import com.son.processor.MigrationProccesor;

import jakarta.persistence.EntityManagerFactory;

@Configuration
public class SampleMigrationJob {

	@Autowired
	private JobRepository jobRepository;
	
	@Autowired
	@Qualifier("mysqlEntityManager")
	private EntityManagerFactory mysqlEntityManagerFactory;
	
	@Autowired
	@Qualifier("postgresEntityManager")
	private EntityManagerFactory postgresEntityManagerFactory;
	
	@Autowired
	private MigrationProccesor migrationProccesor;
	
	@Autowired
	private JpaTransactionManager jpaTransactionManager;

	@Bean
	public Job migrationJob() {
		return new JobBuilder("migrationJob", jobRepository).start(migrationStep()).incrementer(new RunIdIncrementer()).build(); 
			
	}
	
	public Step migrationStep() {
		return new StepBuilder("migrationStep", jobRepository).<Student,com.son.mysql.entity.Student>chunk(3).reader(jpaCursorItemReader()).processor(migrationProccesor).writer(jpaItemWriter()).transactionManager(jpaTransactionManager).build();
	}
	
	@Bean
	public JpaCursorItemReader<Student> jpaCursorItemReader() {
		
		JpaCursorItemReader<Student> jpaCursorItemReader = new JpaCursorItemReader<Student>(postgresEntityManagerFactory);
		jpaCursorItemReader.setQueryString("From Student");
		return jpaCursorItemReader;
	}
	
	@Bean
	public JpaItemWriter<com.son.mysql.entity.Student> jpaItemWriter() {
		
		JpaItemWriter<com.son.mysql.entity.Student> jpaItemWriter = new JpaItemWriter<com.son.mysql.entity.Student>(mysqlEntityManagerFactory);
		return jpaItemWriter;
	}
	
}
