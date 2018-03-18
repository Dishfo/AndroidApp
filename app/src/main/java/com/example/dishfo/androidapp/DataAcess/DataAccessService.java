package com.example.dishfo.androidapp.DataAcess;

import com.google.gson.JsonObject;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by dishfo on 18-3-1.
 */

public interface DataAccessService {
    @POST("dataServlet.jsp")
    @FormUrlEncoded
    public Observable<JsonObject> accessDatarx(@Field("action") String action, @Field("json") String josn);

    @POST("dataServlet.jsp")
    @FormUrlEncoded
    public Call<JsonObject> accessData(@Field("action") String action, @Field("json") String josn);

    @POST("fileServlet.jsp")
    @Multipart
    public Call<JsonObject> uploadFile(@Part("email")String email, @Part MultipartBody.Part body);

    @POST("fileServlet.jsp")
    @Multipart
    public Observable<JsonObject> uploadFilerx(@Part("email")String email, @Part MultipartBody.Part body);
}
