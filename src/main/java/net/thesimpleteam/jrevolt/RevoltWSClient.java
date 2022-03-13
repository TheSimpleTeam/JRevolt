package net.thesimpleteam.jrevolt;

import com.google.gson.JsonObject;
import net.thesimpleteam.jrevolt.commands.Command;
import net.thesimpleteam.jrevolt.entities.Message;
import net.thesimpleteam.jrevolt.entities.Server;
import net.thesimpleteam.jrevolt.entities.User;
import net.thesimpleteam.jrevolt.event.MessageDeletedEvent;
import net.thesimpleteam.jrevolt.event.MessageReceivedEvent;
import net.thesimpleteam.jrevolt.event.ReadyEvent;
import net.thesimpleteam.jrevolt.exception.RevoltException;
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
    private static final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);

    public RevoltWSClient(JRevolt revolt) {
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
                Message msg = revolt.getGson().fromJson(object, Message.class);
                try {
                    Field f = msg.getClass().getDeclaredField("revolt");
                    f.setAccessible(true);
                    f.set(msg, revolt);
                    f.setAccessible(false);
                    Field author = msg.getClass().getDeclaredField("author");
                    author.setAccessible(true);
                    author.set(msg, revolt.getUser(msg.getAuthorID()).orElse(null));
                    author.setAccessible(false);
                } catch (IllegalAccessException | NoSuchFieldException e) {
                    throw new RuntimeException(e);
                }
                revolt.getListeners().forEach(l -> l.onMessageReceived(new MessageReceivedEvent(msg)));
                revolt.getCommands().stream().filter(c -> c.getName().equals(msg.getContent().substring(revolt.getPrefix().length()).split("[^\\S\\r\\n]+")[0]))
                        .forEach(c -> {
                            try {
                                Field f = Command.class.getDeclaredField("revolt");
                                f.setAccessible(true);
                                f.set(c, revolt);
                                f.setAccessible(false);
                            } catch (IllegalAccessException | NoSuchFieldException e) {
                                throw new RuntimeException(e);
                            }
                            String[] args = msg.getContent().split("[^\\S\\r\\n]+");
                            c.execute(Arrays.copyOfRange(args, 1, args.length), msg);
                        });
                break;
            case "MessageDelete":
                revolt.getListeners().forEach(l -> l.onMessageDeleted(revolt.getGson().fromJson(object, MessageDeletedEvent.class)));
                break;
            case "Authenticated":
                JRevolt.LOGGER.info("authenticated");
                break;
            case "Pong": break;
            default:
                if(JRevolt.DEBUG) JRevolt.LOGGER.info("unknown message type: {} with message:\n {}", object.get("type").getAsString(), message);
                break;
        }
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        JRevolt.LOGGER.error("closed connection {} with code {} and reason {}", this.getConnection().getRemoteSocketAddress(), code, reason);
    }

    @Override
    public void onError(Exception ex) {
        if(ex instanceof RevoltException) {
            throw (RevoltException) ex;
        }
        ex.printStackTrace();
    }

    @Override
    public void sendPing() {
        getConnection().send("{\"type\": \"Ping\", \"data\": 0}");
    }

    public static void stopExecutorService() {
        executorService.shutdown();
    }
}
