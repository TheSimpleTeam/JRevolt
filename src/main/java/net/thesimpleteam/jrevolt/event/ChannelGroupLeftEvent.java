package net.thesimpleteam.jrevolt.event;

public class ChannelGroupLeftEvent implements Event {

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
