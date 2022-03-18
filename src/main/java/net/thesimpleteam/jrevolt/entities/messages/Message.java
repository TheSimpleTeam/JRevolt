package net.thesimpleteam.jrevolt.entities.messages;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import net.thesimpleteam.jrevolt.JRevolt;
import net.thesimpleteam.jrevolt.entities.Server;
import net.thesimpleteam.jrevolt.entities.User;
import net.thesimpleteam.jrevolt.utils.RequestHelper;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Message {

    @Expose
    @SerializedName("_id")
    private final String id;
    @Expose
    private final String nonce;
    @Expose
    @SerializedName("channel")
    private final String channelID;
    private User author;
    @Expose
    private final String content;
    @Expose
    private final Server.Icon[] attachments;
    @Expose
    private final Edited edited;
    @Expose
    private final Embed[] embeds;
    @Expose
    @SerializedName("mentions")
    private final String[] mentionIds;
    @Expose
    @SerializedName("replies")
    private final String[] replyIds;
    @Expose
    private final Masquerade[] masquerades;

    private JRevolt revolt;

    public Message(String id, String nonce, String channelID, User author, String content,
                   Server.Icon[] attachments, Edited edited, Embed[] embeds, String[] mentionIds, String[] replyIds, Masquerade[] masquerades) {
        this.id = id;
        this.nonce = nonce;
        this.channelID = channelID;
        this.author = author;
        this.content = content;
        this.attachments = attachments;
        this.edited = edited;
        this.embeds = embeds;
        this.mentionIds = mentionIds;
        this.replyIds = replyIds;
        this.masquerades = masquerades;
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

    public User getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public Server.Icon[] getAttachments() {
        return attachments;
    }

    public Edited getEdited() {
        return edited;
    }

    public Embed[] getEmbeds() {
        return embeds;
    }

    public String[] getMentionIds() {
        return mentionIds;
    }

    public String[] getReplyIds() {
        return replyIds;
    }

    public Masquerade[] getMasquerades() {
        return masquerades;
    }

    /**
     * @param content The content of the message.
     * @throws IllegalStateException if the bot does not have access to the channel or cannot send messages in the channel.
     */
    public void reply(String content) {
        JsonObject message = new JsonObject();
        message.addProperty("content", content);
        Request request = RequestHelper.post("channels/" + channelID + "/messages", revolt.getToken(), message);
        try (Response response = JRevolt.client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                if (response.code() == 403) {
                    throw new IllegalStateException("You are not allowed to send messages in this channel");
                }
                throw new IOException("Unexpected code " + response);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @deprecated Use {@link MessageBuilder#sendMessage(String)} instead.
     */
    @Deprecated
    public void replyEmbed(String content, Embed... embeds) {
        JsonObject message = new JsonObject();
        message.addProperty("content", content);
        String embedsArray = revolt.getGson().toJson(embeds);
        message.add("embeds", revolt.getGson().fromJson(embedsArray, JsonArray.class));
        Request request = RequestHelper.post("channels/" + channelID + "/messages", revolt.getToken(), message);
        try (Response response = JRevolt.client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                if (response.code() == 403) {
                    throw new IllegalStateException("You are not allowed to send messages in this channel");
                }
                throw new IOException("Unexpected code " + response);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static class Edited {
        @Expose
        @SerializedName("$date")
        private final String date;

        private Edited(String date) {
            this.date = date;
        }

        public String getDate() {
            return date;
        }

        public LocalDateTime getDateTime() {
            return LocalDateTime.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-ddTHH:mm:ss.SSSZ"));
        }
    }

    public static class Masquerade {

        @Expose
        private final String name, avatar;

        public Masquerade(String name, String avatar) {
            this.name = name;
            this.avatar = avatar;
        }

        public String getName() {
            return name;
        }

        public String getAvatar() {
            return avatar;
        }
    }
}
