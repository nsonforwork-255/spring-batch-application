package com.son.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.son.service.JobService;

@RestController
@RequestMapping("/api")
public class JobController {
	
	@Autowired
	private JobService jobService;
	
	@GetMapping("/start/job/{jobName}")
	public void start(@PathVariable String jobName) {
		
		try {
			jobService.startJob(jobName);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
	}
	
	@GetMapping("/stop")
	public String stop() {
		
		return "Job Stopped...";
		
	}

}
