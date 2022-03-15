package net.thesimpleteam.jrevolt;

import com.google.common.collect.ImmutableList;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.thesimpleteam.jrevolt.commands.Command;
import net.thesimpleteam.jrevolt.commands.PingCommand;
import net.thesimpleteam.jrevolt.commands.SayCommand;
import net.thesimpleteam.jrevolt.commands.ShutdownCommand;
import net.thesimpleteam.jrevolt.entities.Server;
import net.thesimpleteam.jrevolt.entities.User;
import net.thesimpleteam.jrevolt.event.MessageDeletedEvent;
import net.thesimpleteam.jrevolt.event.MessageReceivedEvent;
import net.thesimpleteam.jrevolt.event.ReadyEvent;
import net.thesimpleteam.jrevolt.event.RevoltListener;
import net.thesimpleteam.jrevolt.utils.RequestHelper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class JRevolt {

    private final String token;
    public static final String WS_BASE_URL = "wss://ws.revolt.chat";
    public static final String BASE_URL = "https://api.revolt.chat/";
    public static final Logger LOGGER = LoggerFactory.getLogger(JRevolt.class);
    private static final Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
    public static final OkHttpClient client = new OkHttpClient.Builder().build();
    private String prefix = "!";
    private String ownerID;
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
        Map<String, String> properties = new HashMap<>();
        lines.filter(l -> !l.startsWith("#")).forEach(line -> {
            String[] split = line.split("=");
            properties.put(split[0], split[1]);
        });
        JRevolt revolt = new JRevolt(properties.get("token"));
        revolt.setOwnerID(properties.get("ownerID"));
        lines.close();
        RevoltWSClient client = new RevoltWSClient(revolt);
        client.connect();
        revolt.addCommands(new PingCommand(client), new ShutdownCommand(), new SayCommand());
        revolt.addListener(new RevoltListener() {
            @Override
            public void onMessageReceived(MessageReceivedEvent event) {
                if(Objects.equals(event.getMessage().getAuthorID(), revolt.getSelfUser().getId())) return;
                LOGGER.info("Received message from user : {} : {}.", event.getMessage().getAuthor().getUsername(),
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

    public Gson getGson() {
        return gson;
    }

    public ImmutableList<User> getUsers() {
        return new ImmutableList.Builder<User>().addAll(users).build();
    }

    public ImmutableList<RevoltListener> getListeners() {
        return new ImmutableList.Builder<RevoltListener>().addAll(listeners).build();
    }

    public ImmutableList<Server> getServers() {
        return new ImmutableList.Builder<Server>().addAll(servers).build();
    }

    public ImmutableList<Command> getCommands() {
        return new ImmutableList.Builder<Command>().addAll(commands).build();
    }

    public void addCommand(Command command) {
        if(command.getName() == null) throw new IllegalArgumentException("Command name cannot be null");
        if(command.getName().isEmpty()) throw new IllegalArgumentException("Command name cannot be empty");
        if(Pattern.matches("\\s+", command.getName())) throw new IllegalArgumentException("Command name cannot contain spaces");
        if(commands.stream().anyMatch(c -> c.getName().equalsIgnoreCase(command.getName()))) throw new IllegalArgumentException("Command name already exists");
        commands.add(command);
    }

    public void addCommands(Command... commands) {
        Arrays.stream(commands).forEach(this::addCommand);
    }

    public void addListener(RevoltListener listener) {
        listeners.add(listener);
    }

    public void addListeners(RevoltListener... listeners) {
        Arrays.stream(listeners).forEach(this::addListener);
    }

    public String getPrefix() {
        return prefix;
    }

    public String getOwnerID() {
        return ownerID;
    }

    public void setPrefix(@NotNull String prefix) {
        this.prefix = prefix;
    }

    public void setOwnerID(String ownerID) {
        this.ownerID = ownerID;
    }

    public Optional<User> getUser(String id) {
        return users.stream().filter(user -> user.getId().equals(id)).findFirst();
    }

    public User getSelfUser() {
        Request request = RequestHelper.get("users/@me", token);
        try {
            String response = client.newCall(request).execute().body().string();
            return gson.fromJson(response, User.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    void addUser(User user) {
        users.add(user);
    }

    void addServer(Server server) {
        servers.add(server);
    }
}
