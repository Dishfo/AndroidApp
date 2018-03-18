package com.example.dishfo.androidapp.data.talk;

import android.provider.BaseColumns;

/**
 * Created by dishfo on 18-3-17.
 */

public class TalkdbContract implements BaseColumns{

    public static final String TABLE_NAME="talk";
    public static final String EMAIL_COLUMN="email";
    public static final String MESSAGE_COLUMN="message";
    public static final String OTHER_COLUMN="other";
    public static final String TIME_COLUMN="time";
    public static final String OTHERUSER_COLUMN="otherUser";
}
