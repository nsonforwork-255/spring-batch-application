package com.son.config;

import com.son.model.CsvModel;
import javax.sql.DataSource;

import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.job.parameters.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.infrastructure.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.infrastructure.item.database.JdbcBatchItemWriter;
import org.springframework.batch.infrastructure.item.database.JdbcCursorItemReader;
import org.springframework.batch.infrastructure.item.file.FlatFileItemReader;
import org.springframework.batch.infrastructure.item.file.FlatFileItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import com.son.model.JdbcModel;
import com.son.processor.JdbcItemProcessor;

@Configuration
public class SampleJdbcChunkJob {

	@Autowired
	private JobRepository jobRepository;

	@Autowired
	private DataSource dataSource;

	@Autowired
	private FlatFileItemWriter<CsvModel> flatFileItemWriter;

	@Autowired
	private FlatFileItemReader<CsvModel> flatFileItemReader;
	
	@Autowired
	private JdbcItemProcessor itemProcessor;


	@Bean
	public Job jdbcChunkJob() {
		return new JobBuilder("jdbcChunkJob", jobRepository).start(jdbcChunkStep()).build();
	}

	@Bean
	public Job reversedJdbcChunkJob() {
		return new JobBuilder("reversedJdbcChunkJob", jobRepository).start(reversedJdbcChunkStep())
				.incrementer(new RunIdIncrementer()).build();
	}

	public Step jdbcChunkStep() {
		return new StepBuilder("jdbcChunkStep", jobRepository).<JdbcModel, CsvModel>chunk(3)
				.reader(jdbcCursorItemReader()).writer(flatFileItemWriter).build();
	}

	public Step reversedJdbcChunkStep() {
		return new StepBuilder("reversedJdbcChunkStep", jobRepository).<CsvModel, JdbcModel>chunk(3)
				.reader(flatFileItemReader).processor(itemProcessor).writer(jdbcBatchItemWriter()).build();
	}

	@Bean
	public JdbcCursorItemReader<JdbcModel> jdbcCursorItemReader() {

		return new JdbcCursorItemReader<JdbcModel>(dataSource,
				"select id,first_name as firstName, last_name as lastName, email from students",
				new BeanPropertyRowMapper<JdbcModel>(JdbcModel.class));
	}

	@Bean
	public JdbcBatchItemWriter<JdbcModel> jdbcBatchItemWriter() {
		JdbcBatchItemWriter<JdbcModel> jdbcBatchItemWriter = new JdbcBatchItemWriter<JdbcModel>();
		jdbcBatchItemWriter.setDataSource(dataSource);
		jdbcBatchItemWriter.setSql(
				"INSERT INTO students (id, first_name, last_name, email) VALUES (:id,:firstName,:lastName,:email)");
		jdbcBatchItemWriter
				.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<JdbcModel>());
		return jdbcBatchItemWriter;
	}
}
