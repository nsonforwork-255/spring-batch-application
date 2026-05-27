package com.son.processor;

import org.jspecify.annotations.Nullable;
import org.springframework.batch.infrastructure.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class FirstItemProcessor implements ItemProcessor<Integer, String>{

	@Override
	public @Nullable String process(Integer item) throws Exception {
		return String.valueOf(item * 10);
	}

	
}
