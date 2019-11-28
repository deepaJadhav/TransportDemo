package com.example.transportDemo.Model;

import java.time.LocalDate;

public class Days {
	
	private LocalDate startDate;
	private LocalDate endDate;
	private double numOfDays;
	public double getNumOfDays() {
		return numOfDays;
	}
	public void setNumOfDays(double numOfDays) {
		this.numOfDays = numOfDays;
	}
	public LocalDate getStartDate() {
		return startDate;
	}
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}
	public LocalDate getEndDate() {
		return endDate;
	}
	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}
	

}
