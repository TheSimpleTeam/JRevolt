package net.thesimpleteam.jrevolt.event;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ServerMemberLeftEvent implements Event {

    @Expose
    @SerializedName("id")
    private final String serverId;
    @Expose
    @SerializedName("user")
    private final String userId;

    public ServerMemberLeftEvent(String serverId, String userId) {
        this.serverId = serverId;
        this.userId = userId;
    }

    public String getServerId() {
        return serverId;
    }

    public String getUserId() {
        return userId;
    }
}
