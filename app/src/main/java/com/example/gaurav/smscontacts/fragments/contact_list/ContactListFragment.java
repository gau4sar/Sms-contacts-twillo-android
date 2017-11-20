package com.example.gaurav.smscontacts.fragments.contact_list;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gaurav.smscontacts.R;
import com.example.gaurav.smscontacts.model.ContactData;
import com.example.gaurav.smscontacts.utils.Constants;
import com.example.gaurav.smscontacts.utils.Constants.Contact;
import com.example.gaurav.smscontacts.utils.GeneralUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 */
public class ContactListFragment extends Fragment {

    @BindView(R.id.rv_contacts)
    RecyclerView recyclerViewContacts;

    List<ContactData> contactList = new ArrayList<>();
    ContactViewAdapter contactViewAdapter;

    public ContactListFragment() {
        // Required empty public constructor
    }

    public static ContactListFragment getInstance() {
        return new ContactListFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_contacts, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewContacts.setLayoutManager(llm);
        recyclerViewContacts.setAdapter(contactViewAdapter);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        contactList = fetchContactList();
        contactViewAdapter = new ContactViewAdapter(getContext(), contactList);
    }

    // Retrives the list on contacts from the contact.json file under assets
    public List<ContactData> fetchContactList() {
        String contactsJsonString = GeneralUtils.loadJSONFromAsset(getContext(), Constants.CONTACTS_JSON);
        try {
            JSONObject contactsObj = new JSONObject(contactsJsonString);
            JSONArray contactsArray = contactsObj.getJSONArray("contacts");
            for (int i = 0; i < contactsArray.length(); i++) {
                JSONObject contactObj = contactsArray.getJSONObject(i);

                ContactData contactData = new ContactData(
                        contactObj.getString(Contact.ID),
                        contactObj.getString(Contact.FIRST_NAME),
                        contactObj.getString(Contact.LAST_NAME),
                        contactObj.getString(Contact.PHONE_NUMBER));

                contactList.add(contactData);

                getActivity().getPreferences(Context.MODE_PRIVATE).edit()
                        .putString("contact_data", contactList.toString()).apply();

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return contactList;
    }
}
