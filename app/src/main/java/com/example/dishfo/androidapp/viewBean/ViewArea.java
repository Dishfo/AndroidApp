package com.example.dishfo.androidapp.viewBean;

import com.example.dishfo.androidapp.sqlBean.Area;
import com.example.dishfo.androidapp.sqlBean.FollowArea;

import java.io.Serializable;

/**
 *
 * Created by dishfo on 18-3-23.
 */

public class ViewArea implements Serializable{
    private Area area;
    private FollowArea followArea;

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public FollowArea getFollowArea() {
        return followArea;
    }

    public void setFollowArea(FollowArea followArea) {
        this.followArea = followArea;
    }
}
