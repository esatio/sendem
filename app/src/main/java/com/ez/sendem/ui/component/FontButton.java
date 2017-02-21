package com.ez.sendem.ui.component;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.TextView;

public class FontButton extends Button {

    public FontButton(Context context) {
        super(context);
        setFont(context);
    }

    public FontButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFont(context);
    }

    public FontButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setFont(context);
    }

    private void setFont(Context context){
        try {
            Typeface font = Typeface.createFromAsset(context.getAssets(), "fonts/fontawesome-webfont.ttf");
            setTypeface(font);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
