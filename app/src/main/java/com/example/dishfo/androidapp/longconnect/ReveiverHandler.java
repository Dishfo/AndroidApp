package com.example.dishfo.androidapp.longconnect;

import android.os.Handler;
import android.os.Looper;

import com.example.dishfo.androidapp.bean.sqlBean.Message;
import com.example.dishfo.androidapp.bean.sqlBean.Talk;
import com.example.dishfo.androidapp.longconnect.bean.InstanceMessage;
import com.example.dishfo.androidapp.longconnect.bean.MessageHandler;
import com.example.dishfo.androidapp.mvp.ModelManager;
import com.example.dishfo.androidapp.mvp.message.MessageModelImpl;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * 用于处理接受消息的回调
 * Created by dishfo on 18-3-16.
 */

class ReveiverHandler extends Handler {

    private LinkedHashMap<Class,MessageHandler> handlers;

    ReveiverHandler() {
        super(Looper.getMainLooper());
        handlers=new LinkedHashMap<>();
        handlers.put(MessageHandler.class,message -> {
            MessageModelImpl model=ModelManager.INSTANCE.getMessageModel();
            InstanceMessage bean=new Gson().fromJson((String) message,InstanceMessage.class);
            try {
                return model.saveInstanceMessage(bean);
            } catch (IOException e) {
                return null;
            }
        });

        handlers.put(Talk.class,message -> {
           Message arg= (Message) message;
           ModelManager.INSTANCE.getTalkModel().saveMessage(arg);
           return message;
        });

    }

    void onRecevier(String message){
        Set<Map.Entry<Class,MessageHandler>> set=handlers.entrySet();
        Object arg=message;
        for(Map.Entry entry:set){
            MessageHandler handler= (MessageHandler) entry.getValue();
            arg=handler.dispatchMessage(arg);
        }

    }

    void addHandler(MessageHandler handler){
        handlers.put(handler.getClass(),handler);
    }

    void removeHandler(Class key){
        handlers.remove(key);
    }
}
