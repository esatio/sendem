package com.ez.sendem.ui.component;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class FontTextView extends TextView {

    public FontTextView(Context context) {
        super(context);
        setFont(context);
    }

    public FontTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFont(context);
    }

    public FontTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setFont(context);
    }

    private void setFont(Context context){
        Typeface font = Typeface.createFromAsset(context.getAssets(), "fonts/fontawesome-webfont.ttf" );
        setTypeface(font);
    }
}
