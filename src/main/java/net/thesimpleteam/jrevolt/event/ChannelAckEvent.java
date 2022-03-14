package net.thesimpleteam.jrevolt.event;

import com.google.gson.annotations.SerializedName;

public class ChannelAckEvent implements Event {

    @SerializedName("channel")
    private final String channelId;
    @SerializedName("user")
    private final String userId;
    @SerializedName("message_id")
    private final String messageId;

    public ChannelAckEvent(String channelId, String userId, String messageId) {
        this.channelId = channelId;
        this.userId = userId;
        this.messageId = messageId;
    }

    public String getChannelId() {
        return channelId;
    }

    public String getUserId() {
        return userId;
    }

    public String getMessageId() {
        return messageId;
    }
}
