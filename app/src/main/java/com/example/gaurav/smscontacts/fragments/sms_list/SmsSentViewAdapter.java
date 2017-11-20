package com.example.gaurav.smscontacts.fragments.sms_list;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.gaurav.smscontacts.R;
import com.example.gaurav.smscontacts.model.MessageInfo;
import com.example.gaurav.smscontacts.utils.GeneralUtils;

import java.util.List;

/**
 * Created by gaurav on 17/07/17.
 */

public class SmsSentViewAdapter extends RecyclerView.Adapter<SmsSentViewAdapter.SmsSentViewHolder> {

    List<MessageInfo> messageInfoList;
    Context context;

    public SmsSentViewAdapter() {
        // empty constructor
    }

    public SmsSentViewAdapter(Context context, List<MessageInfo> messageInfoList) {
        this.context = context;
        this.messageInfoList = messageInfoList;
    }

    @Override
    public SmsSentViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_sms_info, viewGroup, false);
        return new SmsSentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SmsSentViewHolder holder, int position) {
        int pos = holder.getAdapterPosition();
        holder.contactNameView.setText(GeneralUtils.getNameFromPhoneNumber(context, messageInfoList.get(pos).to));
        holder.timeSentView.setText(messageInfoList.get(pos).dateSent);
        holder.otpView.setText(messageInfoList.get(pos).otp);
    }

    @Override
    public int getItemCount() {
        return messageInfoList.size();
    }

    public class SmsSentViewHolder extends RecyclerView.ViewHolder {

        TextView contactNameView;
        TextView timeSentView;
        TextView otpView;

        public SmsSentViewHolder(View itemView) {
            super(itemView);

            contactNameView = (TextView) itemView.findViewById(R.id.tv_contact_name);
            timeSentView = (TextView) itemView.findViewById(R.id.tv_time_sent);
            otpView = (TextView) itemView.findViewById(R.id.tv_otp);
        }
    }
}
