package net.thesimpleteam.jrevolt.commands;

import net.thesimpleteam.jrevolt.RevoltWSClient;
import net.thesimpleteam.jrevolt.entities.Message;

public class ShutdownCommand extends Command {

    public ShutdownCommand() {
        super("shutdown", "Shutdown the bot");
    }

    @Override
    public void execute(String[] args, Message message) {
        message.reply("Shutting down...");
        RevoltWSClient.stopExecutorService();
        System.exit(0);
    }
}
