package com.example.gaurav.smscontacts.send_sms;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gaurav.smscontacts.R;
import com.example.gaurav.smscontacts.dashboard.DashBoardActivity;
import com.example.gaurav.smscontacts.utils.Constants;
import com.example.gaurav.smscontacts.utils.GeneralUtils;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ComposeSmsActivity extends AppCompatActivity {

    private final String TAG = getClass().getName();
    private String name, phoneNumber;

    @BindView(R.id.tv_recipient)
    TextView recipientTextView;

    @BindView(R.id.et_message)
    EditText messageEditText;

    private OkHttpClient mClient = new OkHttpClient();

    @OnClick(R.id.btn_send_message)
    void sendMessage() {
        try {
            post(getApplicationContext().getString(R.string.url) + "/sms", new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    Handler h = new Handler(Looper.getMainLooper());
                    if (response.isSuccessful()) {
                        // SMS sent successfully
                        h.post(() -> Toast.makeText(getApplicationContext(), "SMS Sent!", Toast.LENGTH_SHORT).show());
                        Intent intent = new Intent(ComposeSmsActivity.this, DashBoardActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    } else {
                        // SMS sending failed
                        Log.e(TAG, "Error response : " + " Network Response : " + response.networkResponse().message());
                        h.post(() -> Toast.makeText(getApplicationContext(), "Something went wrong, please try again later", Toast.LENGTH_LONG).show());
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose_sms);
        ButterKnife.bind(this);
        GeneralUtils.setToolbarTitle(this, "Compose Message");
        GeneralUtils.setToolbarBackButton(this, null);

        messageEditText.setHorizontallyScrolling(false);
        messageEditText.setLines(4);

        //Fetch data passed alog with the intent
        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            name = bundle.getString(Constants.Contact.FULL_NAME);
            phoneNumber = bundle.getString(Constants.Contact.PHONE_NUMBER);
        }

        recipientTextView.setText(name + " (" + phoneNumber + ")");

        // Sets the text message format along with a 6 digit random number
        messageEditText.setText("Hi. Your OTP is: " +
                GeneralUtils.getRandomNumber(Constants.MIN_NUM, Constants.MAX_NUM));
        // Sets the cursor at the end of the message text
        messageEditText.post(() -> messageEditText.setSelection(messageEditText.getText().length()));

    }

    // Method to post data to the server
    private Call post(String url, Callback callback) throws IOException {
        RequestBody formBody = new FormBody.Builder()
                .add("To", GeneralUtils.formatIndianPhoneNumber(phoneNumber))
                .add("Body", messageEditText.getText().toString())
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();
        Call response = mClient.newCall(request);
        response.enqueue(callback);
        return response;
    }
}
