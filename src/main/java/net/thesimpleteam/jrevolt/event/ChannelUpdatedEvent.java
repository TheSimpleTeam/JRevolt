package net.thesimpleteam.jrevolt.event;

import com.google.gson.annotations.SerializedName;
import net.thesimpleteam.jrevolt.entities.Server;

import java.util.Map;

public class ChannelUpdatedEvent implements Event {

    private final String id;
    private final Data data;

    public ChannelUpdatedEvent(String id, Data data) {
        this.id = id;
        this.data = data;
    }

    private class Data {
        private final Server.Icon icon;
        @SerializedName("role_permissions")
        private final Map<String, String> rolePermissions;
        private final String description;
        private final boolean nsfw;
        private final String name;

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