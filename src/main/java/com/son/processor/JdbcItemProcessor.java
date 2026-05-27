package com.son.processor;

import org.jspecify.annotations.Nullable;
import org.springframework.batch.infrastructure.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.son.model.CsvModel;
import com.son.model.JdbcModel;

@Component
public class JdbcItemProcessor implements ItemProcessor<CsvModel, JdbcModel>{

	@Override
	public @Nullable JdbcModel process(CsvModel item) throws Exception {
		return JdbcModel.builder().firstName(item.getFirstName()).email(item.getEmail()).lastName(item.getLastName()).id(item.getId()).build();
	}
}
