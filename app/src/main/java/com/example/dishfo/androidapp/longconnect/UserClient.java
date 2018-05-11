package com.example.dishfo.androidapp.longconnect;

import android.util.Log;

import com.example.dishfo.androidapp.longconnect.bean.InstanceMessage;
import com.example.dishfo.androidapp.mvp.ModelManager;
import com.google.gson.Gson;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

/**
 *
 * Created by dishfo on 18-3-15.
 */

public class UserClient extends WebSocketClient{


    /**
     * close code
     */

    private static final int NORMAL=0X3000;


    private static final String TAG="websocket";

    private ReveiverHandler handler;

    UserClient(URI serverUri, Draft protocolDraft) {
        super(serverUri, protocolDraft);
        handler=new ReveiverHandler();
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        Log.d(TAG,"open the websocket");
        InstanceMessage message = new InstanceMessage();
        message.setFrom(ModelManager.INSTANCE.getLoginModel().getCurrentUser().getEmail());
        message.setTo("server");
        message.setMessage("connecting");
        send(new Gson().toJson(message));
    }

    @Override
    public void onMessage(String message) {
        handler.onRecevier(message);
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
    }

    @Override
    public void onError(Exception ex) {

    }

    void closeNormal(){
        if(!isClosed()&&!isClosing()){
            close(NORMAL);
        }
    }

    public void addHandler(AbstractHandler handler){
        this.handler.addHandler(handler);
    }

    public void removeHandler(Class key,boolean delete){
        this.handler.removeHandler(key,delete);
    }

}
