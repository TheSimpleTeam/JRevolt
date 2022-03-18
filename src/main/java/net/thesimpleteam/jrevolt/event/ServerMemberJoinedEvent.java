package net.thesimpleteam.jrevolt.event;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ServerMemberJoinedEvent implements Event {

    @Expose
    @SerializedName("id")
    private final String serverID;
    @Expose
    @SerializedName("user")
    private final String userID;

    public ServerMemberJoinedEvent(String serverID, String userID) {
        this.serverID = serverID;
        this.userID = userID;
    }

    public String getServerID() {
        return serverID;
    }

    public String getUserID() {
        return userID;
    }
}
