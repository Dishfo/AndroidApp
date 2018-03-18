package com.example.dishfo.androidapp.data.message;

import android.provider.BaseColumns;

/**
 * Created by dishfo on 18-3-17.
 */

public class MessagedbContract implements BaseColumns{
    public static final String TABLE_NAME="message";
    public static final String EMAIL_COLUMN="email";
    public static final String MESSAGE_COLUMN="message";
    public static final String NAME_COLUMN="name";
    public static final String TIME_COLUMN="time";
    public static final String SEND_COLUMN="sendUser";
}
