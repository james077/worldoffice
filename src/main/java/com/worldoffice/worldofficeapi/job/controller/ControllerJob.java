package com.worldoffice.worldofficeapi.job.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ControllerJob {

  private final JobLauncher jobProductsLauncher;
  private final Job processProductsJob;


  @GetMapping("/products-job")
  public Long demoJob() throws Exception {
    
    JobExecution jobExecution = jobProductsLauncher
        .run(processProductsJob, new JobParametersBuilder()
            .addLong("jobId", System.nanoTime())
            .toJobParameters());
    return jobExecution.getId();
  }
  
}
