package com.example.gaurav.smscontacts.dashboard;

import com.example.gaurav.smscontacts.model.MessageInfo;

import java.util.List;

// Used to communicate between the activty and the fragment
public interface DataUpdateListener {
        void onDataUpdate(List<MessageInfo> smsList);
    }