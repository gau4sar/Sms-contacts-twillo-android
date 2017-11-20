package com.example.gaurav.smscontacts.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

import com.example.gaurav.smscontacts.R;
import com.example.gaurav.smscontacts.model.MessageInfo;
import com.example.gaurav.smscontacts.utils.Constants.Contact;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by gaurav on 17/07/17.
 */

public class GeneralUtils {

    private static final String TAG = "GeneralUtils";
    private static String SMS_LIST = "sms_list";

    // Sets the title displayed in the header/toolbar
    public static void setToolbarTitle(AppCompatActivity activity, String HEADER_TITLE) {
        TextView headerTitle;
        Toolbar toolbar = (Toolbar) activity.findViewById(R.id.app_bar);
        activity.setSupportActionBar(toolbar);

        if (activity.getSupportActionBar() != null)
            activity.getSupportActionBar().setDisplayShowTitleEnabled(false);

        headerTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        headerTitle.setText(HEADER_TITLE);
    }

    // Click-Handler for the toolbar/header back button
    public static void setToolbarBackButton(AppCompatActivity currentActivity, Class<?> resultActivity) {
        Toolbar toolbar = (Toolbar) currentActivity.findViewById(R.id.app_bar);
        currentActivity.setSupportActionBar(toolbar);

        if (currentActivity.getSupportActionBar() != null)
            currentActivity.getSupportActionBar().setDisplayShowTitleEnabled(false);

        if (toolbar != null) {
            toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
            toolbar.setNavigationOnClickListener(v -> {
                if (resultActivity != null) {
                    Intent intent = new Intent(currentActivity.getBaseContext(), resultActivity);
                    currentActivity.startActivity(intent);
                } else
                    currentActivity.finish();
            });
        }
    }

    // Loads the contact.json file under the assets folder and returns it as a String
    public static String loadJSONFromAsset(Context context, String fileName) {
        String jsonString = null;
        try {
            InputStream is = context.getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            jsonString = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return jsonString;
    }

    // Generates random 6 digit between the defined range
    public static int getRandomNumber(int min, int max) {
        return (int) Math.floor(Math.random() * (max - min + 1)) + min;
    }


    // Recieves a phone number and returns the number by prefixing it with the ISD +91 code
    public static String formatIndianPhoneNumber(String phoneNumber) {
        final String INDIA_ISD_CODE = "+91";
        Log.d(TAG, "Passed phone number : " + phoneNumber);
        if (phoneNumber.startsWith(INDIA_ISD_CODE) && phoneNumber.length() == 13) {
            return phoneNumber;
        } else if (phoneNumber.startsWith("0") && phoneNumber.length() == 11) {
            phoneNumber = INDIA_ISD_CODE + phoneNumber.substring(1);
        } else if (phoneNumber.length() == 10) {
            phoneNumber = INDIA_ISD_CODE + phoneNumber;
        } else {
            Log.d(TAG, "Invalid phone number. Setting phone number to null." + phoneNumber);
            phoneNumber = null;
        }
        return phoneNumber;
    }

    // Saves the SMS sent list into shared prefs
    public static void saveSentSmsList(Activity context, List<MessageInfo> smsSentList) {
        SharedPreferences preferences;
        preferences = context.getPreferences(Context.MODE_PRIVATE);

        Gson gson = new Gson();
        String jsonFavorites = gson.toJson(smsSentList);

        preferences.edit()
                .putInt(Constants.SMS.LIST_SIZE, smsSentList.size())
                .putString(SMS_LIST, jsonFavorites)
                .apply();
    }

    // Returns the SMS sent list from the Shared Preferences
    public static ArrayList<MessageInfo> fetchSentSmsList(Activity context) {

        List<MessageInfo> smsList;
        SharedPreferences preferences;
        preferences = context.getPreferences(Context.MODE_PRIVATE);

        if (preferences.contains(SMS_LIST)) {
            String jsonString = preferences.getString(SMS_LIST, null);
            Gson gson = new Gson();
            MessageInfo[] favoriteItems = gson.fromJson(jsonString,
                    MessageInfo[].class);

            smsList = Arrays.asList(favoriteItems);
            smsList = new ArrayList<>(smsList);

        } else
            return null;

        return (ArrayList<MessageInfo>) smsList;
    }

    //Returns the name for the associated number by parsing the local JSON file
    public static String getNameFromPhoneNumber(Context context, String phoneNumber) {
        String username = null;
        String contactsJsonString = GeneralUtils.loadJSONFromAsset(context, Constants.CONTACTS_JSON);
        JSONObject contactsObj = null;
        try {
            contactsObj = new JSONObject(contactsJsonString);
            JSONArray contactsArray = contactsObj.getJSONArray("contacts");
            for (int i = 0; i < contactsArray.length(); i++) {
                JSONObject contactObj = contactsArray.getJSONObject(i);
                String savedPhoneNumber = contactObj.getString(Contact.PHONE_NUMBER);
                Log.d(TAG, savedPhoneNumber);
                if (savedPhoneNumber.contains(phoneNumber)) {
                    username = contactObj.getString(Contact.FIRST_NAME) + " " +
                            contactObj.getString(Contact.LAST_NAME);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return username;
    }

}
