package net.thesimpleteam.jrevolt.event;

import com.google.gson.annotations.Expose;

public class ChannelGroupJoinEvent implements Event {

    @Expose
    private final String id, user;

    public ChannelGroupJoinEvent(String id, String user) {
        this.id = id;
        this.user = user;
    }

    public String getId() {
        return id;
    }

    public String getUser() {
        return user;
    }
}
