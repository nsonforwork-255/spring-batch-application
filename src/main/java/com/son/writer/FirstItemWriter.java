package com.son.writer;

import org.springframework.batch.infrastructure.item.Chunk;
import org.springframework.batch.infrastructure.item.ItemWriter;
import org.springframework.stereotype.Component;

@Component
public class FirstItemWriter implements ItemWriter<String>{

	@Override
	public void write(Chunk<? extends String> chunk) throws Exception {
		System.out.println(chunk.toString());
		System.out.println("end writer");
		
	}

}
