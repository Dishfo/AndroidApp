package com.example.dishfo.androidapp.DataAcess;

import com.example.dishfo.androidapp.netInterface.JsonGenerator;
import com.google.gson.JsonObject;

import io.reactivex.Observable;
import retrofit2.Retrofit;

/**
 * Created by dishfo on 18-3-7.
 */

public class CollectionAcess {
    public static CollectionAcess INSTANCE=new CollectionAcess();

    private CollectionAcess(){

    }

    public Observable<JsonObject> getCollectionByUser(String email){
        return NetMethod.INSTANCE.generateObserable("query",(generator, args) -> {
            NetMethod.INSTANCE.competeCollectionQueryByUser(generator, (String) args[0]);
        },email);
    }


}
