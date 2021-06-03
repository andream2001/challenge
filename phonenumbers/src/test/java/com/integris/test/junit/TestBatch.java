package com.integris.test.junit;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.After;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobInstance;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.JobRepositoryTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.integris.test.batch.BatchConfiguration;

@EnableAutoConfiguration
@ContextConfiguration(classes = { BatchConfiguration.class })
@SpringBatchTest
@SpringBootTest
@PropertySource("classpath:application.properties")
@RunWith(SpringRunner.class)
class TestBatch {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private JobRepositoryTestUtils jobRepositoryTestUtils;

    @Test
    void testJobExecution() throws Exception {
	JobExecution jobExecution;
	jobExecution = jobLauncherTestUtils.launchJob();
	JobInstance jobInstance = jobExecution.getJobInstance();
	ExitStatus jobExitStatus = jobExecution.getExitStatus();
	assertThat(jobInstance.getJobName(), is("importPhoneNumberJob"));
	assertThat(jobExitStatus.getExitCode(), is("COMPLETED"));

    }

    @After
    public void cleanUp() {
	jobRepositoryTestUtils.removeJobExecutions();
    }
}
