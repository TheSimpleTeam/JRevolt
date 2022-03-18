package net.thesimpleteam.jrevolt.commands;

import net.thesimpleteam.jrevolt.RevoltWSClient;
import net.thesimpleteam.jrevolt.entities.messages.Embed;
import net.thesimpleteam.jrevolt.entities.messages.EmbedBuilder;
import net.thesimpleteam.jrevolt.entities.messages.Message;
import net.thesimpleteam.jrevolt.entities.messages.MessageBuilder;

import java.awt.Color;
import java.io.File;

public class ShutdownCommand extends Command {

    public ShutdownCommand() {
        super("shutdown", "Shutdown the bot");
    }

    @Override
    public void execute(String[] args, Message message) {
        if(getRevolt().getOwnerID() == null || !message.getAuthor().getId().equals(getRevolt().getOwnerID())) {
            message.reply(":x: You are not the owner of the bot!");
            return;
        }
        Embed embed = new EmbedBuilder().setTitle("Shutting down...").setColour(Color.pink).setMediaIndex(0).build();
        Message msg = new MessageBuilder(getRevolt(), ":white_check_mark:").embeds(embed)
                .attachments(new File("abc.png"))
                .masquerade(new Message.Masquerade("JRevolt Markdown Tests", "https://avatars.githubusercontent.com/u/86493495"))
                .sendMessage(message.getChannelID());

        RevoltWSClient.stopExecutorService();
        System.exit(0);
    }
}
