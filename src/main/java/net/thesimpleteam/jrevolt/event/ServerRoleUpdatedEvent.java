package net.thesimpleteam.jrevolt.event;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ServerRoleUpdatedEvent implements Event {

    @Expose
    @SerializedName("id")
    private final String serverId;
    @Expose
    @SerializedName("role_id")
    private final String roleId;
    @Expose
    private final Object data;
    @Expose
    private final String clear;

    public ServerRoleUpdatedEvent(String serverId, String roleId, Object data, String clear) {
        this.serverId = serverId;
        this.roleId = roleId;
        this.data = data;
        this.clear = clear;
    }

    public String getServerId() {
        return serverId;
    }

    public String getRoleId() {
        return roleId;
    }

    public Object getData() {
        return data;
    }

    public String getClear() {
        return clear;
    }
}
