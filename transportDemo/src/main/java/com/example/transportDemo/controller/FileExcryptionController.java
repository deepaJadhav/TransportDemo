package com.example.transportDemo.controller;

import java.time.DateTimeException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.example.transportDemo.Model.Days;
import com.example.transportDemo.Model.FileReadLine;
import com.example.transportDemo.Service.GenerateEncryptedData;

@Controller
public class FileExcryptionController {
	
	@Autowired
	GenerateEncryptedData generateEncryptedData;
	
	private Logger logger = LogManager.getLogger(this.getClass());
	
	@RequestMapping(value = "display/fileinputs", method = RequestMethod.GET)
	public String getUserDates() {
		logger.info("inside method:display/fileinputs");
       	return "fileuserinput";
    }

	@RequestMapping(value = "display/encyptedfileinputs", method = RequestMethod.POST)
	@ResponseBody
	public String getEncryptedFile(@RequestBody FileReadLine inputFile,Model model) throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {
		logger.info("inside method:display/encyptedfileinputs");
		generateEncryptedData.generateEncyptedFile(inputFile.getFileName(), inputFile.getMaxThreads());
		return "fileuserinput";
    }

    @ExceptionHandler({Exception.class,JobExecutionAlreadyRunningException.class, JobRestartException.class, JobInstanceAlreadyCompleteException.class, JobParametersInvalidException.class 
    	,NullPointerException.class})
    @ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR,reason="Internal Server Error")
     public String handleError()
    {
    	return "Error has occured";
    }

}
