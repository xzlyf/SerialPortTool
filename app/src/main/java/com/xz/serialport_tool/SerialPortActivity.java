package com.xz.serialport_tool;

import android.app.Application;
import android.os.Bundle;
import android.serialport.SerialPort;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author czr
 * @date 2020/7/16
 */
public abstract class SerialPortActivity extends AppCompatActivity {
    protected App mApplication;
    protected SerialPort mSerialPort;
    private InputStream mInputStream;

    private ReadThread mReadThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApplication = (App) getApplication();

        try {
            mSerialPort = mApplication.connSerialPort("/dev/ttyS4", 9600);
            mInputStream = mSerialPort.getInputStream();
            mReadThread = new ReadThread();
            mReadThread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    private class ReadThread extends Thread {

        @Override
        public void run() {
            super.run();
            int bytes;
            int ch;//读取字符串变量
            byte[] buffer;
            while (!interrupted()) {

                try {
                    if (mInputStream == null)
                        return;
                    //处理读取
                    buffer = new byte[64];
                    bytes = 0;
                    while ((ch = mInputStream.read()) != '\n') {
                        if (ch != -1) {
                            buffer[bytes] = (byte) ch;
                            bytes++;
                        }
                    }
                    buffer[bytes] = '\n';
                    bytes++;
                    if (bytes > 0) {
                        onDataReceived(buffer, bytes);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }


    /*  private class ReadThread extends Thread {

        @Override
        public void run() {
            super.run();
            while (!isInterrupted()) {
                int size;
                try {
                    byte[] buffer = new byte[64];
                    if (mInputStream == null) return;
                    size = mInputStream.read(buffer);
                    if (size > 0) {
                        onDataReceived(buffer, size);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }
            }
        }
    }
*/
    protected abstract void onDataReceived(byte[] buffer, int size);


    @Override
    protected void onDestroy() {
        if (mReadThread != null) mReadThread.interrupt();
        mApplication.closeSerialPort();
        mSerialPort = null;
        super.onDestroy();
    }
}