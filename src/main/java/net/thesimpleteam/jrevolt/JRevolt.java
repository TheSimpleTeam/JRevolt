package net.thesimpleteam.jrevolt;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.thesimpleteam.jrevolt.commands.Command;
import net.thesimpleteam.jrevolt.commands.PingCommand;
import net.thesimpleteam.jrevolt.commands.ShutdownCommand;
import net.thesimpleteam.jrevolt.entities.Server;
import net.thesimpleteam.jrevolt.entities.User;
import net.thesimpleteam.jrevolt.event.MessageDeletedEvent;
import net.thesimpleteam.jrevolt.event.MessageReceivedEvent;
import net.thesimpleteam.jrevolt.event.ReadyEvent;
import net.thesimpleteam.jrevolt.event.RevoltListener;
import okhttp3.OkHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class JRevolt {

    private final String token;
    public static final String WS_BASE_URL = "wss://ws.revolt.chat";
    public static final String BASE_URL = "https://api.revolt.chat/";
    public static final Logger LOGGER = LoggerFactory.getLogger(JRevolt.class);
    private final Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
    public static final OkHttpClient client = new OkHttpClient.Builder().build();
    private String prefix = "!";
    private final List<User> users = new ArrayList<>();
    private final List<Server> servers = new ArrayList<>();
    private final List<Command> commands = new ArrayList<>();
    private final List<RevoltListener> listeners = new ArrayList<>();
    public static final boolean DEBUG = true;
    
    public JRevolt(String token) {
        this.token = token;
    }

    public static void main(String[] args) throws IOException {
        Stream<String> lines = Files.lines(new File("config.properties").toPath());
        JRevolt revolt = new JRevolt(lines.filter(line -> !line.startsWith("#") && line.startsWith("token=")).findFirst().get().split("=")[1]);
        lines.close();
        RevoltWSClient client = new RevoltWSClient(revolt);
        client.connect();
        revolt.getCommands().addAll(Arrays.asList(new PingCommand(), new ShutdownCommand()));
        revolt.getListeners().add(new RevoltListener() {
            @Override
            public void onMessageReceived(MessageReceivedEvent event) {
                LOGGER.info("Received message from user : " + revolt.getUser(event.getMessage().getAuthor()).get().getUsername() + " : " +
                        event.getMessage().getContent());
            }

            @Override
            public void onReady(ReadyEvent event) {
                LOGGER.info("Ready");
            }

            @Override
            public void onMessageDeleted(MessageDeletedEvent event) {
                LOGGER.info("Message deleted");
            }
        });
    }

    public String getToken() {
        return token;
    }

    public List<User> getUsers() {
        return users;
    }

    public Gson getGson() {
        return gson;
    }

    public List<RevoltListener> getListeners() {
        return listeners;
    }

    public List<Server> getServers() {
        return servers;
    }

    public OkHttpClient getClient() {
        return client;
    }

    public List<Command> getCommands() {
        return commands;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public Optional<User> getUser(String id) {
        return users.stream().filter(user -> user.getId().equals(id)).findFirst();
    }
}
