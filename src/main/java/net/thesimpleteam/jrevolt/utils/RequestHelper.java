package net.thesimpleteam.jrevolt.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import net.thesimpleteam.jrevolt.JRevolt;
import net.thesimpleteam.jrevolt.entities.FileType;
import okhttp3.*;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class RequestHelper {

    private static final Gson gson = new GsonBuilder().serializeNulls().create();
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private RequestHelper() {}

    public static Request get(String url, String token) {
        return new Request.Builder()
                .url(JRevolt.BASE_URL + url)
                .addHeader("x-bot-token", token)
                .get()
                .build();
    }

    public static String uploadFile(FileType fileType, File file) {
        String url = "https://autumn.revolt.chat/" + fileType.name().toLowerCase();
        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("file", file.getName(), RequestBody.create(file, MediaType.parse("application/octet-stream"))).build();
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try(Response response = JRevolt.client.newCall(request).execute()) {
            if(response.code() != 200) {
                throw new RuntimeException("Error uploading file: " + response.code());
            }
            String abc = response.body().string();
            System.out.println(abc);
            return gson.fromJson(abc, JsonObject.class).get("id").getAsString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Request post(String url, String token, JsonObject json) {
        RequestBody body = RequestBody.create(json.toString(), RequestHelper.JSON);
        return new Request.Builder()
                .url(JRevolt.BASE_URL + url)
                .addHeader("x-bot-token", token)
                .post(body)
                .build();
    }

    public static Response getResponse(Request request) {
        try {
            return JRevolt.client.newCall(request).execute();
        } catch (IOException e) {
            if(JRevolt.DEBUG) e.printStackTrace();
            return null;
        }
    }

    public static Optional<String> getBody(Request request) {
        try {
            return Optional.of(getResponse(request).body().string());
        } catch (IOException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }
}
