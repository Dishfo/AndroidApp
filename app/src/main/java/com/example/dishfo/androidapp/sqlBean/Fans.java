package com.example.dishfo.androidapp.sqlBean;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

/**
 * Created by dishfo on 18-3-20.
 */
@Entity
public class Fans implements Serializable {
    @PrimaryKey
    private String id;
}
