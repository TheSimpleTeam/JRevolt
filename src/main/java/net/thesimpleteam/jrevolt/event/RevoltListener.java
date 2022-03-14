package net.thesimpleteam.jrevolt.event;

public interface RevoltListener {

    default void onMessageReceived(MessageReceivedEvent event) {}

    default void onReady(ReadyEvent event) {}

    default void onMessageDeleted(MessageDeletedEvent event) {}

    default void onAuthenticated() {}

    default void onPong() {}

    default void onMessageUpdated(MessageUpdatedEvent event) {}

    default void onChannelCreated(ChannelCreatedEvent event) {}

    default void onChannelUpdated(ChannelUpdatedEvent event) {}

    default void onChannelDeleted(ChannelDeletedEvent event) {}

    default void onChannelGroupJoined(ChannelGroupJoinEvent event) {}

    default void onChannelGroupLeft(ChannelGroupLeftEvent event) {}

    default void onChannelStartTyping(ChannelStartTypingEvent event) {}

    default void onChannelStopTyping(ChannelStopTypingEvent event) {}

    default void onChannelAck(ChannelAckEvent event) {}
}
