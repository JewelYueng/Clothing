package com.example.jewel.clothingrec.global;

/**
 * Created by Aria on 2017/4/20.
 */

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by Aria on 2017/2/16.
 */

public class Networks {


    private static final MediaType JSON = MediaType.parse("application/json;charset=utf-8");

    private static final int READ_TIMEOUT = 5;
    private static final int WRITE_TIMEOUT = 5;

    private OkHttpClient okHttpClient;

    private static Networks networks;

    private Networks() {
        okHttpClient = new OkHttpClient.Builder()
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIMEOUT,TimeUnit.SECONDS)
                .build();
    }

    public static Networks getInstance() {
        if (networks == null) {
            networks = new Networks();
        }
        return networks;
    }

    public Call doPost(String url,JSONObject object){
        Log.d("MainActivity","object:"+object.toString());
        RequestBody body = RequestBody.create(JSON,object.toString());
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        return okHttpClient.newCall(request);
    }

    public Call doPost(String url){
        Request request = new Request.Builder().url(url).build();
        return okHttpClient.newCall(request);
    }

}