package net.thesimpleteam.jrevolt.event;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import net.thesimpleteam.jrevolt.entities.Server;

import java.util.Map;

public class ChannelUpdatedEvent implements Event {

    @Expose
    private final String id;
    @Expose
    private final Data data;

    public ChannelUpdatedEvent(String id, Data data) {
        this.id = id;
        this.data = data;
    }

    public class Data {
        @Expose private final Server.Icon icon;
        @SerializedName("role_permissions")
        @Expose private final Map<String, String> rolePermissions;
        @Expose private final String description;
        @Expose private final boolean nsfw;
        @Expose private final String name;

        private Data(Server.Icon icon, Map<String, String> rolePermissions, String description, boolean nsfw, String name) {
            this.icon = icon;
            this.rolePermissions = rolePermissions;
            this.description = description;
            this.nsfw = nsfw;
            this.name = name;
        }

        public Server.Icon getIcon() {
            return icon;
        }

        public Map<String, String> getRolePermissions() {
            return rolePermissions;
        }

        public String getDescription() {
            return description;
        }

        public boolean isNsfw() {
            return nsfw;
        }

        public String getName() {
            return name;
        }
    }

    public String getId() {
        return id;
    }

    public Data getData() {
        return data;
    }
}