package com.son.config;

import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.job.parameters.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.infrastructure.item.adapter.ItemReaderAdapter;
import org.springframework.batch.infrastructure.item.adapter.ItemWriterAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.son.model.StudentReponse;
import com.son.service.RestCallService;

@Configuration
public class SampleRestChunkJob {

	@Autowired
	private JobRepository jobRepository;
	
	@Autowired
	private RestCallService restCallService;
	
	@Bean
	public Job restChunkJob() {
		
		return new JobBuilder("restChunkJob", jobRepository).start(restChunkStep()).incrementer(new RunIdIncrementer()).build();
	}
	
	public Step restChunkStep() {
		return new StepBuilder("restChunkStep", jobRepository).<StudentReponse,StudentReponse>chunk(3).reader(itemReaderAdapter()).writer(itemWriterAdapter()).build();
	}
	
	public ItemReaderAdapter<StudentReponse> itemReaderAdapter() {
		
		ItemReaderAdapter<StudentReponse> itemReaderAdapter = new ItemReaderAdapter<StudentReponse>();
		itemReaderAdapter.setTargetObject(restCallService);
		itemReaderAdapter.setTargetMethod("read");
		
		return itemReaderAdapter;
	}
	
	public ItemWriterAdapter<StudentReponse> itemWriterAdapter() {
		
		ItemWriterAdapter<StudentReponse> itemWriterAdapter = new ItemWriterAdapter<StudentReponse>();
		itemWriterAdapter.setTargetObject(restCallService);
		itemWriterAdapter.setTargetMethod("write");
		return itemWriterAdapter;
	}
}
