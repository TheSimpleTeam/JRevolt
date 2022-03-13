package net.thesimpleteam.jrevolt.entities;

import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import net.thesimpleteam.jrevolt.JRevolt;
import net.thesimpleteam.jrevolt.utils.RequestHelper;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class Message {

    @SerializedName("_id")
    private final String id;
    private final String nonce;
    @SerializedName("channel")
    private final String channelID;
    @SerializedName("author")
    private final String authorID;
    private transient User author;
    private final String content;
    @Expose(deserialize = false, serialize = false) private JRevolt revolt;

    public Message(String id, String nonce, String channelID, String author, String content) {
        this.id = id;
        this.nonce = nonce;
        this.channelID = channelID;
        this.authorID = author;
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public String getNonce() {
        return nonce;
    }

    public String getChannelID() {
        return channelID;
    }

    public String getAuthorID() {
        return authorID;
    }

    public User getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public void reply(String content) {
        JsonObject message = new JsonObject();
        message.addProperty("content", content);
        Request request = RequestHelper.post("channels/" + channelID + "/messages", revolt.getToken(), message);
        try (Response response = JRevolt.client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
