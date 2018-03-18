package com.example.dishfo.androidapp.DataAcess;

import android.util.Log;

import com.example.dishfo.androidapp.application.MyApplication;
import com.example.dishfo.androidapp.bean.AreaInfo;
import com.example.dishfo.androidapp.bean.DiscussInfo;
import com.example.dishfo.androidapp.bean.NoteInfo;
import com.example.dishfo.androidapp.bean.UserInfo;
import com.example.dishfo.androidapp.mvp.BaseModel;
import com.example.dishfo.androidapp.mvp.FieldConstant;
import com.example.dishfo.androidapp.mvp.TableConstant;
import com.example.dishfo.androidapp.mvp.TypeConstant;
import com.example.dishfo.androidapp.netInterface.JsonGenerator;
import com.example.dishfo.androidapp.netInterface.SelectAction.SelectClassNameAction;
import com.example.dishfo.androidapp.netInterface.SelectAction.SelectConditionAction;
import com.example.dishfo.androidapp.netInterface.SelectAction.SelectFieldsAction;
import com.example.dishfo.androidapp.netbean.AreaInfoMapping;
import com.example.dishfo.androidapp.netbean.DiscussInfoMapping;
import com.example.dishfo.androidapp.netbean.NoteInfoMapping;
import com.example.dishfo.androidapp.netbean.UserInfoMapping;
import com.example.dishfo.androidapp.util.JsonAction;
import com.example.dishfo.androidapp.util.PropertiesReader;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by dishfo on 18-2-14.
 */

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

    public NoteInfo parseNote(JsonElement element){
        NoteInfo noteInfo=new NoteInfo();
        Class noteInfoClass=  noteInfo.getClass();
        JsonArray array=element
                .getAsJsonObject()
                .get(RESULT)
                .getAsJsonArray();

        Iterator<JsonElement> elements=array.iterator();
        while (elements.hasNext()){
            JsonObject object=elements.next().getAsJsonObject();
            String name=object.get("name").getAsString();
            try {
                Field field=noteInfoClass.getField(NoteInfoMapping.INSTANCE.
                        get(PropertiesReader.strTurn(name, MyApplication.MAP)));
                field.setAccessible(true);
                Type type=field.getType();
                if(type.equals(String.class)){
                    field.set(noteInfo,object.get("value").getAsString());
                }else if(type.equals(List.class)){
                    List<String> tmplist=parseStringtoArray(object.get("value").getAsString());
                    field.set(noteInfo,tmplist);
                }
            } catch (NoSuchFieldException e) {
                Log.d("test",e.toString()+" "+name);
            } catch (IllegalAccessException e) {
                Log.d("test",e.toString()+" "+name);
            }
        }

        return noteInfo;
    }

    public List<DiscussInfo> praseDiscussList(String result){
        JsonObject jsonObject=mParser.parse(result).getAsJsonObject();
        JsonArray array=jsonObject.get(RESULT).getAsJsonArray();
        Iterator<JsonElement> elements=array.iterator();
        ArrayList<DiscussInfo> discussInfos=new ArrayList<>();
        while(elements.hasNext()){
            discussInfos.add(praseDiscuss(elements.next()));
        }
        return discussInfos;
    }

    public List<NoteInfo> praseNoteList(String result){
        JsonObject jsonObject=mParser.parse(result).getAsJsonObject();
        JsonArray array=jsonObject.get(RESULT).getAsJsonArray();
        Iterator<JsonElement> elements=array.iterator();
        ArrayList<NoteInfo> noteInfos=new ArrayList<>();
        while(elements.hasNext()){
            noteInfos.add(parseNote(elements.next()));
        }
        return noteInfos;
    }

    public AreaInfo praseArea(String result){
        AreaInfo areaInfo=new AreaInfo();
        return praseArea(result,areaInfo);
    }

    public AreaInfo praseArea(String result,AreaInfo areaInfo){
        Class areaInfoClass=areaInfo.getClass();
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
            String name=object.get("name").getAsString();
            try {
                Field field=areaInfoClass.getField(AreaInfoMapping.INSTANCE.
                        get(PropertiesReader.strTurn(name, MyApplication.MAP)));
                field.setAccessible(true);
                Type type=field.getType();
                if(type.equals(String.class)){
                    field.set(areaInfo,object.get("value").getAsString());
                }else if(type.equals(List.class)){
                    List<String> tmplist=NetMethod.INSTANCE.parseStringtoArray(object.get("value").getAsString());
                    field.set(areaInfo,tmplist);
                }
            } catch (NoSuchFieldException e) {
                Log.d("test",e.toString()+" "+name);
            } catch (IllegalAccessException e) {
                Log.d("test",e.toString()+" "+name);
            }
        }
        return areaInfo;
    }

    public DiscussInfo praseDiscuss(JsonElement element){
        DiscussInfo discussInfo=new DiscussInfo();
        Class discussInfoClass=discussInfo.getClass();

        JsonArray array=element
                .getAsJsonObject()
                .get(RESULT)
                .getAsJsonArray();

        Iterator<JsonElement> elements=array.iterator();
        while (elements.hasNext()){
            JsonObject object=elements.next().getAsJsonObject();
            String name=object.get("name").getAsString();
            try {
                Field field=discussInfoClass.getField(DiscussInfoMapping.INSTANCE.
                        get(PropertiesReader.strTurn(name, MyApplication.MAP)));
                field.setAccessible(true);
                Type type=field.getType();
                if(type.equals(String.class)){
                    field.set(discussInfo,object.get("value").getAsString());
                }else if(type.equals(List.class)){
                    List<String> tmplist=NetMethod.INSTANCE.parseStringtoArray(object.get("value").getAsString());
                    field.set(discussInfo,tmplist);
                }
            } catch (NoSuchFieldException e) {
                Log.d("test",e.toString()+" "+name);
            } catch (IllegalAccessException e) {
                Log.d("test",e.toString()+" "+name);
            }
        }
        return discussInfo;
    }

    public void competeCollectionQueryByUser(JsonGenerator generator,String email){
        SelectClassNameAction classname=new SelectClassNameAction();
        SelectFieldsAction field=new SelectFieldsAction();
        SelectConditionAction condition=new SelectConditionAction();

        generator.openNode();
        generator.openArray()
                .compete(classname,TableConstant.collection)
                .closeNode("className");

        generator.openArray()
                .compete(field,FieldConstant.noteId,TableConstant.collection)
                .closeNode("field");

        generator.openArray()
                .compete(condition,FieldConstant.email,email,TypeConstant.varchar,TableConstant.collection,"0")
                .closeNode("condition");
        generator.closeNode("");
    }

    public void competeDiscussByUser(JsonGenerator generator,String email,String discuss){
        SelectClassNameAction classname=new SelectClassNameAction();
        SelectFieldsAction field=new SelectFieldsAction();
        SelectConditionAction condition=new SelectConditionAction();

        generator.openNode();
        generator.openArray()
                .compete(classname,discuss)
                .closeNode("className");

        generator.openArray()
                .compete(field,FieldConstant.id,discuss)
                .closeNode("field");

        generator.openArray()
                .compete(condition,FieldConstant.email,email,TypeConstant.varchar,discuss,"0")
                .closeNode("condition");
        generator.closeNode("");
    }

    public int getResultSize(String result){
        JsonObject jsonObject=mParser.parse(result).getAsJsonObject();
        JsonArray array=jsonObject.get("result").getAsJsonArray();
        return array.size();
    }

    public Observable<JsonObject> generateObserable(String action, JsonAction jaction,Object...args){
        Retrofit retrofit=NetMethod.INSTANCE.getRetrofitWithRx();
        JsonGenerator generator=new JsonGenerator();
        jaction.competeJson(generator,args);
        DataAccessService service=retrofit.create(DataAccessService.class);
        String json=generator.toJson();
        Log.d("test",json);
        return service.accessDatarx(action,json);
    }
    public Call<JsonObject> generateCall(String action, JsonAction jaction, Object...args){
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

    public List<String> parseStringtoArray(String urls){

        if(!urls.endsWith("]")||!urls.startsWith("[")){
            return new ArrayList<String>();
        }else if(urls.equals("[]")) {
            return new ArrayList<String>();
        }else {
                String tmp=urls.substring(1,urls.length()-1);
                String[] urlarray=tmp.split(",");

                int len=urlarray.length;
                for(int i=0;i<len;i++){
                    urlarray[i]=urlarray[i].replace("http://localhost:8080/test/",BaseModel.HOST);
                }
                return Arrays.asList(urlarray);

        }
    }

    public void competeFansQueryByUser(JsonGenerator generator, String email) {
        SelectClassNameAction classname=new SelectClassNameAction();
        SelectFieldsAction field=new SelectFieldsAction();
        SelectConditionAction condition=new SelectConditionAction();

        generator.openNode();
        generator.openArray()
                .compete(classname,TableConstant.fans)
                .closeNode("className");

        generator.openArray()
                .compete(field,FieldConstant.id,TableConstant.fans)
                .closeNode("field");

        generator.openArray()
                .compete(condition,FieldConstant.email,email,TypeConstant.varchar,TableConstant.fans,"0")
                .closeNode("condition");
        generator.closeNode("");
    }

    public UserInfo praseUser(String result) {
        UserInfo userInfo=new UserInfo();
        Class userInfoClass=userInfo.getClass();
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
            String name=object.get("name").getAsString();
            try {
                Field field=userInfoClass.getField(UserInfoMapping.INSTANCE.
                        get(PropertiesReader.strTurn(name, MyApplication.MAP)));
                field.setAccessible(true);
                Type type=field.getType();
                if(type.equals(String.class)){
                    field.set(userInfo,object.get("value").getAsString());
                }else if(type.equals(List.class)){
                    List<String> tmplist=NetMethod.INSTANCE.parseStringtoArray(object.get("value").getAsString());
                    field.set(userInfo,tmplist);
                }
            } catch (NoSuchFieldException e) {
                Log.d("test",e.toString()+" "+name);
            } catch (IllegalAccessException e) {
                Log.d("test",e.toString()+" "+name);
            }
        }
        return userInfo;
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

}
