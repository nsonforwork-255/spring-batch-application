package com.son.demo;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.EnableJdbcJobRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJdbcJobRepository
@EnableBatchProcessing
@ComponentScan(basePackages =  {"com.son.listener","com.son.config","com.son.controller", "com.son.service", "com.son.model","com.son.reader","com.son.writer","com.son.tasklet","com.son.processor"})
@EnableAsync
//@EnableScheduling
public class SpingBatchApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpingBatchApplication.class, args);
	}

}
