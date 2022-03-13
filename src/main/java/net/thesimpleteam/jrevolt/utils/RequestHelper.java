package net.thesimpleteam.jrevolt.utils;

import com.google.gson.JsonObject;
import net.thesimpleteam.jrevolt.JRevolt;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

import java.io.IOException;

public class RequestHelper {

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private RequestHelper() {}

    public static Request get(String url, String token) {
        return new Request.Builder()
                .url(JRevolt.BASE_URL + url)
                .addHeader("x-bot-token", token)
                .get()
                .build();
    }

    public static Request post(String url, String token, JsonObject json) {
        RequestBody body = RequestBody.create(json.toString(), RequestHelper.JSON);
        return new Request.Builder()
                .url(JRevolt.BASE_URL + url)
                .addHeader("x-bot-token", token)
                .post(body)
                .build();
    }

    public static String getBody(Request request) {
        try {
            return JRevolt.client.newCall(request).execute().body().string();
        } catch (IOException e) {
            if(JRevolt.DEBUG) e.printStackTrace();
            return null;
        }
    }
}
