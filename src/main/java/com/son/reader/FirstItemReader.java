package com.son.reader;

import java.util.ArrayList;
import java.util.List;

import org.jspecify.annotations.Nullable;
import org.springframework.batch.infrastructure.item.ItemReader;
import org.springframework.stereotype.Component;

@Component
public class FirstItemReader implements ItemReader<Integer> {

	private List<Integer> list = new ArrayList<>(List.of(1,2,3,4,5,6,7,8));
		
	@Override
	public @Nullable Integer read() throws Exception {
	
		if(list == null || list.isEmpty()) {
			return null;
		}
		
		Integer value = list.remove(0);
		return value;
	}

	
}
