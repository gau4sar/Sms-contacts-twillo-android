package com.example.gaurav.smscontacts.model;

/**
 * Created by gaurav on 17/07/17.
 */

public class ContactData {

    public String id;
    public String firstName;
    public String lastName;
    public String number;

    /**
     * No args constructor for use in serialization
     *
     */
    public ContactData() {
    }

    /**
     *
     * @param id
     * @param lastName
     * @param number
     * @param firstName
     */
    public ContactData(String id, String firstName, String lastName, String number) {
        super();
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.number = number;
    }
}
