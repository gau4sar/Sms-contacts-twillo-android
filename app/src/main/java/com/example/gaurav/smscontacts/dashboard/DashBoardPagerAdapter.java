package com.example.gaurav.smscontacts.dashboard;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;

import com.example.gaurav.smscontacts.R;
import com.example.gaurav.smscontacts.fragments.contact_list.ContactListFragment;
import com.example.gaurav.smscontacts.fragments.sms_list.SmsSentListFragment;
import com.example.gaurav.smscontacts.utils.Constants;

/**
 * Created by gaurav on 16/07/17.
 */

public class DashBoardPagerAdapter extends FragmentPagerAdapter {

    String[] tabs;

    public DashBoardPagerAdapter(AppCompatActivity activity, FragmentManager fm) {
        super(fm);
        tabs = activity.getResources().getStringArray(R.array.tabs);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabs[position];
    }


    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case Constants.CONTACTS_FRAGMENT:
                return ContactListFragment.getInstance();

            case Constants.SMS_SENT_FRAGMENT:
                return SmsSentListFragment.getInstance();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
