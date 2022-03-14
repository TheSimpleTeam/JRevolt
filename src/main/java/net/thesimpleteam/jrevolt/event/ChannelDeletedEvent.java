package net.thesimpleteam.jrevolt.event;

public class ChannelDeletedEvent implements Event {

    private final String id;

    public ChannelDeletedEvent(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
