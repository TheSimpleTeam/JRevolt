package net.thesimpleteam.jrevolt.event;

public class MessageDeletedEvent implements Event {
    private final String id, channel;

    public MessageDeletedEvent(String id, String channel) {
        this.id = id;
        this.channel = channel;
    }

    public String getId() {
        return id;
    }

    public String getChannel() {
        return channel;
    }
}
