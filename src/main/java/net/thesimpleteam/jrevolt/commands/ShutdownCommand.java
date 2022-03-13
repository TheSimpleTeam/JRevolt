package net.thesimpleteam.jrevolt.commands;

import net.thesimpleteam.jrevolt.RevoltWSClient;
import net.thesimpleteam.jrevolt.entities.Message;

public class ShutdownCommand extends Command {

    public ShutdownCommand() {
        super("shutdown", "Shutdown the bot");
    }

    @Override
    public void execute(String[] args, Message message) {
        if(getRevolt().getOwnerID() == null || !message.getAuthor().equals(getRevolt().getOwnerID())) {
            message.reply(":x: You are not the owner of the bot!");
            return;
        }
        message.reply(":white_check_mark: Shutting down...");
        RevoltWSClient.stopExecutorService();
        System.exit(0);
    }
}
