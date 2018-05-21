package com.example.dishfo.androidapp.bean.sqlBean;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.io.Serializable;

/**
 * Created by dishfo on 18-3-20.
 */
@Entity
public class Fans implements Serializable {
    @PrimaryKey
    @NonNull
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
