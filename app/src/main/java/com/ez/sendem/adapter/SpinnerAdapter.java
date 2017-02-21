package com.ez.sendem.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ez.sendem.R;

public class SpinnerAdapter extends ArrayAdapter<String>{
	
	public SpinnerAdapter(Context context, String[] choice) {
		super(context, 0, choice);
	}
	
	@Override
	public TextView getView(int position, View convertView, ViewGroup parent) {
		//tampilan spinner(drop down belum muncul)
		if(convertView==null){
			convertView = View.inflate(getContext(), R.layout.spinner_style, null);
		}
		TextView tv=(TextView)convertView;
		tv.setText(getItem(position));
		return tv;
	}
	
	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		//tampilan item list spinner
		TextView tv;
		if(convertView==null){
			convertView = View.inflate(getContext(), R.layout.spinner_dropdown_item, null);
		}
		tv = (TextView)convertView;
		tv.setText(getItem(position));
		return convertView;
	}

}
