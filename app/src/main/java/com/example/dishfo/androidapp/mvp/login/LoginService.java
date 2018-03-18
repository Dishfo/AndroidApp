package com.example.dishfo.androidapp.mvp.login;

import com.google.gson.JsonObject;

import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by dishfo on 18-1-26.
 */

public interface LoginService {
    @POST("userServlet.jsp")
    @FormUrlEncoded
    public Observable<JsonObject> login(@Field("action") String action,
                                        @Field("json") String json);
}
