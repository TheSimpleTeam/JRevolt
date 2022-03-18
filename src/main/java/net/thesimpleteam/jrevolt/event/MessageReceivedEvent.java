package net.thesimpleteam.jrevolt.event;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import net.thesimpleteam.jrevolt.entities.User;
import net.thesimpleteam.jrevolt.entities.messages.Message;

public class MessageReceivedEvent implements Event {

    @SerializedName("_id")
    @Expose
    private final String id;
    @Expose
    private final String nonce;
    @SerializedName("channel")
    @Expose
    private final String channelID;
    @SerializedName("author")
    @Expose
    private final String authorID;
    @Expose
    private User author;
    @Expose
    private final String content;
    @Expose
    private final Message message;

    public MessageReceivedEvent(String id, String nonce, String channelID, String authorID, User author, String content, Message message) {
        this.id = id;
        this.nonce = nonce;
        this.channelID = channelID;
        this.authorID = authorID;
        this.author = author;
        this.content = content;
        this.message = message;
    }

    public Message getMessage() {
        return message;
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
}
