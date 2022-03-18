package net.thesimpleteam.jrevolt.commands;

import net.thesimpleteam.jrevolt.JRevolt;
import net.thesimpleteam.jrevolt.entities.messages.EmbedBuilder;
import net.thesimpleteam.jrevolt.entities.messages.Message;

import java.awt.Color;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;

public class PingCommand extends Command {

    public PingCommand() {
        super("ping");
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
        message.replyEmbed(".", new EmbedBuilder()
                .setTitle("Pong")
                .setIconUrl("https://avatars.githubusercontent.com/u/86493495?s=200&v=4")
                .setUrl("https://developers.revolt.chat/api/")
                .setColour(String.valueOf(Color.pink.getRGB()))
                .setDescription("The bot ping is " + currentTime + " ms.")
                .build()
        );
    }
}
