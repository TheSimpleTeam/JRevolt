package net.thesimpleteam.jrevolt.event;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ServerMemberUpdatedEvent implements Event {

    @Expose
    private final Id id;
    @Expose
    private final Data data;
    @Expose
    private final String clear;

    public ServerMemberUpdatedEvent(Id id, Data data, String clear) {
        this.id = id;
        this.data = data;
        this.clear = clear;
    }

    public Id getId() {
        return id;
    }

    public Data getData() {
        return data;
    }

    public String getClear() {
        return clear;
    }

    public class Id {
        @Expose
        private final String server, user;

        private Id(String server, String user) {
            this.server = server;
            this.user = user;
        }

        public String getServer() {
            return server;
        }

        public String getUser() {
            return user;
        }
    }

    public class Data {
        @Expose
        @SerializedName("roles")
        private final String[] rolesIds;

        private Data(String[] rolesIds) {
            this.rolesIds = rolesIds;
        }
    }
}
