package com.son.processor;

import org.jspecify.annotations.Nullable;
import org.springframework.batch.infrastructure.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.son.postgres.entity.Student;

@Component
public class MigrationProccesor implements ItemProcessor<Student, com.son.mysql.entity.Student> {

	@Override
	public com.son.mysql.entity.@Nullable Student process(Student item) throws Exception {
		com.son.mysql.entity.Student stu = new com.son.mysql.entity.Student();
		stu.setFirstName(item.getFirstName());
		stu.setEmail(item.getEmail());
		stu.setId(item.getId());
		stu.setIsActive(item.getIsActive().equals("Y") ? true : false);
		stu.setLastName(item.getLastName());
		stu.setDeptId(item.getDeptId());
		return null;
	}

}
