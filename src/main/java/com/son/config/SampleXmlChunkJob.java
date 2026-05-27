package com.son.config;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.job.parameters.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.infrastructure.item.xml.StaxEventItemReader;
import org.springframework.batch.infrastructure.item.xml.StaxEventItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import com.son.model.XmlModel;

@Configuration
public class SampleXmlChunkJob {

	@Autowired
	private JobRepository jobRepository;

	@Bean
	public Job xmlChunkJob() {
		return new JobBuilder("xmlChunkJob", jobRepository).start(xmlChunkStep()).incrementer(new RunIdIncrementer())
				.build();
	}

	public Step xmlChunkStep() {
		return new StepBuilder("xmlChunkStep", jobRepository).<XmlModel, XmlModel>chunk(3)
				.reader(staxEventItemReader(null)).writer(staxEventItemWriter(null)).build();
	}

	@Bean
	@StepScope
	public StaxEventItemReader<XmlModel> staxEventItemReader(
			@Value("#{jobParameters['inputFile']}") FileSystemResource fsr) {
		Jaxb2Marshaller jaxb2Marshaller = new Jaxb2Marshaller();
		jaxb2Marshaller.setClassesToBeBound(XmlModel.class);

		StaxEventItemReader<XmlModel> staxEventItemReader = new StaxEventItemReader<XmlModel>(jaxb2Marshaller);
		staxEventItemReader.setResource(fsr);
		staxEventItemReader.setFragmentRootElementName("student");
		return staxEventItemReader;
	}

	@Bean
	@StepScope
	public StaxEventItemWriter<XmlModel> staxEventItemWriter(
			@Value("#{jobParameters['outputFile']}") FileSystemResource fsr) {
		Jaxb2Marshaller jaxb2Marshaller = new Jaxb2Marshaller();
		jaxb2Marshaller.setClassesToBeBound(XmlModel.class);

		StaxEventItemWriter<XmlModel> staxEventItemWriter = new StaxEventItemWriter<XmlModel>(jaxb2Marshaller);
		staxEventItemWriter.setResource(fsr);
		staxEventItemWriter.setRootTagName("students");
		return staxEventItemWriter;
	}
}
