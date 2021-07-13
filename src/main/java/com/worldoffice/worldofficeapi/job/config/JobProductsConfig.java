package com.worldoffice.worldofficeapi.job.config;


import com.worldoffice.worldofficeapi.job.listener.JobProductsListener;
import com.worldoffice.worldofficeapi.dto.ProductDto;
import com.worldoffice.worldofficeapi.job.processor.ProductItemProcessor;
import com.worldoffice.worldofficeapi.job.writer.ProductItemWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.PathResource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

@Configuration
@EnableBatchProcessing
public class JobProductsConfig {

  @Autowired
  public JobBuilderFactory jobBuilderFactory;
  
  @Autowired
  public StepBuilderFactory stepBuilderFactory;
  
  @Autowired
  JobRepository jobRepository;
  
  @Value("${file.input}")
  private String fileInput;

  @Bean
  public JobLauncher jobProductsLauncher() throws Exception {
    SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
    jobLauncher.setJobRepository(jobRepository);
    jobLauncher.setTaskExecutor(new SimpleAsyncTaskExecutor());
    jobLauncher.afterPropertiesSet();
    return jobLauncher;
  }

  
  @Bean
  public Job processProductsJob(JobProductsListener listener, Step stepOne) {
      return jobBuilderFactory.get("processProductsJob")
          .listener(listener)
          .flow(stepOne)
          .end()
          .build();
  }
  
  @Bean
  public Step stepOne() {
      return stepBuilderFactory.get("stepOne")
          .<ProductDto, ProductDto>chunk(100)
          .reader(reader())
          .processor(processor())
          .writer(writer())
          .build();
  }
  
  @Bean
  public FlatFileItemReader<ProductDto> reader() {
      return new FlatFileItemReaderBuilder<ProductDto>()
          .name("productItemReader")
          .resource(new PathResource(fileInput))
          .delimited()
          .names(new String[] { "name", "brand", "price", "quantity", "state" , "discountPercentage"})
          .fieldSetMapper(new BeanWrapperFieldSetMapper<ProductDto>() {{
              setTargetType(ProductDto.class);
           }})
          .build();
  }
  
  
  @Bean
  public ProductItemProcessor processor() {
      return new ProductItemProcessor();
  }
  
  @Bean
  public ProductItemWriter writer() {
      return new ProductItemWriter();
  }
  
  
}
