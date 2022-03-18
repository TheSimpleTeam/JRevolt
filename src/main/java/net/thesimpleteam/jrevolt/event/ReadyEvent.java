package net.thesimpleteam.jrevolt.event;

import com.google.gson.annotations.Expose;
import net.thesimpleteam.jrevolt.entities.Server;
import net.thesimpleteam.jrevolt.entities.User;

public class ReadyEvent implements Event {

    @Expose
    private final User[] users;
    @Expose
    private final Server[] server;
    //TODO: Add members and channels

    public ReadyEvent(User[] users, Server[] server) {
        this.users = users;
        this.server = server;
    }

    public User[] getUsers() {
        return users;
    }

    public Server[] getServer() {
        return server;
    }
}
