package com.ez.sendem.ui;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.ez.sendem.R;
import com.ez.sendem.adapter.SpinnerAdapter;
import com.ez.sendem.db.DBConstraint;
import com.ez.sendem.db.RealmMainHelper;
import com.ez.sendem.db.tables.Table_Recipient;
import com.ez.sendem.db.tables.Table_Scheduled;
import com.ez.sendem.function.GeneralFunction;
import com.ez.sendem.object.ContactData;
import com.ez.sendem.ui.component.FontButton;

import java.util.ArrayList;
import java.util.Date;

import io.realm.RealmList;

public class ScheduleNewAct extends RootToolbar implements View.OnClickListener, AdapterView.OnItemSelectedListener{
    private FontButton fb_addrecipient;
    private Spinner spn_repeat, spn_ends;
    private String[] repeatType, endsType;

    private static final int PICK_CONTACT = 1;

    private EditText etTo, et_msg;
    private Button btn_schedule;
    private DatePicker datePicker;
    private TimePicker timePicker;
    private String str_selectedContact = "";
    private ArrayList<ContactData> selectedContact = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedulenewact);

        setTitle(R.string.schedulenew_title);

        etTo = (EditText)findViewById(R.id.etTo);
        et_msg = (EditText)findViewById(R.id.et_msg);

        fb_addrecipient = (FontButton)findViewById(R.id.fb_addrecipient);
        fb_addrecipient.setOnClickListener(this);

        repeatType = getResources().getStringArray(R.array.repeat_type);
        spn_repeat = (Spinner)findViewById(R.id.spn_repeat);
        SpinnerAdapter adtRepeatQuestion = new SpinnerAdapter(this,repeatType);
        spn_repeat.setAdapter(adtRepeatQuestion);
        spn_repeat.setOnItemSelectedListener(this);

        endsType = getResources().getStringArray(R.array.ends_type);
        spn_ends = (Spinner)findViewById(R.id.spn_ends);
        SpinnerAdapter adtEndsQuestion = new SpinnerAdapter(this,endsType);
        spn_ends.setAdapter(adtEndsQuestion);
        spn_ends.setOnItemSelectedListener(this);

        btn_schedule = (Button)findViewById(R.id.btn_schedule);
        btn_schedule.setOnClickListener(this);

        datePicker = (DatePicker)findViewById(R.id.datePicker);
        timePicker = (TimePicker)findViewById(R.id.timePicker);
    }

    @Override
    public void onClick(View view) {
        if(view.equals(fb_addrecipient)){
            selectContact();
        } else if(view.equals(btn_schedule)){
            saveSchedule();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void refreshRecipientEditText(){
        etTo.setText(str_selectedContact);
    }

    private void selectContact(){
        Uri uri = Uri.parse("content://contacts");
        Intent intent = new Intent(Intent.ACTION_PICK, uri);
        intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
        startActivityForResult(intent, PICK_CONTACT);
    }

    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        switch (reqCode) {
            case (PICK_CONTACT) :
                if (resultCode == Activity.RESULT_OK) {
                    Uri contactUri = data.getData();
                    ContentResolver cr = getContentResolver();
                    Cursor c = cr.query(contactUri, null, null, null, null);

                    if (c.moveToFirst()) {
                        String id =
                                c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts._ID));

                        String displayName = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                        String phoneNumber = c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                        Uri photoUri = Uri.withAppendedPath(contactUri, ContactsContract.Contacts.Photo.CONTENT_DIRECTORY);

                        ContactData contactData = new ContactData();
                        contactData.id = id;
                        contactData.displayName = displayName;
                        contactData.phoneNumber = phoneNumber;
                        contactData.photoUri = photoUri;
                        str_selectedContact += displayName +"("+phoneNumber+")";
                        refreshRecipientEditText();
                        selectedContact.add(contactData);
                    }
                    c.close();
                }
                break;
        }
    }

    private void saveSchedule(){
        RealmList<Table_Recipient> recipientList = new RealmList<>();
        for(int i = 0; i<selectedContact.size(); i++){
            Table_Recipient recipient = new Table_Recipient();
            recipient.setInfo("");
            recipient.setPhoneNumber(selectedContact.get(i).phoneNumber);
            recipientList.add(recipient);
//            GeneralFunction.sendSMS(this, selectedContact.get(i).phoneNumber, "test");
        }

        Date selectedDate = GeneralFunction.getDateTimeFromPicker(datePicker, timePicker);

        Table_Scheduled schedule = new Table_Scheduled();
        schedule.setSch_id(RealmMainHelper.getPrimaryKey(Table_Scheduled.class, Table_Scheduled.PRIMARY_KEY));
        schedule.setRecipients(recipientList);
        schedule.setSch_msg(et_msg.getText().toString());
        schedule.setSch_recipient_type(DBConstraint.SCHEDULE_RECIPIENT_TYPE.SMS);
        schedule.setSch_date(selectedDate.getTime());
        schedule.setSch_ends_on(0);
        schedule.setSch_repeat_type(0);
        schedule.setSch_status(DBConstraint.SCHEDULE_STATUS.ACTIVE);
        RealmMainHelper.insertDB(schedule);

//        AlarmFunction.setAlarm(this, selectedDate.getTime());
//        AlarmFunction.setAlarm(this, selectedDate.getTime()+TimeUnit.MINUTES.toMillis(2));

        finish();
    }
}
