package com.mad.trafficclient.util;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Utils {

    public static void getData(final Map<String, String> map, final String path, final Ok ok) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                sendRequset(map, path, ok);
            }
        }).start();
    }

    private static void sendRequset(Map<String, String> map, String path, final Ok ok) {
        MediaType Json = MediaType.parse("application/json;charset=utf-8");
        String json = new Gson().toJson(map);

        RequestBody body = RequestBody.create(Json, json);
        Request request = new Request.Builder().url(path).post(body).build();

        new OkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                ok.error(e);
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String result = response.body().string();
//                    try {
//                        JSONObject jo = new JSONObject(result);
//                        String serviceinfo = jo.getString("serviceinfo");
                        ok.success(result);
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
                }
            }
        });
    }

    interface Ok {
        void success(String s);
        void error(Exception e);
    }

    public static void main(String[] args) {
        HashMap<String, String> map = new HashMap<>();
        map.put("CarId", "1");
        getData(map, "http://192.168.1.6:8890/transportservice/type/jason/action/GetCarSpeed", new Ok() {
            @Override
            public void success(String s) {
                System.out.println(s);
            }

            @Override
            public void error(Exception e) {

            }
        });
    }
}
