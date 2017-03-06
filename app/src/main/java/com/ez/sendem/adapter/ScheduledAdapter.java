package com.ez.sendem.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.ez.sendem.R;
import com.ez.sendem.db.tables.Table_Scheduled;
import com.ez.sendem.function.ContactFunction;
import com.ez.sendem.function.GeneralFunction;
import com.ez.sendem.object.ContactData;
import com.ez.sendem.ui.component.InitialImageGroup;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

import io.realm.OrderedRealmCollection;
import io.realm.RealmBaseAdapter;
import io.realm.RealmCollection;
import io.realm.RealmResults;


/* sz - comment:
/////ADAPTER REALM//////
*/
public class ScheduledAdapter extends RealmBaseAdapter<Table_Scheduled> implements ListAdapter{
    private Context myContext;
    private String[] repeatType_str;

    public ScheduledAdapter(Context context, OrderedRealmCollection<Table_Scheduled> realmResults) {
        super(context, realmResults);
        myContext = context;
        repeatType_str = myContext.getResources().getStringArray(R.array.repeat_type_str);
    }

    class ViewHolder{
        InitialImageGroup iv;
        TextView tvRecipient, tvMsg, tvInfo;
    }

    @Override
    public long getItemId(int position){
        return adapterData.get(position).getSch_id();
    }

    ViewHolder viewHolder;
    @Override
    public View getView(int position, View view, ViewGroup parent) {

        if(view==null){
            LayoutInflater inflater = ((Activity)myContext).getLayoutInflater();
            view = inflater.inflate(R.layout.scheduledadapter_item, null);

            viewHolder = new ViewHolder();
            viewHolder.iv = (InitialImageGroup) view.findViewById(R.id.iv);
            viewHolder.tvRecipient = (TextView)view.findViewById(R.id.tvRecipient);
            viewHolder.tvMsg = (TextView)view.findViewById(R.id.tvMsg);
            viewHolder.tvInfo = (TextView)view.findViewById(R.id.tvInfo);

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder)view.getTag();
        }

        Table_Scheduled item = adapterData.get(position);
        if(item!=null){
            //set data from item to ui
            String contactInfo = "";
            String[] initial = new String[item.getRecipients().size()];
            for(int a=0; a<item.getRecipients().size(); a++){
                ContactData contactData = ContactFunction.getPhoneContactInfo(context, item.getRecipients().get(a).phoneNumber());
                if(contactData != null){
                    contactInfo += contactData.displayName + ";";
                    initial[a] = contactData.displayName.substring(0,1);
                }
            }

            viewHolder.iv.setValue(initial);
            viewHolder.tvRecipient.setText(contactInfo);
            viewHolder.tvMsg.setText(item.getSch_msg());
            viewHolder.tvInfo.setText(getInfo(item.getSch_repeat_type(), item.getSch_next_active()));
        }

        return view;
    }

    private String getInfo(int repeatType, long nextActiveDate){
        String info = repeatType_str[repeatType];

        /*
        comment:
            1. ganti %1 dari repearType_str dengan tanggal next_active_date
         */
        info = info.replace("%date", GeneralFunction.getDateTime(new Date(nextActiveDate), GeneralFunction.DATE_TIME_DISPLAY_FORMAT));

        return info;
    }
}
