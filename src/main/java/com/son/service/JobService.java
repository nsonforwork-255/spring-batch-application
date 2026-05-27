package com.son.service;

public interface JobService {

	public void startJob(String jobName) throws Exception;
	
	public void stopJob(Long jobId) throws Exception;

}
