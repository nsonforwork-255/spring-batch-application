package com.son.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;

@Data
@XmlRootElement(name = "student")
@XmlAccessorType(XmlAccessType.FIELD)
public class XmlModel {

	private Long id;
	
	@XmlElement(name = "f_n")
	private String firstName;
	
	private String lastName;
	
	private String email;
}
