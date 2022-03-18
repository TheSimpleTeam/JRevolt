package net.thesimpleteam.jrevolt.event;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import net.thesimpleteam.jrevolt.entities.Server;

public class ServerUpdatedEvent implements Event {

    @Expose
    @SerializedName("id")
    private String serverId;
    @Expose
    private final Data data;
    @Expose
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

    public class Data {
        @Expose
        private final String description;
        @Expose
        private final Server.SystemMessage systemMessage;
        @Expose
        private final Server.Icon banner;
        @Expose
        private final Server.Icon icon;
        @Expose
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
