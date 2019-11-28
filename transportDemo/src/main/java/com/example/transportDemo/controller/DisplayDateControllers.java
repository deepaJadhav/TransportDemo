package com.example.transportDemo.controller;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import com.example.transportDemo.Model.Days;

@Controller
public class DisplayDateControllers {
	private Logger logger = LogManager.getLogger(this.getClass());


	@RequestMapping(value = "display/NumberOfDays", method = RequestMethod.GET)
	public String getUserDates() {
		logger.info("method :display/NumberOfDays");
       	return "displaydays";
    }

	 @ResponseBody
	 @RequestMapping(value = "display/CalculateOfDays", method = RequestMethod.POST,headers = {"Accept=application/json"})
	 public String getNumberOfDays(@RequestBody Days days,Model model) throws DateTimeException ,Exception,NullPointerException{
	 logger.info("method :display/NumberOfDays");
     long daysBetween = ChronoUnit.DAYS.between(days.getStartDate(), days.getEndDate());
     return String.valueOf(daysBetween);
     }
	 
    @ExceptionHandler({Exception.class,DateTimeException.class,NullPointerException.class})
    @ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR,reason="Internal Server Error")
     public String handleError()
    {
    	return "Error has occured";
    }

}
