package net.thesimpleteam.jrevolt.event;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ServerDeletedEvent implements Event {

    @Expose
    @SerializedName("id")
    private final String serverId;

    public ServerDeletedEvent(String serverId) {
        this.serverId = serverId;
    }

    public String getServerId() {
        return serverId;
    }
}
