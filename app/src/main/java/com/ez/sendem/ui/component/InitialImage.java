package com.ez.sendem.ui.component;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ez.sendem.R;

import java.util.Random;

public class InitialImage extends RelativeLayout{
    /*
    circle-1 = #f72222
    circle-2 = #f7228b
    circle-3 = #c722f7
    circle-4 = #7222f7
    circle-5 = #228bf7
    circle-6 = #22f76d
    circle-7 = #9ff722
    circle-8 = #f7cc22
    circle-9 = #f78622
    circle-10 = #f73122
    */

    private ViewGroup layout_circle;
    private TextView tv;

    public InitialImage(Context context) {
        super(context);
        setLayout(context);
    }

    public InitialImage(Context context, AttributeSet attrs) {
        super(context, attrs);
        setLayout(context);
    }

    public InitialImage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setLayout(context);
    }

    private void setLayout(Context context){
        LayoutInflater inflater = (LayoutInflater)
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.initialimage, this);
        layout_circle = (ViewGroup)view.findViewById(R.id.layout_circle);
        tv = (TextView) view.findViewById(R.id.tv);
    }

    public void setCircle(String text) {
        Random random = new Random();
        int result = random.nextInt(10);
        layout_circle.setBackgroundResource(this.getResources().getIdentifier("circle_" + result, "drawable", getContext().getPackageName()));
        tv.setText(text);
    }

    public void setCircle(int position, String text) {
        layout_circle.setBackgroundResource(this.getResources().getIdentifier("circle_" + position, "drawable", getContext().getPackageName()));
        tv.setText(text);
    }
}
