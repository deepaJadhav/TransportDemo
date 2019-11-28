package com.example.transportDemo.Service;

import java.lang.reflect.Parameter;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GenerateEncryptedData {
	
    @Autowired
    JobLauncher jobLauncher;
      
    @Autowired
    Job job;

 public boolean generateEncyptedFile(String filename,int maxThreads) throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException
 {
     JobParameters params = new JobParametersBuilder()
             .addString("JobID", String.valueOf(System.currentTimeMillis())).addString("fileName", filename).addString("maxThreads", String.valueOf(maxThreads))
             .toJobParameters();
     jobLauncher.run(job, params);
    
	 return true;
 }
}
