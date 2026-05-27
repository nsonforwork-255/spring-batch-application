package com.son.listener;


import org.jspecify.annotations.Nullable;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.listener.StepExecutionListener;
import org.springframework.batch.core.step.StepExecution;
import org.springframework.stereotype.Component;

@Component
public class FirstStepListener implements StepExecutionListener{

	@Override
	public void beforeStep(StepExecution stepExecution) {
	
		System.out.println("beforeStep: " + stepExecution.getId());
		System.out.println("beforeStep: " + stepExecution.getStatus());

	}
	
	@Override
	public @Nullable ExitStatus afterStep(StepExecution stepExecution) {
		System.out.println("afterStep: " + stepExecution.getId());
		System.out.println("afterStep: " + stepExecution.getStatus());
		return null;
	}
}
