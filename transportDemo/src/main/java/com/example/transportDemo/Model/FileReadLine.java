package com.example.transportDemo.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class FileReadLine {

	@Column(name="line")
	private String line;
	
	@Column(name="fileName")
	public String fileName;
	
	@Column(name="maxThreads")
	public int maxThreads;
	
	@Id
	@GeneratedValue
	private Long id;


	public String getLine() {
		return line;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public int getMaxThreads() {
		return maxThreads;
	}

	public void setMaxThreads(int maxThreads) {
		this.maxThreads = maxThreads;
	}

	public void setLine(String line) {
		this.line = line;
	}
	
	

}
