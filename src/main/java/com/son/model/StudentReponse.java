package com.son.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StudentReponse {

	private Long id;
	
	private String firstName;
	
	private String lastName;
	
	private String email;
}
