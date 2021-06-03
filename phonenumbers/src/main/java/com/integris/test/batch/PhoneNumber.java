package com.integris.test.batch;

public class PhoneNumber {

    private Long id;
    private String smsPhone;
    private String correctionDescr;
    private boolean invalid;

    public PhoneNumber() {
    }

    public PhoneNumber(Long id, String smsPhone, String correctionDescr, boolean invalid) {
	super();
	this.id = id;
	this.smsPhone = smsPhone;
	this.correctionDescr = correctionDescr;
	this.invalid = invalid;
    }

    public boolean isInvalid() {
	return invalid;
    }

    public void setInvalid(boolean invalid) {
	this.invalid = invalid;
    }

    public String getCorrectionDescr() {
	return correctionDescr;
    }

    public void setCorrectionDescr(String correctionDescr) {
	this.correctionDescr = correctionDescr;
    }

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public String getSmsPhone() {
	return smsPhone;
    }

    public void setSmsPhone(String smsPhone) {
	this.smsPhone = smsPhone;
    }

    @Override
    public String toString() {
	return "PhoneNumber [id=" + id + ", smsPhone=" + smsPhone + ", correctionDescr=" + correctionDescr + ", invalid=" + invalid + "]";
    }

}
