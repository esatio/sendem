package com.ez.sendem.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.ez.sendem.R;
import com.ez.sendem.manager.PageManager;

/*
note untuk class ini:
-. class ini adalah class parent dari aplikasi Send'em
-. class ini berguna untuk set semua settingan default aplikasi, meliputi: theme dan language
 */

/*
//comment : ui - 1
    1. Halaman di android dinamakan Activity dan dalam aplikasi ini extends AppCompatActivity (extends lain: Activity, etc)
    2. Untuk tau AppCompatActivity extends dari kelas apa (import android.support.v7.app.AppCompatActivity;),
        tinggal tekan ctrl+space
        Detail:
            -. coba hapus "import android.support.v7.app.AppCompatActivity;" dari atas (line - 6)
            -. kata-kata AppCompatActivity akan jadi warna merah
            -. klik di tulisan "AppCompatActivity" dan tekan ctrl_space
            -. akan muncul suggestion import
            -. jika ada lebih dari 1 import yang di-suggest, maka utamakan yang ada tulisan "v7"
    3. semua function yang mengandung kata "@Override" merupakan fungsi bawaan dari class AppCompatActivity
    4. function "onCreate, onStart, onResume, onStop" dinamakan "life cycle" dari aplikasi.
        List-nya disesuaikan dengan kebutuhan. Untuk list lengkapnya bisa googling sendiri.
    5. visibility untuk "life-cycle" disesuaikan dengan class yang di-extends
    6. Buat layout di folder res > layout dengan format xml
 */
public class RootAct extends AppCompatActivity{

    protected static Activity act;
    protected static Class currClass;

    /*
    //comment : ui - 3
        1. list semua id component (contoh: lytContent) dan tipenya (FrameLayout) di sini.
    */
    private FrameLayout lytContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        PageManager.setTheme(this);
        PageManager.setLanguage(this);

        //Visibility state for softInputMode: please always hide any soft input area when this window receives focus.
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        super.onCreate(savedInstanceState);
        act = this;

        /*
        //comment : ui - 2
            1. setContentView dengan layout yang mau dipakai.
        */
        super.setContentView(R.layout.rootact);

        /*
        //comment : ui - 4
            1. inisialisasi component yang di-list di point 3 disini.
                formatnya:
                (nama variable component) = (Tipe Component)findViewById(id component di file layout.xml);
         */
        lytContent = (FrameLayout)findViewById(R.id.lytContent);
    }

    @Override
    public void setContentView(int layoutResID){
        getLayoutInflater().inflate(layoutResID, lytContent);
    }

    @Override
    public void setContentView(View view){
        lytContent.addView(view);
    }

    @Override
    public void onStart(){
        super.onStart();
        act = this;
        currClass = getClass();
    }

    @Override
    public void onResume(){
        super.onResume();
        act = this;
        currClass = getClass();
    }

    @Override
    public void onStop(){
        super.onStop();
    }
}
