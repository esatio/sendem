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
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.ez.sendem.R;
import com.ez.sendem.adapter.object.AppTypeData;

import java.util.ArrayList;

public class AppTypeAdapter extends ArrayAdapter {
    private Context myContext;
    private AppTypeData[] data = {};

    public AppTypeAdapter(Context context, AppTypeData[] data) {
        super(context, 0);
        myContext = context;
        this.data = data;
    }

    class ViewHolder{
        ImageView iv;
        TextView tvAppName;
    }

    @Override
    public int getCount(){
        return (data.length);
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    ViewHolder viewHolder;
    @Override
    public View getView(int position, View view, ViewGroup parent) {

        if(view==null){
            LayoutInflater inflater = ((Activity)myContext).getLayoutInflater();
            view = inflater.inflate(R.layout.apptypeadapter_item, null);

            viewHolder = new ViewHolder();
            viewHolder.iv = (ImageView)view.findViewById(R.id.iv);
            viewHolder.tvAppName = (TextView)view.findViewById(R.id.tvAppName);

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder)view.getTag();
        }

        AppTypeData item = data[position];
        if(item!=null){
            Glide.with(getContext()).load(R.drawable.pp).asBitmap().centerCrop().into(new BitmapImageViewTarget(viewHolder.iv) {
                @Override
                protected void setResource(Bitmap resource) {
                    RoundedBitmapDrawable circularBitmapDrawable =
                            RoundedBitmapDrawableFactory.create(getContext().getResources(), resource);
                    circularBitmapDrawable.setCircular(true);
                    viewHolder.iv.setImageDrawable(circularBitmapDrawable);
                }
            });

            viewHolder.tvAppName.setText(item.appName);
        }
//        Table_Scheduled item = scheduled.get(position);
//        if(item!=null){
//            //set data from item to ui
//            viewHolder.tvInfo.setText(item.getSkr_no_form());
//        }

        return view;
    }
}