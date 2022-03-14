package net.thesimpleteam.jrevolt.event;

import com.google.gson.annotations.SerializedName;

public class ChannelStopTypingEvent implements Event {

    @SerializedName("id")
    private final String channelId;
    @SerializedName("user")
    private final String userId;

    public ChannelStopTypingEvent(String channelId, String userId) {
        this.channelId = channelId;
        this.userId = userId;
    }

    public String getChannelId() {
        return channelId;
    }

    public String getUserId() {
        return userId;
    }
}
