package com.example.demo;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;

public class JobListener extends JobExecutionListenerSupport {
	
	public void beforeMessage(JobExecution jobExecution) {
		super.beforeJob(jobExecution);
		System.out.println("ジョブ開始");
	}
	
	public void afterMessage(JobExecution jobExecution) {
		super.afterJob(jobExecution);
		System.out.println("ジョブ終了");
	}
}
