package com.son.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.son.model.StudentReponse;
import com.son.service.RestCallService;

@Service
public class RestCallServiceImpl implements RestCallService {

	private List<StudentReponse> list = null;
	
	private final String url = "http://localhost:2525/students";
	
	private final String post_url = "http://localhost:2525/create";

	
	@Override
	public List<StudentReponse> restCallTemplate() {

		list = new ArrayList<StudentReponse>();
		RestTemplate restTemplate = new RestTemplate();
		
		StudentReponse[] studentArr = restTemplate.getForObject(url, StudentReponse[].class);
		
		for (StudentReponse studentReponse : studentArr) {
			list.add(studentReponse);
		}
		
		return list;
	}

	@Override
	public StudentReponse read() {
		
		if(list == null) {
			list = restCallTemplate();
		}
		
		if (list != null && !list.isEmpty()) {
			return list.remove(0);
		}
		
		return null;
	}

	@Override
	public String write(StudentReponse studentReponse) {
		RestTemplate restTemplate = new RestTemplate();
		String result = restTemplate.postForObject(post_url, studentReponse, String.class);
		System.out.println(result);
		return result;
	}

}
