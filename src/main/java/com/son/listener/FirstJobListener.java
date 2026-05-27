package com.son.listener;


import org.springframework.batch.core.job.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListener;
import org.springframework.stereotype.Component;

@Component
public class FirstJobListener implements JobExecutionListener{

	@Override
	public void beforeJob(JobExecution jobExecution) {
	
		System.out.println("beforeJob: " + jobExecution.getId());
		System.out.println("beforeJob: " + jobExecution.getStatus());

	}
	
	@Override
	public void afterJob(JobExecution jobExecution) {
	
		System.out.println("afterJob: " + jobExecution.getId());
		System.out.println("afterJob: " + jobExecution.getStatus());

	}
}
