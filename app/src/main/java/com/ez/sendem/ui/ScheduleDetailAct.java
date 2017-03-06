package com.ez.sendem.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ez.sendem.R;
import com.ez.sendem.db.DBConstraint;
import com.ez.sendem.db.RealmMainHelper;
import com.ez.sendem.db.tables.Table_Scheduled;
import com.ez.sendem.function.ContactFunction;
import com.ez.sendem.function.GeneralFunction;
import com.ez.sendem.manager.PageManager;
import com.ez.sendem.object.ContactData;

import java.util.Date;

public class ScheduleDetailAct extends RootToolbar implements View.OnClickListener{

    private TextView tvToValue, tvMsgValue, tvNextActiveValue, tvRepeatTypeValue, tvEndsValue, tvStatusValue;
    private int sch_id=0;
    private String[] arr_sch_status, arr_repeat_type;
    private Button btnInActive;
    private Table_Scheduled scheduled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scheduledetail);

        setTitle(R.string.scheduledetail_title);

        //get params from page before
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null) {
            sch_id = bundle.getInt(PageManager.KEY_SCH_ID);
        }
        arr_sch_status = getResources().getStringArray(R.array.sch_status);
        arr_repeat_type = getResources().getStringArray(R.array.repeat_type);

        tvToValue = (TextView)findViewById(R.id.tvToValue);
        tvMsgValue = (TextView)findViewById(R.id.tvMsgValue);
        tvNextActiveValue = (TextView)findViewById(R.id.tvNextActiveValue);
        tvRepeatTypeValue = (TextView)findViewById(R.id.tvRepeatTypeValue);
        tvEndsValue = (TextView)findViewById(R.id.tvEndsValue);
        tvStatusValue = (TextView)findViewById(R.id.tvStatusValue);
        btnInActive = (Button)findViewById(R.id.btnInActive);
        btnInActive.setOnClickListener(this);

        setContent();
    }

    private void setContent(){
        scheduled = RealmMainHelper.getRealm().where(Table_Scheduled.class).equalTo(Table_Scheduled.PRIMARY_KEY, sch_id).findFirst();
        if(scheduled!=null){
            String str_contact = "";
            for(int a=0; a<scheduled.getRecipients().size(); a++){
                ContactData contactData = ContactFunction.getPhoneContactInfo(ScheduleDetailAct.this, scheduled.getRecipients().get(a).phoneNumber());
                if(contactData!=null){
                    str_contact += contactData.displayName +"("+contactData.phoneNumber+")\n";
                }
            }
            tvToValue.setText(str_contact);
            tvMsgValue.setText(scheduled.getSch_msg());
            tvNextActiveValue.setText(GeneralFunction.getDateTime(new Date(scheduled.getSch_next_active()), GeneralFunction.DATE_TIME_DISPLAY_FORMAT));
            tvRepeatTypeValue.setText(arr_repeat_type[scheduled.getSch_repeat_type()]);
            if(scheduled.getSch_ends_on() == 0){
                tvEndsValue.setVisibility(View.GONE);
            } else {
                tvEndsValue.setVisibility(View.VISIBLE);
                tvEndsValue.setText(GeneralFunction.getDateTime(new Date(scheduled.getSch_ends_on()), GeneralFunction.DATE_DISPLAY_FORMAT));
            }
            tvStatusValue.setText(arr_sch_status[scheduled.getSch_status()]);

            if(scheduled.getSch_status() == DBConstraint.SCHEDULE_STATUS.ACTIVE){
                btnInActive.setVisibility(View.VISIBLE);
            } else {
                btnInActive.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onClick(View view) {
        if(view.equals(btnInActive)){
            try{
                RealmMainHelper.getRealm().beginTransaction();
                scheduled.setSch_status(DBConstraint.SCHEDULE_STATUS.INACTIVE);
                RealmMainHelper.getRealm().commitTransaction();
            }catch (Exception e){
                e.printStackTrace();
                RealmMainHelper.getRealm().cancelTransaction();
            }
            setContent();
        }
    }
}
