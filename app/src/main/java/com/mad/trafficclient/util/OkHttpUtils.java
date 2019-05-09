package com.mad.trafficclient.util;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkHttpUtils {

    private static final String ip = "192.168.1.240:8080";
    private static final String temp = "/transportservice/type/jason/action/";

    public static void getDataFromIntent(final String path, final Map<String, String> map, final CallBack callBack) {
        final String uri = "http://" + ip + temp + path + ".do";
        new Thread(new Runnable() {
            @Override
            public void run() {
                sendOkHttpRequest(map, uri, callBack);
            }
        }).start();
    }

    private static void sendOkHttpRequest(Map<String, String> map, String uri, final CallBack callBack) {
        //0.创建MediaType类型 说明参数是一个json数据
        MediaType Json = MediaType.parse("application/json;charset=utf-8");
        //1.初始化参数
        Gson gson = new Gson();
        //将map转成json数据
        String json = gson.toJson(map);
        //2.创建OkHttpClient对象
        OkHttpClient okHttpClient = new OkHttpClient();
        //3.通过new FormBody()调用build方法,创建一个RequestBody,可以用add添加键值对
//       RequestBody requestBody = new FormBody.Builder().add("CarId", 1).build();
        RequestBody body = RequestBody.create(Json, json);
        //4.创建Request对象，设置URL地址，将RequestBody作为post方法的参数传入
        final Request request = new Request.Builder().url(uri).post(body).build();
        //5.创建一个call对象,参数就是Request请求对象
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                callBack.exception(e);
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()){
                    try {
                        String body = response.body().string();
                        JSONObject jo = new JSONObject(body);
                        String serverinfo = jo.getString("serverinfo");
                        callBack.successful(serverinfo);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public interface CallBack{
        void successful(String string);
        void exception(Exception e);
    }
}

