package com.integris.test.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

public class PhoneNumberProcessor implements ItemProcessor<PhoneNumber, PhoneNumber> {

    private static final Logger LOGGER = LoggerFactory.getLogger(PhoneNumberProcessor.class);

    @Override
    public PhoneNumber process(final PhoneNumber phoneNumber) throws Exception {
	processPhoneNumber(phoneNumber);
	return phoneNumber;
    }
    
    public static void processPhoneNumber(PhoneNumber phoneNumber) {
	String smsPhone = phoneNumber.getSmsPhone();
	if (smsPhone.length() != 11) {
	    phoneNumber.setInvalid(true);
	} else if (!smsPhone.startsWith("27")) {
		smsPhone = smsPhone.substring(2);
		smsPhone = "27" + smsPhone;
		phoneNumber.setSmsPhone(smsPhone);
		phoneNumber.setCorrectionDescr("Prefix updated");
	}
    }
    
}