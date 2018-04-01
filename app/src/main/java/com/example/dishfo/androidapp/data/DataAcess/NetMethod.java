package com.example.dishfo.androidapp.data.DataAcess;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.request.RequestOptions;
import com.example.dishfo.androidapp.GlideApp;
import com.example.dishfo.androidapp.GlideRequest;
import com.example.dishfo.androidapp.R;
import com.example.dishfo.androidapp.application.MyApplication;
import com.example.dishfo.androidapp.mvp.BaseModel;
import com.example.dishfo.androidapp.netInterface.JsonGenerator;
import com.example.dishfo.androidapp.util.JsonAction;
import com.example.dishfo.androidapp.util.PropertiesReader;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.Iterator;
import java.util.List;

import javax.inject.Singleton;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 提供部分的常用方法
 * Created by dishfo on 18-2-14.
 */
@Singleton
public class NetMethod {

    private String user;
    private String RESULT="result";
    public static  final NetMethod INSTANCE=new NetMethod();
    private JsonParser mParser;

    public void setUser(String user) {
        this.user = user;
    }

    public String getUser(){
        return user;
    }

    public NetMethod() {
        this.mParser = new JsonParser();
    }




    public int getResultSize(String result){
        JsonObject jsonObject=mParser.parse(result).getAsJsonObject();
        JsonArray array=jsonObject.get("result").getAsJsonArray();
        return array.size();
    }

    Observable<JsonObject> generateObserable(String action, JsonAction jaction, Object... args){
        Retrofit retrofit=NetMethod.INSTANCE.getRetrofitWithRx();
        JsonGenerator generator=new JsonGenerator();
        jaction.competeJson(generator,args);
        DataAccessService service=retrofit.create(DataAccessService.class);
        String json=generator.toJson();
        Log.d("test",json);
        return service.accessDatarx(action,json);
    }

    Call<JsonObject> generateCall(String action, JsonAction jaction, Object... args){
        Retrofit retrofit=NetMethod.INSTANCE.getRetrofitWithRx();
        JsonGenerator generator=new JsonGenerator();
        jaction.competeJson(generator,args);
        DataAccessService service=retrofit.create(DataAccessService.class);
        String json=generator.toJson();
        Log.d("test",json);
        return service.accessData(action,json);
    }

    public Retrofit getRetrofitWithRx(){
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(BaseModel.HOST)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        return retrofit;
    }

    public Retrofit getRetrofit(){
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(BaseModel.HOST)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit;
    }

    public String parseList(List<String> strs){
        StringBuilder builder=new StringBuilder("[");
        for(String s:strs){
            s.trim();
            if(builder.length()!=1){
                builder.append(",");
            }
            builder.append(s);

        }
        builder.append("]");
        return builder.toString();
    }




    public String getUrl(JsonObject object){
        String url=object.get("result").getAsString();
        String tmp="http://"+url;
        return tmp.replace("http://localhost:8080/test/", BaseModel.HOST);
    }

    public String getId(String result){
        String id=null;
        JsonObject jsonObject=mParser.parse(result).getAsJsonObject();
        JsonArray array=jsonObject
                .get(RESULT)
                .getAsJsonArray()
                .get(0)
                .getAsJsonObject()
                .get(RESULT)
                .getAsJsonArray();

        Iterator<JsonElement> elements=array.iterator();
        while (elements.hasNext()){
            JsonObject object=elements.next().getAsJsonObject();
            String name = object.get("name").getAsString();
            String realname =
                   PropertiesReader.strTurn(name, MyApplication.MAP);
            if (realname.endsWith("id")) {
                id = object.get("value").getAsString();
            }
        }
        return id;
    }

    public String getIdFromInsert(String result){
        String id=null;
        JsonObject jsonObject=mParser.parse(result).getAsJsonObject();
        JsonArray array=jsonObject
                .get("values")
                .getAsJsonArray();
        Iterator<JsonElement> elements=array.iterator();
        while (elements.hasNext()){
            JsonObject object=elements.next().getAsJsonObject();
            String name = object.get("name").getAsString();
            String realname =PropertiesReader.get(name,MyApplication.MAP);
            if (realname.endsWith("id")) {
                id = object.get("value").getAsString();
            }
        }
        return id;
    }

    public void useGlide(Context context, String url, RequestOptions options, ImageView view,int placeholder,int error){
        GlideRequest<Drawable> request= GlideApp.with(context).load(urlHandle(url));
        if(options!=null){
            request=request.apply(options);
        }
        if(placeholder!=-1){
            request=request.placeholder(placeholder);
        }
        if(error!=-1){
            request=request.error(error);
        }
        request.into(view);
    }

    public void useGlide(Context context, int res, RequestOptions options, ImageView view,int placeholder,int error){
        GlideRequest<Drawable> request= GlideApp.with(context).load(res);
        if(options!=null){
            request=request.apply(options);
        }
        if(placeholder!=-1){
            request=request.placeholder(placeholder);
        }
        if(error!=-1){
            request=request.error(error);
        }
        request.into(view);
    }

    public void useGlide(Context context,String url,ImageView view){
        useGlide(context,url,RequestOptions.circleCropTransform(),view, R.mipmap.placeholder,R.mipmap.placeholder);
    }

    public void useGlideWithoutCircle(Context context,String url,ImageView view){
        useGlide(context,url,null,view, R.mipmap.placeholder,R.mipmap.placeholder);
    }

    public void useGlideWithoutCircle(Context context,int res,ImageView view){
        useGlide(context,res,null,view, R.mipmap.placeholder,R.mipmap.placeholder);
    }


    private String urlHandle(String url){
        return url.replaceAll("localhost",BaseModel.HOST_IP);
    }

    private static final String CODE="code";

    boolean isSucceed(JsonObject object){
        JsonElement codeElement=object==null?null:object.get(CODE);
        int code=codeElement==null?-1:codeElement.getAsInt();
        if(code!=1){
            return false;
        }
        return true;
    }

    String getResult(JsonObject object){
        JsonElement element=object==null?null:object.get(RESULT);
        String json=element==null?"{}":element.getAsString();
        return json;
    }
}
