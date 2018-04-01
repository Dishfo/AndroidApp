package com.example.dishfo.androidapp.longconnect;

import android.util.Log;

import com.example.dishfo.androidapp.data.DataAcess.NetMethod;
import com.example.dishfo.androidapp.bean.MessageBean;
import com.google.gson.Gson;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

/**
 * Created by dishfo on 18-3-15.
 */

public class UserClient extends WebSocketClient{

    public static final String OPEN="open";
    public static final String MESSAGE="message";
    public static final String CLOSE="close";

    /**
     * close code
     */

    public static final int NORMAL=0X3000;


    private static final String TAG="websocket";
    private Throwable  throwable=null;


    public UserClient(URI serverUri, Draft protocolDraft) {
        super(serverUri, protocolDraft);
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        Log.d(TAG,"open the websocket");
        MessageBean bean=new MessageBean(NetMethod.INSTANCE.getUser(),null,null,MessageBean.USERSINFO);
        send(new Gson().toJson(bean));
    }

    @Override
    public void onMessage(String message) {
        Log.d(TAG,message);
        Log.d(TAG,"receiver message");
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        Log.d(TAG,"close the webscoket");
    }

    @Override
    public void onError(Exception ex) {
        throwable=ex;
    }

    public void closeNormal(){
        if(!isClosed()&&!isClosing()){
            close(NORMAL);
        }
    }

}
