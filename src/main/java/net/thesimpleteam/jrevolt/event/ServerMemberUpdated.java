package net.thesimpleteam.jrevolt.event;

import com.google.gson.annotations.SerializedName;

public class ServerMemberUpdated implements Event {

    private final Id id;
    private final Data data;
    private final String clear;

    public ServerMemberUpdated(Id id, Data data, String clear) {
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

    private class Id {
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

    private class Data {
        @SerializedName("roles")
        private final String[] rolesIds;

        private Data(String[] rolesIds) {
            this.rolesIds = rolesIds;
        }
    }
}
