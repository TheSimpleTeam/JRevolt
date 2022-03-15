package net.thesimpleteam.jrevolt.commands;

import net.thesimpleteam.jrevolt.JRevolt;
import net.thesimpleteam.jrevolt.RevoltWSClient;
import net.thesimpleteam.jrevolt.entities.Message;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;

public class PingCommand extends Command {

    private final RevoltWSClient client;

    public PingCommand(RevoltWSClient client) {
        super("ping");
        this.client = client;
    }

    @Override
    public void execute(String[] args, Message message) {
        long currentTime = System.currentTimeMillis();
        try {
            InetAddress.getByName(new URL(JRevolt.BASE_URL).getHost()).isReachable(5000);
            currentTime = System.currentTimeMillis() - currentTime;
        } catch (IOException e) {
            //THIS SHOULD NEVER HAPPEN
            throw new RuntimeException(e);
        }
        message.reply("Pong. The bot ping is " + currentTime + " ms.");
    }
}
