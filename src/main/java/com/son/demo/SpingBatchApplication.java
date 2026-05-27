package com.son.demo;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableBatchProcessing
@ComponentScan(basePackages =  {"com.son.config","com.son.controller", "com.son.service", "com.son.model","com.son.reader","com.son.writer"})
public class SpingBatchApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpingBatchApplication.class, args);
	}

}
