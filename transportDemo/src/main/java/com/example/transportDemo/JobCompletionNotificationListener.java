package com.example.transportDemo;


import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.example.transportDemo.Service.FileInputLineRepository;
import com.example.transportDemo.Model.FileReadLine;

public class JobCompletionNotificationListener extends JobExecutionListenerSupport {
	@Autowired
	private FileInputLineRepository repository;

	@Value("${fileout.name}")
	private String fileOutName;

	private Logger logger = LogManager.getLogger(this.getClass());


    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
        	List<FileReadLine>fileinputLines=repository.findAll();
        	Path path = Paths.get(fileOutName);
        	 
        	//Use try-with-resource to get auto-closeable writer instance
        	try (BufferedWriter writer = Files.newBufferedWriter(path)) 
        	{
        		for(FileReadLine inputLine:fileinputLines) {
        			System.out.println(inputLine.getLine());
        	    writer.write(inputLine.getLine());
        	    writer.newLine();
        		}
        		writer.flush();
        		writer.close();
        	} catch (IOException e) {
        		logger.error(e.getMessage());
			}
        } 
}
}
