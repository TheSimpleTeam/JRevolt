package net.thesimpleteam.jrevolt.event;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ServerRoleDeletedEvent implements Event {

    @Expose
    @SerializedName("id")
    private final String serverId;
    @Expose
    @SerializedName("role_id")
    private final String roleId;

    public ServerRoleDeletedEvent(String serverId, String roleId) {
        this.serverId = serverId;
        this.roleId = roleId;
    }

    public String getServerId() {
        return serverId;
    }

    public String getRoleId() {
        return roleId;
    }
}
