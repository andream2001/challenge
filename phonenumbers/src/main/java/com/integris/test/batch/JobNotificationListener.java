package com.integris.test.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

public class JobNotificationListener implements JobExecutionListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(JobNotificationListener.class);

    public JobNotificationListener() {
	super();

    }

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void afterJob(JobExecution jobExecution) {
	if (jobExecution.getStatus() == BatchStatus.COMPLETED) {	 

	    String query = "SELECT id, sms_phone, corr_descr, invalid FROM phone_number";
	    jdbcTemplate.query(query, (rs, row) -> new PhoneNumber(rs.getLong(1), rs.getString(2), rs.getString(3), rs.getBoolean(4)))
		.forEach(smsphone -> LOGGER.info("Found < {} > in the database.", smsphone));
	}
    }

    @Override
    public void beforeJob(JobExecution jobExecution) {
	jdbcTemplate.update("DELETE FROM phone_number");

    }
}
