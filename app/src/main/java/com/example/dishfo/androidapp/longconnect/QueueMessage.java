package com.example.dishfo.androidapp.longconnect;

/**
 *
 * Created by dishfo on 18-4-3.
 */

public class QueueMessage<T>{
    static final int DELETE_HANDLER=0X25;
    static final int ADD_HANDLER=0X26;

    private int msg;
    private T arg;

    public QueueMessage(int msg,T arg) {
        this.msg = msg;
        this.arg=arg;
    }


    public T getArg(){
        return arg;
    }
    public int getMessage(){
        return msg;
    }
}
