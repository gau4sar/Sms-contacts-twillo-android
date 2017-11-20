package com.example.gaurav.smscontacts.send_sms;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.gaurav.smscontacts.R;
import com.example.gaurav.smscontacts.utils.Constants.Contact;
import com.example.gaurav.smscontacts.utils.GeneralUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SendSmsActivity extends AppCompatActivity {

    String firstName, lastName, fullName, phoneNumber;

    @BindView(R.id.tv_name)
    TextView nameTextView;

    @BindView(R.id.tv_phone_number)
    TextView phoneNumberTextView;

    @OnClick(R.id.btn_send_sms)
    void sendSms() {
        Intent intent = new Intent(this, ComposeSmsActivity.class);
        intent.putExtra(Contact.FULL_NAME, fullName);
        intent.putExtra(Contact.PHONE_NUMBER, phoneNumber);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_sms);
        ButterKnife.bind(this);
        GeneralUtils.setToolbarTitle(this, "Contact Details");
        GeneralUtils.setToolbarBackButton(this, null);

        Bundle bundle = getIntent().getExtras();
        firstName = bundle.getString(Contact.FIRST_NAME);
        lastName = bundle.getString(Contact.LAST_NAME);
        phoneNumber = bundle.getString(Contact.PHONE_NUMBER);
        fullName = firstName + " " + lastName;

        nameTextView.setText(fullName);
        phoneNumberTextView.setText(phoneNumber);
    }

}
