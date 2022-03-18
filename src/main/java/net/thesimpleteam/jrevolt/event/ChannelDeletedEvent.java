package net.thesimpleteam.jrevolt.event;

import com.google.gson.annotations.Expose;

public class ChannelDeletedEvent implements Event {

    @Expose
    private final String id;

    public ChannelDeletedEvent(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
