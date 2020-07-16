package com.xz.serialport_tool;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.os.Bundle;
import android.serialport.SerialPort;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends SerialPortActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onDataReceived(byte[] buffer, int size) {
        Log.d("xz", "fixId: " + new String(buffer, 0, size).trim());

    }


}
