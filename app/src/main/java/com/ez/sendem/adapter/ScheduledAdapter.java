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
import com.ez.sendem.object.ContactData;

import java.io.File;
import java.util.ArrayList;

import io.realm.OrderedRealmCollection;
import io.realm.RealmBaseAdapter;
import io.realm.RealmCollection;
import io.realm.RealmResults;


/* sz - comment:
/////ADAPTER REALM//////
*/
public class ScheduledAdapter extends RealmBaseAdapter<Table_Scheduled> implements ListAdapter{
    private Context myContext;

    public ScheduledAdapter(Context context, OrderedRealmCollection<Table_Scheduled> realmResults) {
        super(context, realmResults);
        myContext = context;
    }

    class ViewHolder{
        ImageView iv;
        TextView tvRecipient, tvMsg, tvInfo;
    }

    ViewHolder viewHolder;
    @Override
    public View getView(int position, View view, ViewGroup parent) {

        if(view==null){
            LayoutInflater inflater = ((Activity)myContext).getLayoutInflater();
            view = inflater.inflate(R.layout.scheduledadapter_item, null);

            viewHolder = new ViewHolder();
            viewHolder.iv = (ImageView)view.findViewById(R.id.iv);
            viewHolder.tvRecipient = (TextView)view.findViewById(R.id.tvRecipient);
            viewHolder.tvMsg = (TextView)view.findViewById(R.id.tvMsg);
            viewHolder.tvInfo = (TextView)view.findViewById(R.id.tvInfo);

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder)view.getTag();
        }

        /*dummy*/
        if(position%2==0) {
            ContactData data = ContactFunction.getPhoneContactInfo(context, "085710899789");
            viewHolder.tvRecipient.setText(data.displayName);
            Glide.with(context).load(data.photo).asBitmap().placeholder(R.drawable.pp).dontAnimate().centerCrop().into(new BitmapImageViewTarget(viewHolder.iv) {
                @Override
                protected void setResource(Bitmap resource) {
                    RoundedBitmapDrawable circularBitmapDrawable =
                            RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                    circularBitmapDrawable.setCircular(true);
                    viewHolder.iv.setImageDrawable(circularBitmapDrawable);
                    viewHolder.iv.refreshDrawableState();
                    viewHolder.iv.invalidate();
                }
            });
        } else {
            Glide.with(context).load(R.drawable.pp).asBitmap().placeholder(R.drawable.pp).dontAnimate().centerCrop().into(new BitmapImageViewTarget(viewHolder.iv) {
                @Override
                protected void setResource(Bitmap resource) {
                    RoundedBitmapDrawable circularBitmapDrawable =
                            RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                    circularBitmapDrawable.setCircular(true);
                    viewHolder.iv.setImageDrawable(circularBitmapDrawable);
                    viewHolder.iv.refreshDrawableState();
                    viewHolder.iv.invalidate();
                }
            });
        }

        Table_Scheduled item = adapterData.get(position);
        if(item!=null){
            //set data from item to ui
            String contactInfo = "";
            for(int a=0; a<item.getRecipients().size(); a++){
                ContactData contactData = ContactFunction.getPhoneContactInfo(context, item.getRecipients().get(a).phoneNumber());
                if(contactData != null){
                    contactInfo += contactData.displayName + ";";
                }
            }
            viewHolder.tvRecipient.setText(contactInfo);
            viewHolder.tvMsg.setText(item.getSch_msg());
            viewHolder.tvInfo.setText("Menyusul ya pak");
        }

        return view;
    }
}

/* sz - comment:
/////ADAPTER BIASA//////
*/

//public class ScheduledAdapter extends ArrayAdapter {
//    private Context myContext;
//    private ArrayList<Table_Scheduled> scheduled = new ArrayList<>();
//
//    public ScheduledAdapter(Context context) {
//        super(context, 0);
//        myContext = context;
//    }
//
//    public void setScheduledAdapterObjects(ArrayList<Table_Scheduled> scheduled){
//        this.scheduled = scheduled;
//        notifyDataSetChanged();
//    }
//
//    class ViewHolder{
//        ImageView iv;
//        TextView tvRecipient, tvMsg, tvInfo;
//    }
//
//    @Override
//    public int getCount(){
////        return (scheduled.size());
//        return 100;
//    }
//
//    @Override
//    public long getItemId(int position){
////        return scheduled.get(position).getSch_id();
//        return 0;
//    }
//
//    ViewHolder viewHolder;
//    @Override
//    public View getView(int position, View view, ViewGroup parent) {
//
//        if(view==null){
//            LayoutInflater inflater = ((Activity)myContext).getLayoutInflater();
//            view = inflater.inflate(R.layout.scheduledadapter_item, null);
//
//            viewHolder = new ViewHolder();
//            viewHolder.iv = (ImageView)view.findViewById(R.id.iv);
//            viewHolder.tvRecipient = (TextView)view.findViewById(R.id.tvRecipient);
//            viewHolder.tvMsg = (TextView)view.findViewById(R.id.tvMsg);
//            viewHolder.tvInfo = (TextView)view.findViewById(R.id.tvInfo);
//
//            view.setTag(viewHolder);
//        } else {
//            viewHolder = (ViewHolder)view.getTag();
//        }
//
//        /*dummy*/
//        if(position%2==0) {
//            ContactData data = ContactFunction.getPhoneContactInfo(getContext(), "085710899789");
//            viewHolder.tvRecipient.setText(data.displayName);
//            Glide.with(getContext()).load(data.photo).asBitmap().placeholder(R.drawable.pp).dontAnimate().centerCrop().into(new BitmapImageViewTarget(viewHolder.iv) {
//                @Override
//                protected void setResource(Bitmap resource) {
//                    RoundedBitmapDrawable circularBitmapDrawable =
//                            RoundedBitmapDrawableFactory.create(getContext().getResources(), resource);
//                    circularBitmapDrawable.setCircular(true);
//                    viewHolder.iv.setImageDrawable(circularBitmapDrawable);
//                    viewHolder.iv.refreshDrawableState();
//                    viewHolder.iv.invalidate();
//                }
//            });
//        } else {
//            Glide.with(getContext()).load(R.drawable.pp).asBitmap().placeholder(R.drawable.pp).dontAnimate().centerCrop().into(new BitmapImageViewTarget(viewHolder.iv) {
//                @Override
//                protected void setResource(Bitmap resource) {
//                    RoundedBitmapDrawable circularBitmapDrawable =
//                            RoundedBitmapDrawableFactory.create(getContext().getResources(), resource);
//                    circularBitmapDrawable.setCircular(true);
//                    viewHolder.iv.setImageDrawable(circularBitmapDrawable);
//                    viewHolder.iv.refreshDrawableState();
//                    viewHolder.iv.invalidate();
//                }
//            });
//        }
//
////        Table_Scheduled item = scheduled.get(position);
////        if(item!=null){
////            //set data from item to ui
////            viewHolder.tvInfo.setText(item.getSkr_no_form());
////        }
//
//        return view;
//    }
//}

