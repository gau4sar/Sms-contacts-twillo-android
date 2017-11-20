package com.example.gaurav.smscontacts.fragments.contact_list;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.gaurav.smscontacts.R;
import com.example.gaurav.smscontacts.send_sms.SendSmsActivity;
import com.example.gaurav.smscontacts.model.ContactData;
import com.example.gaurav.smscontacts.utils.Constants.Contact;

import java.util.List;

/**
 * Created by gaurav on 17/07/17.
 */

public class ContactViewAdapter extends RecyclerView.Adapter<ContactViewAdapter.ContactViewHolder> {

    List<ContactData> contactsList;
    Context context;

    public ContactViewAdapter(Context context, List<ContactData> contactsList) {
        this.context = context;
        this.contactsList = contactsList;
    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_contact_info, viewGroup, false);
        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ContactViewHolder holder, int position) {
        int pos = holder.getAdapterPosition();
        holder.contactIdView.setText(contactsList.get(pos).id);
        holder.firstNameView.setText(contactsList.get(pos).firstName);
        holder.lastNameView.setText(contactsList.get(pos).lastName);
        holder.cardView.setOnClickListener(v -> {
            Intent intent = new Intent(context, SendSmsActivity.class);
            intent.putExtra(Contact.FIRST_NAME, contactsList.get(pos).firstName);
            intent.putExtra(Contact.LAST_NAME, contactsList.get(pos).lastName);
            intent.putExtra(Contact.PHONE_NUMBER, contactsList.get(pos).number);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return contactsList.size();
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        TextView contactIdView;
        TextView firstNameView;
        TextView lastNameView;

        public ContactViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.cv_item);
            contactIdView = (TextView) itemView.findViewById(R.id.tv_contact_id);
            firstNameView = (TextView) itemView.findViewById(R.id.tv_first_name);
            lastNameView = (TextView) itemView.findViewById(R.id.tv_last_name);
        }
    }
}
