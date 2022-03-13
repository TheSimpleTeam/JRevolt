package net.thesimpleteam.jrevolt.entities;

import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import net.thesimpleteam.jrevolt.JRevolt;
import net.thesimpleteam.jrevolt.utils.RequestHelper;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.IOException;

public class Message {

    @SerializedName("_id")
    private final String id;
    private final String nonce;
    private final String channel;
    private final String author;
    private final String content;
    @Expose(deserialize = false, serialize = false) private JRevolt revolt;

    public Message(String id, String nonce, String channel, String author, String content) {
        this.id = id;
        this.nonce = nonce;
        this.channel = channel;
        this.author = author;
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public String getNonce() {
        return nonce;
    }

    public String getChannel() {
        return channel;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public void reply(String content) {
        JsonObject message = new JsonObject();
        message.addProperty("content", content);
        RequestBody body = RequestBody.create(revolt.getGson().toJson(message), RequestHelper.JSON);
        Request request = new Request.Builder()
                .addHeader("x-bot-token", revolt.getToken()).url(JRevolt.BASE_URL + "channels/" + channel + "/messages")
                .post(body)
                .build();
        try (Response response = JRevolt.client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
