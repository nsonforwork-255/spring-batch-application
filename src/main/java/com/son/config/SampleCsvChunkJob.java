package com.son.config;

import java.io.IOException;
import java.io.Writer;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.job.parameters.JobParameter;
import org.springframework.batch.core.job.parameters.JobParameters;
import org.springframework.batch.core.job.parameters.JobParametersIncrementer;
import org.springframework.batch.core.job.parameters.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.infrastructure.item.file.FlatFileHeaderCallback;
import org.springframework.batch.infrastructure.item.file.FlatFileItemReader;
import org.springframework.batch.infrastructure.item.file.FlatFileItemWriter;
import org.springframework.batch.infrastructure.item.file.LineMapper;
import org.springframework.batch.infrastructure.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.infrastructure.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.infrastructure.item.file.mapping.FieldSetMapper;
import org.springframework.batch.infrastructure.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.infrastructure.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.infrastructure.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.infrastructure.item.file.transform.ExtractorLineAggregator;
import org.springframework.batch.infrastructure.item.file.transform.FieldExtractor;
import org.springframework.batch.infrastructure.item.file.transform.LineAggregator;
import org.springframework.batch.infrastructure.item.file.transform.LineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import com.son.model.CsvModel;

@Configuration
public class SampleCsvChunkJob {

	@Autowired
	private JobRepository jobRepository;
	
	@Bean
	public Job csvChunkJob() {
		return new JobBuilder("csvChunkJob", jobRepository).start(csvChunkStep()).incrementer(new RunIdIncrementer())
				.build();
	}

	public Step csvChunkStep() {
		return new StepBuilder("csvChunkStep", jobRepository).<CsvModel,CsvModel>chunk(3).reader(flatFileItemReader(null)).writer(flatFileItemWriter(null)).build();
	}
	
	@Bean
	@StepScope()
	public FlatFileItemReader<CsvModel> flatFileItemReader(@Value("#{jobParameters['inputFile']}") FileSystemResource file) {
		
		DefaultLineMapper<CsvModel> lineMapper = new DefaultLineMapper<>();
		
		BeanWrapperFieldSetMapper<CsvModel> beanWrapperFieldSetMapper = new BeanWrapperFieldSetMapper<CsvModel>();
		beanWrapperFieldSetMapper.setTargetType(CsvModel.class);
		
		
		DelimitedLineTokenizer delimitedLineTokenizer = new DelimitedLineTokenizer();
		delimitedLineTokenizer.setNames("id", "firstName", "lastName", "email");
		delimitedLineTokenizer.setDelimiter(",");
		
		lineMapper.setLineTokenizer(delimitedLineTokenizer);
		lineMapper.setFieldSetMapper(beanWrapperFieldSetMapper);
		
		FlatFileItemReader<CsvModel> flatFileItemReader = new FlatFileItemReader<CsvModel>(file, lineMapper);
		flatFileItemReader.setLinesToSkip(1);
		
		return flatFileItemReader;
	}
	
	@Bean
	@StepScope
	public FlatFileItemWriter<CsvModel> flatFileItemWriter(@Value("#{jobParameters['outputFile']}") FileSystemResource file) {
		
		BeanWrapperFieldExtractor<CsvModel> beanWrapperFieldExtractor = new BeanWrapperFieldExtractor<CsvModel>();
		beanWrapperFieldExtractor.setNames(new String[] {"id", "firstName", "lastName", "email"});
		
		DelimitedLineAggregator<CsvModel> lineAggregator = new DelimitedLineAggregator<CsvModel>();
		lineAggregator.setDelimiter(",");
		lineAggregator.setFieldExtractor(beanWrapperFieldExtractor);
		
		FlatFileItemWriter<CsvModel> flatFileItemWriter = new FlatFileItemWriter<CsvModel>(file, lineAggregator);
		
		flatFileItemWriter.setHeaderCallback(new FlatFileHeaderCallback() {
			
			@Override
			public void writeHeader(Writer writer) throws IOException {
				writer.write("id,firstName,lastName,email");
				
			}
		});
		return flatFileItemWriter;
	}
}
