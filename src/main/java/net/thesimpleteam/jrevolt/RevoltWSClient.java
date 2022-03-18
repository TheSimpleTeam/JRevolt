package net.thesimpleteam.jrevolt;

import com.google.gson.JsonObject;
import net.thesimpleteam.jrevolt.commands.Command;
import net.thesimpleteam.jrevolt.entities.ChannelType;
import net.thesimpleteam.jrevolt.entities.messages.Message;
import net.thesimpleteam.jrevolt.entities.Server;
import net.thesimpleteam.jrevolt.entities.User;
import net.thesimpleteam.jrevolt.event.*;
import net.thesimpleteam.jrevolt.exception.RevoltException;
import net.thesimpleteam.jrevolt.utils.RequestHelper;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.lang.reflect.Field;
import java.net.URI;
import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class RevoltWSClient extends WebSocketClient {

    private final String token;
    private final JRevolt revolt;
    private static final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(2);

    RevoltWSClient(JRevolt revolt) {
        super(URI.create(JRevolt.WS_BASE_URL));
        this.token = revolt.getToken();
        this.revolt = revolt;
        this.setConnectionLostTimeout(10);
    }

    @Override
    public void onOpen(ServerHandshake handshakeData) {
        getConnection().send("{\"type\": \"Authenticate\", \"token\": \"" + token + "\"}");
        executorService.scheduleAtFixedRate(this::sendPing, 10, 10, TimeUnit.SECONDS);
    }

    @Override
    public void onMessage(String message) {
        JsonObject object = revolt.getGson().fromJson(message, JsonObject.class);
        sendLog(object);
        switch (object.get("type").getAsString()) {
            case "Error":
                throw new RevoltException(object.get("error").getAsString());
            case "Ready":
                object.get("users").getAsJsonArray().forEach(user -> revolt.addUser(revolt.getGson().fromJson(user, User.class)));
                object.get("servers").getAsJsonArray().forEach(server -> revolt.addServer(revolt.getGson().fromJson(server, Server.class)));
                revolt.getListeners().forEach(listener -> listener.onReady(new ReadyEvent(revolt.getUsers().toArray(new User[0]),
                        revolt.getServers().toArray(new Server[0]))));
                break;
            case "Message":
                //Check if the message is a System Message
                if(!object.get("content").isJsonPrimitive()) return;
                Message msg = revolt.getGson().fromJson(RequestHelper.getBody(RequestHelper.get(String.format("channels/%s/messages/%s",
                        object.get("channel").getAsString(), object.get("_id").getAsString()), revolt.getToken())).orElseThrow(RuntimeException::new), Message.class);
                setValue(Message.class, "revolt", revolt, msg);
                setValue(Message.class, "author", revolt.getUser(object.get("author").getAsString()).orElse(null), msg);
                revolt.getListeners().forEach(l -> l.onMessageReceived(new MessageReceivedEvent(msg.getId(), msg.getNonce(), msg.getChannelID(),
                        msg.getAuthor().getId(), msg.getAuthor(), msg.getContent(), msg)));
                revolt.getCommands().stream().filter(c -> c.getName().equals(msg.getContent().substring(revolt.getPrefix().length()).split("[^\\S\\r\\n]+")[0]))
                        .forEach(c -> {
                            setValue(Command.class, "revolt", revolt, c);
                            String[] args = msg.getContent().split("[^\\S\\r\\n]+");
                            c.execute(Arrays.copyOfRange(args, 1, args.length), msg);
                        });
                break;
            case "MessageDelete":
                revolt.getListeners().forEach(l -> l.onMessageDeleted(revolt.getGson().fromJson(object, MessageDeletedEvent.class)));
                break;
            case "Authenticated":
                revolt.getListeners().forEach(RevoltListener::onAuthenticated);
                break;
            case "Pong":
                revolt.getListeners().forEach(RevoltListener::onPong);
                break;
            case "MessageUpdate":
                revolt.getListeners().forEach(l -> l.onMessageUpdated(revolt.getGson().fromJson(object, MessageUpdatedEvent.class)));
                break;
            case "ChannelCreate":
                ChannelCreatedEvent e = revolt.getGson().fromJson(object, ChannelCreatedEvent.class);
                setValue(e.getClass(), "type", ChannelType.getChannelType(e.getChannelType()), e);
                revolt.getListeners().forEach(l -> l.onChannelCreated(e));
                break;
            case "ChannelUpdate":
                revolt.getListeners().forEach(l -> l.onChannelUpdated(revolt.getGson().fromJson(object, ChannelUpdatedEvent.class)));
                break;
            case "ChannelDelete":
                revolt.getListeners().forEach(l -> l.onChannelDeleted(revolt.getGson().fromJson(object, ChannelDeletedEvent.class)));
                break;
            case "ChannelGroupJoin":
                revolt.getListeners().forEach(l -> l.onChannelGroupJoined(revolt.getGson().fromJson(object, ChannelGroupJoinEvent.class)));
                break;
            case "ChannelGroupLeave":
                revolt.getListeners().forEach(l -> l.onChannelGroupLeft(revolt.getGson().fromJson(object, ChannelGroupLeftEvent.class)));
                break;
            case "ChannelStartTyping":
                revolt.getListeners().forEach(l -> l.onChannelStartTyping(revolt.getGson().fromJson(object, ChannelStartTypingEvent.class)));
                break;
            case "ChannelStopTyping":
                revolt.getListeners().forEach(l -> l.onChannelStopTyping(revolt.getGson().fromJson(object, ChannelStopTypingEvent.class)));
                break;
            case "ChannelAck":
                revolt.getListeners().forEach(l -> l.onChannelAck(revolt.getGson().fromJson(object, ChannelAckEvent.class)));
                break;
            case "ServerUpdate":
                revolt.getListeners().forEach(l -> l.onServerUpdated(revolt.getGson().fromJson(object, ServerUpdatedEvent.class)));
                break;
            case "ServerDelete":
                revolt.getListeners().forEach(l -> l.onServerDeleted(revolt.getGson().fromJson(object, ServerDeletedEvent.class)));
                break;
            case "ServerMemberJoin":
                revolt.getListeners().forEach(l -> l.onServerMemberJoined(revolt.getGson().fromJson(object, ServerMemberJoinedEvent.class)));
                break;
            case "ServerMemberUpdate":
                revolt.getListeners().forEach(l -> l.onServerMemberUpdated(revolt.getGson().fromJson(object, ServerMemberUpdatedEvent.class)));
                break;
            case "ServerMemberLeave":
                revolt.getListeners().forEach(l -> l.onServerMemberLeft(revolt.getGson().fromJson(object, ServerMemberLeftEvent.class)));
                break;
            case "ServerRoleUpdate":
                revolt.getListeners().forEach(l -> l.onServerRoleUpdated(revolt.getGson().fromJson(object, ServerRoleUpdatedEvent.class)));
                break;
            case "ServerRoleDelete":
                revolt.getListeners().forEach(l -> l.onServerRoleDeleted(revolt.getGson().fromJson(object, ServerRoleDeletedEvent.class)));
                break;
            default:
                break;
        }
    }

    private void sendLog(JsonObject object) {
        if(!JRevolt.DEBUG) return;
        //We don't want to send pong messages to the log to prevent log spam
        if(object.get("type").getAsString().equals("Pong")) return;
        JRevolt.LOGGER.info("Received {} event with data:\n {}", object.get("type").getAsString(), object);
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        JRevolt.LOGGER.error("closed connection {} with code {} and reason {}", this.getConnection().getRemoteSocketAddress(), code, reason);
        executorService.shutdown();
    }

    @Override
    public void onError(Exception ex) {
        if(ex instanceof RevoltException) {
            throw (RevoltException) ex;
        }
        ex.printStackTrace();
    }

    public static void setValue(Class<?> clazz, String field, Object value, Object instance) {
        try {
            Field f = clazz.getDeclaredField(field);
            f.setAccessible(true);
            f.set(instance, value);
            f.setAccessible(false);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void sendPing() {
        getConnection().send("{\"type\": \"Ping\", \"data\": 0}");
    }

    public static void stopExecutorService() {
        executorService.shutdown();
    }

    public static ScheduledExecutorService getExecutorService() {
        return executorService;
    }
}
