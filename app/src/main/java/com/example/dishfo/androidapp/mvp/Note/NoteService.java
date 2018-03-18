package com.example.dishfo.androidapp.mvp.Note;

import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by dishfo on 18-2-28.
 */

public interface NoteService {

    @POST("dataServlet.jsp")
    @FormUrlEncoded
    public Observable<JsonObject> getDiscusses(@Field("action") String aciton, @Field("json") String json);
}
