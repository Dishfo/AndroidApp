package com.example.dishfo.androidapp.longconnect;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.dishfo.androidapp.bean.sqlBean.Message;
import com.example.dishfo.androidapp.longconnect.bean.InstanceMessage;
import com.example.dishfo.androidapp.mvp.ModelManager;
import com.example.dishfo.androidapp.mvp.message.MessageModelImpl;
import com.google.gson.Gson;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * 用于处理接受消息的回调
 * 作为单例
 * Created by dishfo on 18-3-16.
 */



class ReveiverHandler extends Handler {

    private LinkedHashMap<Class, AbstractHandler> handlers;

    private final static Object LOCK = new Object();
    private HandlerThread thread;

    private Handler handler;


    ReveiverHandler() {
        super(Looper.getMainLooper());
        handlers = new LinkedHashMap<>();
        thread=new HandlerThread();
        thread.start();
        synchronized (LOCK) {
            addHandler(new FirstHandler());
            addHandler(new SecondHandler());
        }
    }

    void onRecevier(String message) {
        sendMesssage(message);
    }

    void addHandler(AbstractHandler handler) {
            if (handlers.containsValue(handler)) {
                handler.active = true;
            } else {
                sendMesssage(new QueueMessage<>(QueueMessage.ADD_HANDLER,handler));
            }
    }

    void removeHandler(Class key, boolean delete) {
        Log.d("handlerme","remove" + key);
        AbstractHandler handler;
        if((handler=handlers.get(key))!=null){
            handler.active=false;
        }
        if (delete) {
            sendMesssage(new QueueMessage<>(QueueMessage.DELETE_HANDLER,key));
        }
    }

    private void sendMesssage(Object msg){
        if(!thread.isReady){
            while (!thread.isReady);
        }
        android.os.Message message=handler.obtainMessage();
        message.obj=msg;
        handler.sendMessage(message);
    }

    private final class HandlerThread extends Thread{
        private Looper looper;
        private volatile boolean isReady=false;
        @Override
        public void run() {
            init();
            if(looper!=null){
                Looper.loop();
            }
        }

        private void init(){
            Looper.prepare();
            looper=Looper.myLooper();
            handler=new Handler(looper){
                @Override
                public void dispatchMessage(android.os.Message msg) {
                    super.dispatchMessage(msg);
                    Object arg;
                    if((arg=msg.obj)!=null){
                        if(arg instanceof QueueMessage){
                            handMessage((QueueMessage) arg);
                        }else {
                            handNetMessage(arg);
                        }
                    }
                }
            };
            isReady=true;
        }

        private void handNetMessage(Object message){
            Set<Map.Entry<Class, AbstractHandler>> set = handlers.entrySet();
            for (Map.Entry entry : set) {
                AbstractHandler handler = (AbstractHandler) entry.getValue();
                message = handler.dispatchMessage(message);
            }
        }

        private void handMessage(QueueMessage queueMessage){

            switch (queueMessage.getMessage()){
                case QueueMessage.DELETE_HANDLER:
                    handlers.remove(queueMessage.getArg());
                    break;
                case QueueMessage.ADD_HANDLER:
                    AbstractHandler handler= (AbstractHandler) queueMessage.getArg();
                    if(handler!=null){
                        handlers.put(handler.getClass(),handler);
                        handler.active=true;
                    }
                    break;
            }
        }

    }

    private final class FirstHandler extends AbstractHandler {
        @Override
        public Object dispatchMessage(Object message) {
            if (!check(message)) {
                return null;
            } else {
                MessageModelImpl model = ModelManager.INSTANCE.getMessageModel();
                try {
                    if (active) {
                        InstanceMessage bean =
                                new Gson().fromJson((String) message, InstanceMessage.class);
                        return model.saveInstanceMessage(bean);
                    } else {
                        return message;
                    }
                } catch (Throwable e) {
                    Log.d("handlerme",e.toString());
                    return null;
                }
            }
        }
    }

    private final class SecondHandler extends AbstractHandler {
        @Override
        public Object dispatchMessage(Object message) {
            if (!check(message)) {
                return null;
            } else if (!(message instanceof Message)) {
                return message;
            } else {
                if (active) {
                    Message arg = (Message) message;
                    ModelManager.INSTANCE.getTalkModel().saveMessage(arg);
                }
                return message;
            }
        }
    }


}
