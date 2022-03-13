package net.thesimpleteam.jrevolt.event;

import net.thesimpleteam.jrevolt.entities.Message;

public class MessageReceivedEvent implements Event {

    private final Message message;

    public MessageReceivedEvent(Message message) {
        this.message = message;
    }

    public Message getMessage() {
        return message;
    }
}
