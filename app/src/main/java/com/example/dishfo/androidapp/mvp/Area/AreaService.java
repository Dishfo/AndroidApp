package com.example.dishfo.androidapp.mvp.Area;

import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by dishfo on 18-2-14.
 */

public interface AreaService {
    @POST("dataServlet.jsp")
    @FormUrlEncoded
    public Observable<HashMap<String,Object>> loadReCommend(@Field("action") String aciton,@Field("json") String json);


}
