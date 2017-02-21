package com.ez.sendem.ui;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.ez.sendem.R;
import com.ez.sendem.ui.component.FontTextView;

public class RootToolbar extends RootAct implements RootFrag.OnFragmentInteractionListener{

    protected Toolbar toolbar;
    protected FontTextView fontTextViewRight;
    private TextView tvTitle;
    private FrameLayout content;
    //test ubah
    // test lagi
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.roottoolbar);

        content = (FrameLayout)findViewById(R.id.content);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fontTextViewRight = (FontTextView)findViewById(R.id.fontTextViewRight);
        fontTextViewRight.setVisibility(View.GONE);
        tvTitle = (TextView)findViewById(R.id.tvTitle);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNavigationClickListener();
            }
        });
    }

    public void setTitle(int strResId){
        tvTitle.setText(strResId);
    }

    public void setTitle(String title){
        tvTitle.setText(title);
    }

    public void showCheckIcon(View.OnClickListener listener){
        fontTextViewRight.setVisibility(View.VISIBLE);
        fontTextViewRight.setOnClickListener(listener);
    }

    protected void onNavigationClickListener(){
        finish();
    }

    @Override
    public void setContentView(int layoutResID){
        content.addView(getLayoutInflater().inflate(layoutResID, null));
    }

    @Override
    public void setContentView(View view){
        content.addView(view);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
