package com.son.config;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.job.parameters.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.infrastructure.item.json.JacksonJsonObjectMarshaller;
import org.springframework.batch.infrastructure.item.json.JacksonJsonObjectReader;
import org.springframework.batch.infrastructure.item.json.JsonFileItemWriter;
import org.springframework.batch.infrastructure.item.json.JsonItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import com.son.model.JsonModel;

@Configuration
public class SampleJsonChunkJob {

	@Autowired
	private JobRepository jobRepository;
	
	@Bean
	public Job jsonChunkJob() {
		return new JobBuilder("jsonChunkJob", jobRepository).start(jsonChunkStep()).incrementer(new RunIdIncrementer()).build();
	}

	public Step jsonChunkStep() {
		return new StepBuilder("jsonChunkStep", jobRepository).<JsonModel,JsonModel>chunk(3).reader(jsonItemReader(null)).writer(jsonFileItemWriter(null)).build();
	}
	
	@Bean
	@StepScope
	public JsonItemReader<JsonModel> jsonItemReader(@Value("#{jobParameters['inputFile']}") FileSystemResource fsr) {
		
		JsonItemReader<JsonModel> jsonItemReader = new JsonItemReader<JsonModel>(fsr, new JacksonJsonObjectReader<JsonModel>(JsonModel.class));
	
		return jsonItemReader;
	}
	
	@Bean
	@StepScope
	public JsonFileItemWriter<JsonModel> jsonFileItemWriter(@Value("#{jobParameters['outputFile']}") FileSystemResource fsr) {
		
		JsonFileItemWriter<JsonModel> jsonFileItemWriter = new JsonFileItemWriter<JsonModel>(fsr, new JacksonJsonObjectMarshaller<JsonModel>());
	
		return jsonFileItemWriter;
	}
}
