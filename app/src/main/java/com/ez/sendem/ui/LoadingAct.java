package com.ez.sendem.ui;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import com.ez.sendem.R;
import com.ez.sendem.function.ServiceFunction;
import com.ez.sendem.manager.PageManager;

import java.util.Timer;
import java.util.TimerTask;

/*
//comment : loadingact - 1
    1. semua activity harus extends RootAct agar settingan default jalan.
*/
public class LoadingAct extends RootAct {

    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 1;
    private static final int PERMISSIONS_REQUEST_SEND_SMS= 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*
        //comment : loadingact - 2
            1. set layout yang akan kita gunakan (yang sudah ada di folder res > layout)
        */
        setContentView(R.layout.loadingact);
    }

    @Override
    public void onResume(){
        super.onResume();

        /*
        //comment : loadingact - 3
            1. di function ini, lakukan apa yang harus dilakukan oleh class LoadingAct ini
            2. di class ini, kita perlu memastikan untuk android versi Marshmallow keatas, sudah allow permission untuk kategori permission yang dianggap berbahaya.
            3. untuk list kategori permission yang dianggap berbahaya bisa googling sendiri.
            4. pengecekan awal adalah readContact
        */
        checkPermission_readContact();
    }

    private void checkPermission_readContact() {
        // Check the SDK version and whether the permission is already granted or not.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, PERMISSIONS_REQUEST_READ_CONTACTS);
            //After this point you wait for callback in onRequestPermissionsResult(int, String[], int[]) overriden method
        } else {
            // Android version is lesser than 6.0 or the permission is already granted.
            /*
            //comment : loadingact - 4
                1. semua activity harus extends RootAct agar settingan default jalan.
            */
            checkPermission_sendSMS();
        }
    }

    private void checkPermission_sendSMS() {
        // Check the SDK version and whether the permission is already granted or not.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.SEND_SMS}, PERMISSIONS_REQUEST_SEND_SMS);
            //After this point you wait for callback in onRequestPermissionsResult(int, String[], int[]) overriden method
        } else {
            // Android version is lesser than 6.0 or the permission is already granted.
            openMainApp();
        }
    }

    /*
    //comment : loadingact - 5
        1. function dibawah ini untuk menerima balikan dari dialog allow permission
        2. untuk membedakan balikan dari dialog yang mana (read contact atau send sms), digunakan request code.
    */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted
                checkPermission_sendSMS();
            } else {
                Toast.makeText(this, "Until you grant the permission, we cannot display the names", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == PERMISSIONS_REQUEST_SEND_SMS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted
                openMainApp();
            } else {
                Toast.makeText(this, "Until you grant the permission, we cannot display the names", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void openMainApp(){
        /*
        //comment : loadingact - 6
            1. masuk ke halaman utama setelah 3 detik (hal ini optional saja). Diset 3 detik biar keliatan animasi di halaman login.
        */
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                /*
                //comment : loadingact - 7
                    1. jika aplikasi allow semua permission, baru boleh menjalankan background service dan masuk ke halaman utama
                */
                ServiceFunction.StartMyService(LoadingAct.this);
                PageManager.open_FirstPage(LoadingAct.this);
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, 3000);
    }
}
