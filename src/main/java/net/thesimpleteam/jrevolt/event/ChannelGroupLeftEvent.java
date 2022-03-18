package net.thesimpleteam.jrevolt.event;

import com.google.gson.annotations.Expose;

public class ChannelGroupLeftEvent implements Event {

    @Expose
    private final String id, user;

    public ChannelGroupLeftEvent(String id, String user) {
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
