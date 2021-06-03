package com.integris.test.controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.integris.test.batch.JobNotificationListener;
import com.integris.test.batch.PhoneNumber;

@RestController
@RequestMapping("phonenumbers")
public class PhoneNumberRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PhoneNumberRestController.class);
    
    @Autowired
    JdbcTemplate jdbcTemplate;

    @GetMapping(produces = "application/json")
    public List<PhoneNumber> getBook(@RequestParam(required = false) String search_type) {
	String query = "SELECT * FROM phone_number";
	if(search_type!=null) {
	    switch (search_type) {
	         case "corrected":
	             query += " WHERE corr_descr IS NOT NULL";
	             break; 
	         case "invalid":
	             query += " WHERE invalid = true";
	             break; 
	         case "valid":
	             query += " WHERE invalid = false AND corr_descr IS NULL";
	             break; 
	         default:
	             LOGGER.warn("Expected search_type values: valid, invalid, corrected");
	    }
	}
	List<PhoneNumber> list = jdbcTemplate.query(query, new RowMapper<PhoneNumber>() {

	    public PhoneNumber mapRow(ResultSet rs, int rowNum) throws SQLException {
		PhoneNumber phoneNumber = new PhoneNumber();
		phoneNumber.setId(rs.getLong("id"));
		phoneNumber.setInvalid(rs.getBoolean("invalid"));
		phoneNumber.setSmsPhone(rs.getString("sms_phone"));
		phoneNumber.setCorrectionDescr(rs.getString("corr_descr"));
		return phoneNumber;
	    }

	});
	return list;
    }

    
}
