package com.example.gaurav.smscontacts.dashboard;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.example.gaurav.smscontacts.R;
import com.example.gaurav.smscontacts.model.MessageInfo;
import com.example.gaurav.smscontacts.utils.Constants;
import com.example.gaurav.smscontacts.utils.GeneralUtils;
import com.example.gaurav.smscontacts.utils.SlidingTabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by gaurav on 16/07/17.
 */

public class DashBoardActivity extends AppCompatActivity {

    private final String TAG = getClass().getName();
    @BindView(R.id.app_bar)
    Toolbar mToolbar;

    @BindView(R.id.pager)
    ViewPager viewPager;

    @BindView(R.id.tabs)
    SlidingTabLayout slidingTabLayout;

    private OkHttpClient mClient = new OkHttpClient();
    private List<MessageInfo> smsInfoList = new ArrayList<>();

    public DataUpdateListener dataUpdateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        ButterKnife.bind(this);
        GeneralUtils.setToolbarTitle(this, "Sms Contacts");

        viewPager.setAdapter(new DashBoardPagerAdapter(this, getSupportFragmentManager()));

        slidingTabLayout.setDistributeEvenly(true);
        slidingTabLayout.setCustomTabColorizer(position -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                return getColor(R.color.white);
            else
                return getResources().getColor(R.color.white);
        });

        slidingTabLayout.setViewPager(viewPager);

        getSmsList();
    }

    public void getSmsList() {
        getSmsInfoList(getApplicationContext().getString(R.string.url) + "/users", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Handler handler = new Handler(Looper.getMainLooper());

                if (response.isSuccessful()) {
                    //
                    String jsonData = response.body().string();
                    try {
                        JSONArray Jarray = new JSONArray(jsonData);
                        Log.d(TAG, "Response data : " + Jarray.get(0));
                        for (int i = 0; i < Jarray.length(); i++) {
                            JSONObject object = Jarray.getJSONObject(i);
                            MessageInfo messageInfo = new MessageInfo(
                                    object.getString("to"),
                                    object.getString("dateSent"),
                                    object.getString("otp"));
                            smsInfoList.add(messageInfo);
                        }

                        // In case the list has been updated, update the SMS list adapter as well
                        if (smsInfoList.size() >
                                getPreferences(Context.MODE_PRIVATE).getInt(Constants.SMS.LIST_SIZE, 0)) {
                            DashBoardActivity.this.runOnUiThread(() -> dataUpdateListener.onDataUpdate(smsInfoList));
                        }

                        // store sms list to SharedPreferences
                        GeneralUtils.saveSentSmsList(DashBoardActivity.this, smsInfoList);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {
                    Log.e(TAG, "Error response : " + " Network Response : " + response.networkResponse().message());
                    handler.post(() -> Toast.makeText(DashBoardActivity.this,
                            response.code() + ":" + response.message(), Toast.LENGTH_SHORT).show());
                }
            }
        });
    }

    // Retrives the List of SMSes sent
    private Call getSmsInfoList(String url, Callback callback) {
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        Call response = mClient.newCall(request);
        response.enqueue(callback);
        return response;
    }

}
