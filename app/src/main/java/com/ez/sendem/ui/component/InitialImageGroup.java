package com.ez.sendem.ui.component;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.ez.sendem.R;

public class InitialImageGroup extends FrameLayout{

    public RelativeLayout layout_1, layout_2, layout_3;
    private InitialImage initialimage_1_1, initialimage_2_1, initialimage_2_2,
            initialimage_3_1, initialimage_3_2, initialimage_3_3;
    private String[] value;

    public InitialImageGroup(Context context) {
        super(context);
        setLayout(context);
    }

    public InitialImageGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        setLayout(context);
    }

    public InitialImageGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setLayout(context);
    }

    private void setLayout(Context context){
        LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.initialimagegroup, this);
        layout_1 = (RelativeLayout) view.findViewById(R.id.layout_1);
        initialimage_1_1 = (InitialImage)view.findViewById(R.id.initialimage_1_1);

        layout_2 = (RelativeLayout) view.findViewById(R.id.layout_2);
        initialimage_2_1 = (InitialImage)view.findViewById(R.id.initialimage_2_1);
        initialimage_2_2 = (InitialImage)view.findViewById(R.id.initialimage_2_2);

        layout_3 = (RelativeLayout) view.findViewById(R.id.layout_3);
        initialimage_3_1 = (InitialImage)view.findViewById(R.id.initialimage_3_1);
        initialimage_3_2 = (InitialImage)view.findViewById(R.id.initialimage_3_2);
        initialimage_3_3 = (InitialImage)view.findViewById(R.id.initialimage_3_3);
    }

    public void setValue(String[] value){
        if(value.length == 1){
            layout_1.setVisibility(View.VISIBLE);
            layout_2.setVisibility(View.GONE);
            layout_3.setVisibility(View.GONE);

            initialimage_1_1.setCircle(3, value[0]);
        } else if(value.length == 2){
            layout_1.setVisibility(View.GONE);
            layout_2.setVisibility(View.VISIBLE);
            layout_3.setVisibility(View.GONE);

            initialimage_2_1.setCircle(4, value[0]);
            initialimage_2_2.setCircle(5, value[1]);
        } else if(value.length >= 3){
            layout_1.setVisibility(View.GONE);
            layout_2.setVisibility(View.GONE);
            layout_3.setVisibility(View.VISIBLE);

            initialimage_3_1.setCircle(6, value[0]);
            initialimage_3_2.setCircle(7, value[1]);
            initialimage_3_3.setCircle(8, value[2]);
        }
    }
}
