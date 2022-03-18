package net.thesimpleteam.jrevolt.event;

import net.thesimpleteam.jrevolt.RevoltWSClient;

public interface RevoltListener {

    /**
     * Called when the bot is ready.
     */
    default void onReady(ReadyEvent event) {}

    /**
     * Called when the bot is authenticated.
     */
    default void onAuthenticated() {}

    /**
     * Called when the server responds to a ping {@link RevoltWSClient#sendPing()}.
     */
    default void onPong() {}

    /**
     * Called when a message has been sent to the server.
     */
    default void onMessageReceived(MessageReceivedEvent event) {}

    /**
     * Called when a message has been edited.
     */
    default void onMessageUpdated(MessageUpdatedEvent event) {}

    /**
     * Called when a message has been deleted;
     */
    default void onMessageDeleted(MessageDeletedEvent event) {}

    /**
     * Called when a channel has been created.
     */
    default void onChannelCreated(ChannelCreatedEvent event) {}

    /**
     *  Called when a channel has been updated, like when a channel's name has been changed.
     */
    default void onChannelUpdated(ChannelUpdatedEvent event) {}

    /**
     * Called when a channel has been deleted.
     */
    default void onChannelDeleted(ChannelDeletedEvent event) {}

    /**
     * Called when a user has joined a group channel.
     */
    default void onChannelGroupJoined(ChannelGroupJoinEvent event) {}

    /**
     * Called when a user has left a group channel.
     */
    default void onChannelGroupLeft(ChannelGroupLeftEvent event) {}

    /**
     * Called when a user starts typing in a channel.
     */
    default void onChannelStartTyping(ChannelStartTypingEvent event) {}

    /**
     * Called when a user stops typing in a channel.
     */
    default void onChannelStopTyping(ChannelStopTypingEvent event) {}

    /**
     * Called when...
     * <br/>
     * I don't know what this event is.
     * <br/>
     * <h6>TODO: Find out what this event is.</h6>
     */
    default void onChannelAck(ChannelAckEvent event) {}

    /**
     * Called when a server has been updated, like when a server's description has been changed.
     */
    default void onServerUpdated(ServerUpdatedEvent event) {}

    /**
     * Called when a server has been deleted.
     */
    default void onServerDeleted(ServerDeletedEvent event) {}

    /**
     * Called when a user has joined a server.
     */
    default void onServerMemberJoined(ServerMemberJoinedEvent event) {}

    /**
     * Called when a user has been updated, like when a user's nickname has been changed.
     */
    default void onServerMemberUpdated(ServerMemberUpdatedEvent event) {}

    /**
     * Called when a user has left a server.
     */
    default void onServerMemberLeft(ServerMemberLeftEvent event) {}

    /**
     * Called when a server role has been updated or created.
     */
    default void onServerRoleUpdated(ServerRoleUpdatedEvent event) {}

    /**
     * Called when a server role has been deleted.
     */
    default void onServerRoleDeleted(ServerRoleDeletedEvent event) {}
}