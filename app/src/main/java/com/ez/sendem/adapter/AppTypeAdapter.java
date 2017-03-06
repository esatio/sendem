package com.ez.sendem.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ez.sendem.R;
import com.ez.sendem.adapter.object.AppTypeData;
import com.ez.sendem.ui.component.FontTextView;

public class AppTypeAdapter extends ArrayAdapter {
    private Context myContext;
    private AppTypeData[] data = {};

    public AppTypeAdapter(Context context, AppTypeData[] data) {
        super(context, 0);
        myContext = context;
        this.data = data;
    }

    class ViewHolder{
        FontTextView tvAppLogo;
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
            viewHolder.tvAppLogo = (FontTextView)view.findViewById(R.id.tvAppLogo);
            viewHolder.tvAppName = (TextView)view.findViewById(R.id.tvAppName);

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder)view.getTag();
        }

        AppTypeData item = data[position];
        if(item!=null){
            viewHolder.tvAppLogo.setText(item.pictureRes);
            viewHolder.tvAppName.setText(item.appName);
        }

        return view;
    }
}