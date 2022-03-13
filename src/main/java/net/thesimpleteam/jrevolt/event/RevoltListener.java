package net.thesimpleteam.jrevolt.event;

public interface RevoltListener {

    default void onMessageReceived(MessageReceivedEvent event) {}

    default void onReady(ReadyEvent event) {}

    default void onMessageDeleted(MessageDeletedEvent event) {}

}
