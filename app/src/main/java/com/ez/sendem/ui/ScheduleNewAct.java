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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
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
import java.util.Calendar;
import java.util.Date;

import io.realm.RealmList;

public class ScheduleNewAct extends RootToolbar implements View.OnClickListener, AdapterView.OnItemSelectedListener{
    private FontButton fb_addrecipient;
    private CheckBox cb_repeat;
    private Spinner spn_repeat, spn_ends;
    private String[] repeatType, endsType;

    private static final int PICK_CONTACT = 1;

    private EditText etTo, et_msg;
    private Button btn_schedule;
    private LinearLayout layout_sch_datetime;
    private DatePicker datePicker;
    private TimePicker timePicker;
    private TextView tvDateTime;
    private String str_selectedContact = "", selectedDate="", selectedTime="";
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

        cb_repeat = (CheckBox)findViewById(R.id.cb_repeat);
        cb_repeat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    spn_repeat.setVisibility(View.VISIBLE);
                } else {
                    spn_repeat.setVisibility(View.GONE);
                }
            }
        });

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

        layout_sch_datetime = (LinearLayout)findViewById(R.id.layout_sch_datetime);
        layout_sch_datetime.setOnClickListener(this);

        tvDateTime = (TextView)findViewById(R.id.tvDateTime);
        datePicker = (DatePicker)findViewById(R.id.datePicker);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        selectedDate = GeneralFunction.getDateTime(calendar.getTime(), GeneralFunction.DATE_DISPLAY_FORMAT);
        datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {

            @Override
            public void onDateChanged(DatePicker datePicker, int year, int month, int dayOfMonth) {
                //month yang dihasilkan = 0-11
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month, dayOfMonth);
                selectedDate = GeneralFunction.getDateTime(calendar.getTime(), GeneralFunction.DATE_DISPLAY_FORMAT);
                refreshDateTimeTextView();
            }
        });
        timePicker = (TimePicker)findViewById(R.id.timePicker);
        selectedTime = GeneralFunction.getHourAndMinuteString(timePicker.getCurrentHour(), timePicker.getCurrentMinute());
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int hour, int minute) {
                selectedTime = GeneralFunction.getHourAndMinuteString(hour, minute);
                refreshDateTimeTextView();
            }
        });

        refreshDateTimeTextView();

        showCheckIcon(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveSchedule();
            }
        });
    }

    public void refreshDateTimeTextView(){
        tvDateTime.setText(selectedDate + " " + selectedTime);
    }

    @Override
    public void onClick(View view) {
        if(view.equals(fb_addrecipient)){
            selectContact();
        } else if(view.equals(btn_schedule)){
            saveSchedule();
        } else if(view.equals(layout_sch_datetime)){
            if(datePicker.getVisibility() == View.VISIBLE){
                datePicker.setVisibility(View.GONE);
                timePicker.setVisibility(View.GONE);
            } else {
                datePicker.setVisibility(View.VISIBLE);
                timePicker.setVisibility(View.VISIBLE);
            }
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
        }

//        Date selectedDate = GeneralFunction.getDateTimeFromPicker(datePicker, timePicker);

        Date date = GeneralFunction.parseStringToDate(selectedDate+" "+selectedTime, GeneralFunction.DATE_DISPLAY_FORMAT+" "+GeneralFunction.TIME_DISPLAY_FORMAT);

        Table_Scheduled schedule = new Table_Scheduled();
        schedule.setSch_id(RealmMainHelper.getPrimaryKey(Table_Scheduled.class, Table_Scheduled.PRIMARY_KEY));
        schedule.setRecipients(recipientList);
        schedule.setSch_msg(et_msg.getText().toString());
        schedule.setSch_recipient_type(DBConstraint.SCHEDULE_RECIPIENT_TYPE.SMS);
        schedule.setSch_date(date.getTime());
        schedule.setSch_ends_on(0);
        schedule.setSch_repeat_type(0);
        schedule.setSch_status(DBConstraint.SCHEDULE_STATUS.ACTIVE);
        RealmMainHelper.insertDB(schedule);

        finish();
    }
}
