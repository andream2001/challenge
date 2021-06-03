package com.integris.test.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.integris.test.batch.PhoneNumber;
import com.integris.test.batch.PhoneNumberProcessor;

@Controller
public class PhoneNumberWebController {

    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    Job job;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView showForm() {
	return new ModelAndView("index", "phonenumber", new PhoneNumber());
    }

    @PostMapping("/check")
    public String submitForm(@ModelAttribute("phonenumber") PhoneNumber phonenumber, Model model) {
	String message = "";
	PhoneNumberProcessor.processPhoneNumber(phonenumber);
	if (phonenumber.isInvalid()) {
	    message = "NOT VALID -  The phone numeber has not acceptable length(11)";
	} else if (phonenumber.getCorrectionDescr()!=null) {
	    message = "CORRECTED - The phone number prefix has been updated :" + phonenumber.getSmsPhone();
	} else {
	    message = "VALID - The phone number is  :" + phonenumber.getSmsPhone();
	}

	model.addAttribute("message", message);
	System.out.println(phonenumber.toString());

	return "index";
    }

    @RequestMapping(value = "/csv/upload", method = RequestMethod.POST)
    public @ResponseBody String uploadFileHandler(@RequestParam("file") MultipartFile file) {
	String name = "phoneNumbers.csv";
	if (!file.isEmpty()) {
	    try {
		byte[] bytes = file.getBytes();
		String rootPath = System.getProperty("user.dir") + "\\csv";
		File dir = new File(rootPath);
		File serverFile = new File(dir.getAbsolutePath() + File.separator + name);
		BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
		stream.write(bytes);
		stream.close();
		JobParameters parameters = new JobParametersBuilder()
                    .addDate("key", new Date())
                    .toJobParameters();
		
		jobLauncher.run(job, parameters);

		return "You successfully uploaded file=" + name;
	    } catch (Exception e) {
		return "You failed to upload " + name + " => " + e.getMessage();
	    }
	} else {
	    return "You failed to upload " + name + " because the file was empty.";
	}
    }

}
