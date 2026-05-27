package com.son.config;

import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.job.parameters.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.son.processor.FirstItemProcessor;
import com.son.reader.FirstItemReader;
import com.son.writer.FirstItemWriter;

@Configuration
public class SampleChunkJob {

	@Autowired
	private JobRepository jobRepository;
	
	@Autowired
	private FirstItemReader firstItemReader;
	
	@Autowired 
	private FirstItemWriter firstItemWriter;
	
	@Autowired
	private FirstItemProcessor firstItemProcessor;
	@Bean
	public Job chunkJob() {
		return new JobBuilder("firstChunkJob",jobRepository).incrementer(new RunIdIncrementer()).start(firstChunkStep()).build();
		
	}
	
	public Step firstChunkStep() {
		
		return new StepBuilder("firstChunkStep",jobRepository).<Integer,String>chunk(3).reader(firstItemReader).processor(firstItemProcessor).writer(firstItemWriter).build();
	}
	
	
}
