package net.thesimpleteam.jrevolt.event;

public interface RevoltListener {

    public default void onMessageReceived(MessageReceivedEvent event) {}

    public default void onReady(ReadyEvent event) {}

    public default void onMessageDeleted(MessageDeletedEvent event) {}

}
