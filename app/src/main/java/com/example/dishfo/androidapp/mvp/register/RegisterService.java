package com.example.dishfo.androidapp.mvp.register;

import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by dishfo on 18-1-24.
 */

public interface RegisterService {

    @POST("emailServlet.jsp")
    @FormUrlEncoded
    Observable<HashMap<String,Object>> sendEmail(@Field("action") String action,@Field("json") String json);

    @POST("userServlet.jsp")
    @FormUrlEncoded
    Observable<HashMap<String,Object>> setPassword(@Field("action") String action,
                                                   @Field("json") String json,
                                                   @Field("verificationCode") String code);

}
