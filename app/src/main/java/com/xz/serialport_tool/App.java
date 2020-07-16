package com.xz.serialport_tool;

import android.app.Application;
import android.serialport.SerialPort;

import java.io.IOException;

/**
 * @author czr
 * @date 2020/7/16
 */
public class App extends Application {
    private SerialPort mSerialPort;


    public SerialPort connSerialPort(String path, int baudrate) throws IOException {
        if (mSerialPort == null) {
            mSerialPort = SerialPort
                    .newBuilder(path, baudrate) // 串口地址地址，波特率
                    .parity(2) // 校验位；0:无校验位(NONE，默认)；1:奇校验位(ODD);2:偶校验位(EVEN)
                    .dataBits(7) // 数据位,默认8；可选值为5~8
                    .stopBits(2) // 停止位，默认1；1:1位停止位；2:2位停止位
                    .build();
        }
        return mSerialPort;
    }

    public void closeSerialPort() {
        if (mSerialPort != null) {
            mSerialPort.close();
            mSerialPort = null;
        }
    }

}
