package com.example.transportDemo.Service;

import java.util.List;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeJob;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;

import com.example.transportDemo.JobCompletionNotificationListener;
import com.example.transportDemo.Model.FileReadLine;

@Configuration
@EnableBatchProcessing
public class RunBatchJob {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;
     
    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    
   
    private String fileName;
    public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
    
	private String maxThreads;
	

    public String getMaxThreads() {
		return maxThreads;
	}

	public void setMaxThreads(String maxThreads) {
		this.maxThreads = maxThreads;
	}

	@Bean
    public JobCompletionNotificationListener jobExecutionListener() {
    	return new JobCompletionNotificationListener();
    }
    
    @Bean
    public  Job readFilesJob() throws UnexpectedInputException, ParseException, Exception {
        return jobBuilderFactory
                .get("readFilesJob")
                .incrementer(new RunIdIncrementer())
        		.listener(jobExecutionListener())
        		.flow(step1()).end().build();
    }
    
    
	@Bean
    @StepScope
	public TaskExecutor taskExecutor(@Value("#{jobParameters[maxThreads]}") String maxthreadsin) {
		SimpleAsyncTaskExecutor taskExecutor = new SimpleAsyncTaskExecutor();
		int usermaxthreads=maxthreadsin==null?1:Integer.valueOf(maxthreadsin);
		taskExecutor.setConcurrencyLimit(usermaxthreads);
		return taskExecutor;
	}

 
    @Bean
    public Step step1() throws UnexpectedInputException, ParseException, Exception {

        return stepBuilderFactory.get("step1").<FileReadLine,FileReadLine>chunk(5)
                .reader(reader(fileName))
                .writer(writer())//.listener(jobExecutionListener())
                .taskExecutor(taskExecutor(maxThreads))
        		.build();
    }
 
   @SuppressWarnings({ "rawtypes", "unchecked" })
    @Bean
    @StepScope
    public  synchronized FlatFileItemReader<FileReadLine> reader(@Value("#{jobParameters[fileName]}") String pathToFile) throws UnexpectedInputException, ParseException, Exception 
    {
        //Create reader instance
        FlatFileItemReader<FileReadLine> reader = new FlatFileItemReader<FileReadLine>();
        reader.setStrict(false);
        String fileName=pathToFile==null?"":pathToFile;
        reader.setResource(new FileSystemResource(fileName));
        reader.setLineMapper(new DefaultLineMapper() {
            {
                //3 columns in each row
                setLineTokenizer(new DelimitedLineTokenizer() {
                    {
                    	setDelimiter("\n");
                        setNames(new String[] { "line" });
                    }
                });
                //Set values in Employee class
                setFieldSetMapper(new BeanWrapperFieldSetMapper<FileReadLine>() {
                    {
                        setTargetType(FileReadLine.class);
                    }
                });
            }
        });
        return reader;
    }
   
      
   @Bean
    public synchronized ItemWriter<FileReadLine> writer() 
    {
	   
        return new EncryptItemWriter<FileReadLine>();
    }
}
