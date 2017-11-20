package com.example.gaurav.smscontacts.fragments.sms_list;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gaurav.smscontacts.R;
import com.example.gaurav.smscontacts.dashboard.DashBoardActivity;
import com.example.gaurav.smscontacts.dashboard.DataUpdateListener;
import com.example.gaurav.smscontacts.model.MessageInfo;
import com.example.gaurav.smscontacts.utils.GeneralUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SmsSentListFragment extends Fragment implements DataUpdateListener {

    private final String TAG = getClass().getName();

    @BindView(R.id.rv_sms_sent)
    RecyclerView recyclerViewSmsSent;

    private List<MessageInfo> smsInfoList = new ArrayList<>();
    SmsSentViewAdapter smsSentViewAdapter;

    public SmsSentListFragment() {
        // Required empty public constructor
    }

    public static SmsSentListFragment getInstance() {
        return new SmsSentListFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sms_sent, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        smsInfoList = GeneralUtils.fetchSentSmsList(getActivity());
        if (smsInfoList != null)
            updateSmsList(smsInfoList);
    }

    public void updateSmsList(List<MessageInfo> smsInfoList) {

        smsSentViewAdapter = new SmsSentViewAdapter(getContext(), smsInfoList);

        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewSmsSent.setLayoutManager(llm);
        recyclerViewSmsSent.addItemDecoration(new DividerItemDecoration(recyclerViewSmsSent.getContext(),
                llm.getOrientation()));
        recyclerViewSmsSent.setAdapter(smsSentViewAdapter);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Initialize listener
        ((DashBoardActivity) getActivity()).dataUpdateListener = this;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDataUpdate(List<MessageInfo> smsInfoList) {
        if (smsInfoList != null)
            updateSmsList(smsInfoList);
    }
}
