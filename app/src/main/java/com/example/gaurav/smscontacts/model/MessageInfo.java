package com.example.gaurav.smscontacts.model;

public class MessageInfo {

    public String to;
    public String dateSent;
    public String otp;

    /**
     * No args constructor for use in serialization
     */
    public MessageInfo() {
    }

    /**
     * @param to
     * @param dateSent
     * @param otp
     */
    public MessageInfo(String to, String dateSent, String otp) {
        super();
        this.to = to;
        this.dateSent = dateSent;
        this.otp = otp;
    }

}