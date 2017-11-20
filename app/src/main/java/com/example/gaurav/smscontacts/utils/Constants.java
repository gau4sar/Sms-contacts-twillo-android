package com.example.gaurav.smscontacts.utils;

/**
 * Created by gaurav on 16/07/17.
 */

public interface Constants {

    int CONTACTS_FRAGMENT = 0;
    int SMS_SENT_FRAGMENT = 1;

    int MIN_NUM = 100000;
    int MAX_NUM = 999999;

    String CONTACTS_JSON = "contacts.json";

    interface SMS {
        String LIST_SIZE = " list_size";
    }

    interface Contact {
        String ID = "id";
        String FIRST_NAME = "first_name";
        String LAST_NAME = "last_name";
        String PHONE_NUMBER = "number";
        String FULL_NAME = "full_name";
    }
}
