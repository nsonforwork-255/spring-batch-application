package com.son.tasklet;

import org.jspecify.annotations.Nullable;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.StepContribution;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.infrastructure.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

@Component
public class SecondTasket implements Tasklet{

	@Override
	public @Nullable RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

		System.out.println("second Step by tasklet");
		return null;
	}
	
	

}
