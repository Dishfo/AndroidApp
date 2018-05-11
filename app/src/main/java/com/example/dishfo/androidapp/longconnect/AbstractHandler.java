package com.example.dishfo.androidapp.longconnect;

import com.example.dishfo.androidapp.longconnect.bean.MessageHandler;

/**
 *
 * Created by dishfo on 18-4-3.
 */

public abstract class AbstractHandler implements MessageHandler {
    protected volatile boolean active;

    protected boolean check(Object message) {
        return message != null && active;
    }



}
