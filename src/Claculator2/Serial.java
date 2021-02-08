/*
 * Copyright (C) 2021 Admin
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package Claculator2;
import com.fazecast.jSerialComm.*;
import java.io.*;

/**
 *
 * @author Admin
 */
public class Serial implements Runnable{
    
    private SerialPort printerPort;
    private SerialPort [] ports;
    private boolean connected = false;
    InputStream in;
    byte[] readBuffer;
    int numRead;
    
    
    public Serial() {}
    
    public void sendCommand(String string) 
    {
        byte [] test = translateStringToByte(string + "\n");
        int status = printerPort.writeBytes(test, test.length);
        System.out.println("Status: " + status);
    }
    
    public boolean getConnectionStatus()
    {
        return connected;
    }
    public void disconnect() 
    {
        try {
            printerPort.closePort();
            connected = false;
            System.err.println("Disconnected!");
        } 
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("No Port to close!");
        }
    }
    
    public String getSerialData()
    {
        try 
        {
            while (printerPort.bytesAvailable() == 0) {
//                        System.err.println("Sleeping");
                Thread.sleep(20);
            }
            readBuffer = new byte[printerPort.bytesAvailable()];
            numRead = printerPort.readBytes(readBuffer, readBuffer.length); // liest und löscht die bytesAvailable
            try {
                return new String(readBuffer,0,numRead);
            } catch (java.util.IllegalFormatCodePointException e) {
                System.out.println("Only jibberish... can't translate. Mayabe wrong baudrate?");
            }

        } 
        catch (NegativeArraySizeException e) {System.err.println("No Connection or Port set!");}
        catch (Exception e) {e.printStackTrace();}
        return "Error Reading";
    }
    
    public void connect(SerialPortDataListener listener) 
    {   
        try {
            printerPort.openPort();
            printerPort.addDataListener(listener);
            connected = true;
            
            System.err.println(printerPort.isOpen());
            System.err.println("Connected!");
        } 
        catch (Exception e) {
            System.out.println("No Ports selected!");
            connected = false;
        }
    }
    public SerialPort[] getPorts()
    {
        ports = SerialPort.getCommPorts();
        return ports;
    }
    public String[] getPortsAsStrings()
    {
        if (SerialPort.getCommPorts().length > 0)
        {
            String [] portString = new String[SerialPort.getCommPorts().length];
            for(int i = 0; i < SerialPort.getCommPorts().length; i++)
            {
                portString[i] = SerialPort.getCommPorts()[i].getSystemPortName();
            }
            return portString;
        }
        else
        {
            String [] portString = {"no Com!"};
            return portString;
        }
    }
    public void setPort(int i)
    {
        try {
            
            printerPort = SerialPort.getCommPorts()[i];
        } catch (Exception e) {
            System.out.println("No COM Ports available!");
        }
    }
    
    public void setBaudRate(int baudRate)
    {
        try {
            printerPort.setBaudRate(baudRate);
            
        } catch (Exception e) {
            System.out.println("No COM Port set to change Baudrate!");
        }
    }

    public String translateByteToString(byte[] buffer) {
        String temp = "";
        for(int i = 0; i < buffer.length;i++) 
        {
            temp += String.format("%c",buffer[i]); 
        }
        return temp;
    }
    
    public byte[] translateStringToByte(String buffer) 
    {
        byte[] temp = new byte[buffer.length()];
        temp = buffer.getBytes();
        return temp;
    }

    public void printPortsInfo() {
        
        SerialPort [] list = SerialPort.getCommPorts();
        
        for(int i = 0; i<list.length; i++)
        {
            
            System.out.println(list[i].getSystemPortName());
            System.out.println(" |-> "+ list[i].getDescriptivePortName());
            System.out.println(" |-> "+ list[i].getPortDescription());
            System.out.println(" |-> ReadBuffer Size: "+ list[i].getDeviceReadBufferSize());
            System.out.println(" |-> WriteBuffer Size: "+ list[i].getDeviceWriteBufferSize());
        }
    }
    public void printPorts() {
        
        for(String port : this.getPortsAsStrings())
        {
            System.out.println("Available Ports:");
            System.out.println(port);
        }
    }
    
    public void run()
    {
//        System.out.println("Thread: " + Thread.activeCount());
//        System.out.println(Thread.currentThread().getName());
//        System.out.println(Thread.currentThread().getPriority());
//        while(true)
//        {
//            if(printerPort.isOpen() && this.getConnectionStatus())
//            {
//                try 
//                {
//                    while (printerPort.bytesAvailable() == 0) {
////                        System.err.println("Sleeping");
//                        Thread.sleep(1000);
//                    }
//                    readBuffer = new byte[printerPort.bytesAvailable()];
//                    numRead = printerPort.readBytes(readBuffer, readBuffer.length); // liest und löscht die bytesAvailable
//                    
//                    try {
////                      System.out.println("Bytes received: "+ numRead + "\n" + "Message:\n" + translateByteToString(readBuffer).strip());
////                        System.out.println(translateByteToString(readBuffer).strip());
////                        System.err.println(new String(readBuffer, 0, numRead));
//                        
//                    } catch (java.util.IllegalFormatCodePointException e) {
//                        System.out.println("Only jibberish... can't translate. Mayabe wrong baudrate?");
//                    }
//
//                } 
//                catch (NegativeArraySizeException e) {System.err.println("No Connection or Port set!");}
//                catch (Exception e) {e.printStackTrace();}
//            }
//        }
    }
}

