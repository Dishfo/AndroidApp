package com.example.dishfo.androidapp.longconnect;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.extensions.IExtension;
import org.java_websocket.protocols.IProtocol;
import org.java_websocket.protocols.Protocol;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;

public class LongConService extends Service {
    private static String url="ws://172.16.0.121:8080/test/websocket";
    private static UserClient client;
    private static boolean opened=false;

    public LongConService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }
    public static synchronized boolean isConnection(){
        if(client==null){
            return false;
        }else {
            return client.isConnecting();
        }
    }

    public static synchronized void connect(){
        initClient();
        if(client!=null&&!client.isConnecting()){
            client.connect();
        }
    }


    public static synchronized void close(){
        if(client!=null){
            client.closeNormal();
        }

    }

//    public static synchronized void setOpen(boolean open){
//        WebSocketService.opened=open;
//    }

//    public void starTimer(){
//        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
//
//        int temsec = 10* 1000;   // 这是一小时的毫秒数
//        int onsec = 5*1000;
//
//        long triggerAtTime = SystemClock.elapsedRealtime() + onsec;
//        Intent i = new Intent(this, AlarmRecevier.class);
//        PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
//
//        manager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, onsec,temsec, pi);
//    }
//
//    public void stopTimer(){
//        AlarmManager manager= (AlarmManager) getSystemService(ALARM_SERVICE);
//        Intent i = new Intent(this, AlarmRecevier.class);
//        PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
//        manager.cancel(pi);
//
//    }

    public static void send(String msg){
        client.send(msg);
    }

    public static synchronized boolean isClosed(){
        return opened;
    }

    private static void initClient(){
        ArrayList<IProtocol> protocols = new ArrayList<IProtocol>();
        protocols.add(new Protocol("ocpp2.0"));
        protocols.add(new Protocol(""));
        Draft_6455 draft_ocppAndFallBack = new Draft_6455(Collections.<IExtension>emptyList(), protocols);
        try {
            client = new UserClient(new URI(url),draft_ocppAndFallBack);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        close();
    }
}
