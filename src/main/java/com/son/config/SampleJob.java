package com.son.config;

import org.jspecify.annotations.Nullable;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.job.parameters.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.step.StepContribution;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.infrastructure.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import com.son.tasklet.SecondTasket;

@Configuration
public class SampleJob {

	@Autowired
	private JobRepository jobRepository;

	@Autowired
	private SecondTasket secondTasket;

	@Bean
	public Job firstJob() {
		return new JobBuilder("firstJob", jobRepository).incrementer(new RunIdIncrementer()).start(firstStep())
				.next(secondStep()).build();
	}

	public Step firstStep() {
		return new StepBuilder("firstStep", jobRepository).tasklet(new Tasklet() {

			@Override
			public @Nullable RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext)
					throws Exception {
				System.out.println("first Step by tasklet");
				return null;
			}

		}).build();
	}

	public Step secondStep() {
		return new StepBuilder("secondStep", jobRepository).tasklet(secondTasket).build();
	}
}
