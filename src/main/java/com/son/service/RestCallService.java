package com.son.service;

import java.util.List;

import com.son.model.StudentReponse;

public interface RestCallService {
	
	public List<StudentReponse> restCallTemplate();
	
	public StudentReponse read();
	
	public String write(StudentReponse studentReponse);

}
