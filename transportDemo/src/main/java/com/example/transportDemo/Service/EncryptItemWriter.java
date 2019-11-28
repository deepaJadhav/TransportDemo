package com.example.transportDemo.Service;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import com.example.transportDemo.Model.FileReadLine;
 
public class EncryptItemWriter<T> implements ItemWriter<T> { 
    
	@Autowired
	private FileInputLineRepository repository;
	
	private Logger logger = LogManager.getLogger(this.getClass());


    @Override
    public synchronized void write(List<? extends T> items) throws Exception { 
        //System.out.println("Writer"); 
    	//Get the file reference
    	 
    	logger.info("saving all data Lines");
    	try 
    	{ 		
                for (T item : items) { 
            	FileReadLine read=(FileReadLine) item;
            	read.setLine(CaesarCipher.encrypt(read.getLine(), 2).toString());
            	repository.save(read);
               } 
    	}
    	catch(Exception e)
    	{
    		logger.error(e.getMessage());
    	}
    }   
}