package com.radiosonda.serialrx;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortEvent;
import com.fazecast.jSerialComm.SerialPortPacketListener;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.function.Consumer;
import java.util.stream.Stream;



public class SimpleSerialPort {

    private volatile  SerialPort commPort;
    private volatile OutputStream outputStream;

    public static String[] getPorts() {
        Stream<SerialPort> stream = Arrays.stream(SerialPort.getCommPorts());
        return stream
                .map((port) -> port.getSystemPortName())
                .toArray(String[]::new);
    }

    public SimpleSerialPort(String portName) throws UnsupportedEncodingException {
        this.commPort = SerialPort.getCommPort(portName);
        this.commPort.setBaudRate(9600);
    }

    public void write(String s) throws IOException {
        if(!commPort.isOpen()){
            commPort.openPort();
            outputStream = commPort.getOutputStream();
        }
        
        char[] chars = s.toCharArray();

        for (char c : chars) {
            this.outputStream.write(c);
        }
    }

    public void setOnDataReceivedListener(Consumer<Character> callback) {
        this.commPort.addDataListener(new SerialPortPacketListener() {
            @Override
            public int getPacketSize() {
                return 1;
            }

            @Override
            public int getListeningEvents() {
                return SerialPort.LISTENING_EVENT_DATA_RECEIVED;
            }

            @Override
            public void serialEvent(SerialPortEvent event) {
                char c = (char) event.getReceivedData()[0];
                System.out.println("at reciver" + c);
                callback.accept(c);
            }
        });

        System.out.println(commPort.isOpen());
        if(!commPort.isOpen()){
            commPort.openPort();
            System.out.println(commPort.isOpen());
        }
    }

    public void close(){
        this.commPort.closePort();
    }
}
