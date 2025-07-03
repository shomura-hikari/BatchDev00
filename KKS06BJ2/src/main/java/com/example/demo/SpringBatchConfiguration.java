package com.example.demo;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;


@Configuration
public class SpringBatchConfiguration {

    @Autowired
    KKS06BJ02Ser KKS06BJ02Ser;

//    @Autowired
//    private JobBuilder jobBuilderFactory;
//    
//    @Autowired
//    private StepBuilder stepBuilderFactory;
    
    @Autowired
    private PlatformTransactionManager transactionManager;

    @Bean
    public Step step(KKS06BJ02Ser KKS06BJ02Ser, JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("HelloWorld", jobRepository)
                .tasklet(KKS06BJ02Ser, transactionManager).build();
    }

    @Bean
    public Job job(JobRepository jobRepository, Step step) throws Exception {
        return new JobBuilder("HelloWorld", jobRepository).listener(listener()).start(step).build();
    }

    @Bean
    public JobExecutionListener listener() {
        return new JobListener();
    }
}

