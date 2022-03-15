package net.thesimpleteam.jrevolt.event;

import com.google.gson.annotations.SerializedName;
import net.thesimpleteam.jrevolt.entities.Server;

public class ServerUpdatedEvent implements Event {

    @SerializedName("id")
    private String serverId;
    private final Data data;
    private final String clear;

    public ServerUpdatedEvent(String serverId, Data data, String clear) {
        this.serverId = serverId;
        this.data = data;
        this.clear = clear;
    }

    public String getServerId() {
        return serverId;
    }

    public Data getData() {
        return data;
    }

    public String getClear() {
        return clear;
    }

    private class Data {
        private final String description;
        private final Server.SystemMessage systemMessage;
        private final Server.Icon banner;
        private final Server.Icon icon;
        private final String name;

        private Data(String description, Server.SystemMessage systemMessage, Server.Icon banner, Server.Icon icon, String name) {
            this.description = description;
            this.systemMessage = systemMessage;
            this.banner = banner;
            this.icon = icon;
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public Server.SystemMessage getSystemMessage() {
            return systemMessage;
        }

        public Server.Icon getBanner() {
            return banner;
        }

        public Server.Icon getIcon() {
            return icon;
        }

        public String getName() {
            return name;
        }
    }
}
