package com.example.dishfo.androidapp.DataAcess;

import com.google.gson.JsonObject;

import io.reactivex.Observable;

/**
 * Created by dishfo on 18-3-7.
 */

public class FansAcess {

    public  static FansAcess INSTANCE=new FansAcess();

    private FansAcess(){

    }

    public Observable<JsonObject> getFansByUser(String email){
        return NetMethod.INSTANCE.generateObserable("query",(generator, args) -> {
            NetMethod.INSTANCE.competeFansQueryByUser(generator, (String) args[0]);
        },email);
    }
}
