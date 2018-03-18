package com.example.dishfo.androidapp.bean;

import java.io.Serializable;
import java.util.SimpleTimeZone;

/**
 * Created by dishfo on 18-3-8.
 */

public class UserInfo implements Serializable{
    public  String email;
    public String name;
    public String head;
    public boolean isOne;
    public boolean isFollow;
    public String followId;
}
