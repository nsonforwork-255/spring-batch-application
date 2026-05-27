package com.son.service.impl;

import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.parameters.InvalidJobParametersException;
import org.springframework.batch.core.job.parameters.JobParameters;
import org.springframework.batch.core.job.parameters.JobParametersBuilder;
import org.springframework.batch.core.launch.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.launch.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.son.service.JobService;

import ch.qos.logback.core.testUtil.RandomUtil;

@Service
public class JobServiceImpl implements JobService {

	@Autowired
	private JobLauncher jobLauncher;

	@Autowired
	@Qualifier("firstChunkJob")
	private Job firstChunkJob;

	@Autowired
	@Qualifier("firstJob")
	private Job firstJob;

	@Override
	public void startJob(String jobName) throws Exception {

		JobParameters jobParameters = new JobParametersBuilder().addString("jobName", jobName)
				.addLong("run.id", Long.valueOf(RandomUtil.getPositiveInt())).toJobParameters();
		if (jobName.equalsIgnoreCase("firstChunkJob")) {
			jobLauncher.run(firstChunkJob, jobParameters);
		} else {
			return;
		}

	}
	
	@Scheduled(cron = "0 0/1 * 1/1 * ?")
	public void schedulerFirstJob() throws Throwable {
		JobParameters jobParameters = new JobParametersBuilder().addString("jobName", "firstJob")
				.addLong("run.id", Long.valueOf(RandomUtil.getPositiveInt())).toJobParameters();
		jobLauncher.run(firstJob, jobParameters);
	}

}
