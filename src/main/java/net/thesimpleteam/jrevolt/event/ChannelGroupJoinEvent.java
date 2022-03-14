package net.thesimpleteam.jrevolt.event;

public class ChannelGroupJoinEvent implements Event {

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
