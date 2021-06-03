package com.integris.test.batch;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;
    
    @Value("${file.input}")
    private String fileInput;


    
    @Bean
    public FlatFileItemReader<PhoneNumber> reader() {
        return new FlatFileItemReaderBuilder<PhoneNumber>()
            .linesToSkip(1)
            .name("coffeeItemReader")
            .resource(new FileSystemResource(fileInput))
            .delimited()
            .names(new String[] { "id", "smsPhone" })
            .fieldSetMapper(new BeanWrapperFieldSetMapper<PhoneNumber>() {{
                setTargetType(PhoneNumber.class);
             }})
            .build();
    }

    @Bean
    public PhoneNumberProcessor processor() {
        return new PhoneNumberProcessor();
    }
    
    @Bean
    public JobNotificationListener jobNotificationListener() {
        return new JobNotificationListener();
    }

    @Bean
    public JdbcBatchItemWriter<PhoneNumber> writer(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<PhoneNumber>().itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
            .sql("INSERT INTO phone_number (id, sms_phone, corr_descr, invalid) VALUES (:id, :smsPhone, :correctionDescr, :invalid)")
            .dataSource(dataSource)
            .build();
    }

    @Bean
    public Job importUserJob(Step step1) {
        return jobBuilderFactory.get("importPhoneNumberJob")
            .incrementer(new RunIdIncrementer())
            .listener(jobNotificationListener())
            .flow(step1)
            .end()
            .build();
    }

    @Bean
    public Step step1(JdbcBatchItemWriter<PhoneNumber> writer) {
        return stepBuilderFactory.get("step1")
            .<PhoneNumber, PhoneNumber> chunk(100)
            .reader(reader())           
            .processor(processor())
            .writer(writer)
            .faultTolerant()
            .build();
    }

}
