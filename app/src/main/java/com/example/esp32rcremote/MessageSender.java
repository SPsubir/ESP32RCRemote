package com.example.esp32rcremote;

import android.os.AsyncTask;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import javax.xml.transform.Result;
public class MessageSender extends AsyncTask<String,Void,Void>{
    Socket s;
    PrintWriter pw;
    String ip="192.168.43.127";
    int port=1999;
    MessageSender(String ip1,int port1) {
        this.ip=ip1;
        this.port=port1;
    }
    protected Void doInBackground (String... voids){
        String message=voids[0];
        try{
            //s=new Socket(ip,port);
            s=new Socket(ip,port);
            pw=new PrintWriter(s.getOutputStream());
            pw.write(message);
            pw.flush();
            pw.close();
            s.close();
        }catch(IOException e){
            e.printStackTrace();
        }

        return null;
    }
}
