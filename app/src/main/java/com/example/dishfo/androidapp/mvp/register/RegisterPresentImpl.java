package com.example.dishfo.androidapp.mvp.register;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.example.dishfo.androidapp.mvp.BaseModel;
import com.example.dishfo.androidapp.constant.FieldConstant;
import com.example.dishfo.androidapp.constant.TypeConstant;
import com.example.dishfo.androidapp.netInterface.AddAction2;
import com.example.dishfo.androidapp.netInterface.AddAction3;
import com.example.dishfo.androidapp.netInterface.InsertValuesAction;
import com.example.dishfo.androidapp.netInterface.JsonGenerator;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by dishfo on 18-1-22.
 */

public class RegisterPresentImpl implements RegisterTaskContract.RegisterPresenter{

    private RegisterTaskContract.RegisterView mRegisterView;
    private Pattern mEmailPattern;

    public RegisterPresentImpl(@NonNull RegisterTaskContract.RegisterView mRegisterView) {
        this.mRegisterView = mRegisterView;
        mEmailPattern=Pattern.compile("^[a-z0-9A-Z_]+@[a-z0-9A-Z_]+\\.com$");
    }

    @Override
    public void start(Object... args) {
        //部分数据的初始化
    }

    @Override
    public void stop() {

    }

    @Override
    public void onCompete(Object... args) {

    }

    @Override
    public void onError(Object... args) {

    }

    @Override
    public void sendEmail(String email) {
        if(email==null||TextUtils.isEmpty(email)){
            mRegisterView.showSendEmailError("邮箱地址为空");
        }else if(!configEmail(email)){
            mRegisterView.showSendEmailError("不合法的邮箱地址");
        } else{
            sendEmailNow(email);
        }
    }

    @Override
    public void register(String email,String password,String token) {
        if(password==null||TextUtils.isEmpty(password)){
            mRegisterView.showRegisterError("密码不能为空");
        }else if(token==null||TextUtils.isEmpty(token)){
            mRegisterView.showRegisterError("验证码不能为空");
        }else if(password.length()<10){
            mRegisterView.showRegisterError("密码必须长于10位");
        }else{
            registerNow(email,password,token);
        }

    }

    private void registerNow(String email,String password,String token){
        Retrofit retrofit=new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(BaseModel.HOST).build();

        RegisterService service=retrofit.create(RegisterService.class);
        JsonGenerator generator=new JsonGenerator();
        generator.openNode()
                .compete(new AddAction2(),"className","user")
                .openArray()
                .compete(new InsertValuesAction(), FieldConstant.email,email, TypeConstant.varchar,"primarykey")
                .compete(new InsertValuesAction(),FieldConstant.password,password,TypeConstant.varchar,"")
                .compete(new InsertValuesAction(),FieldConstant.time,"",TypeConstant.varchar,"")
                .compete(new InsertValuesAction(),FieldConstant.name,"",TypeConstant.varchar,"")
                .compete(new InsertValuesAction(),FieldConstant.autograph,"",TypeConstant.varchar,"")
                .compete(new InsertValuesAction(),FieldConstant.head,"",TypeConstant.varchar,"")
                .closeNode("values")
                .closeNode("");

        Log.d("test",generator.toJson());

        Observable<HashMap<String,Object>> observable=
                service.setPassword("register",generator.toJson(),token);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringObjectHashMap -> {
                    double code= (double) stringObjectHashMap.get("code");
                    if(code==1){
                        mRegisterView.showRegisterSucceed();
                    }else {
                        Log.d("test","register "+stringObjectHashMap.toString());
                        mRegisterView.showRegisterError("注册失败");
                    }
                },
                throwable -> {
                    Log.d("test","register "+throwable.toString());
                    mRegisterView.showRegisterError("网路异常");
                });

    }

    private void sendEmailNow(String email){
        Retrofit retrofit=new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(BaseModel.HOST).build();

        RegisterService service=retrofit.create(RegisterService.class);

        JsonGenerator generator=new JsonGenerator();
        generator.openNode()
                .compete(new AddAction3(),"email",email)
                .closeNode("");

        String json=generator.toJson();
        Log.d("test",json);

        Observable<HashMap<String,Object>> observable=service.sendEmail("sendEmail",json);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringObjectMap -> {
                    double code = (double) stringObjectMap.get("code");
                    if(code==1){
                        mRegisterView.showSendEmailSucceed();
                    }else{
                        Log.d("test",stringObjectMap.toString());
                        mRegisterView.showSendEmailError("未能成功发送邮件");
                    }
                },throwable -> {
                    Log.d("test",throwable.toString());
                    mRegisterView.showSendEmailError("未能成功发送邮件");
                });
    }

    private boolean configEmail(String email){
        Matcher matcher=mEmailPattern.matcher(email);
        if(matcher.find()){
            return  true;
        }else {
            return  false;
        }
    }

}
