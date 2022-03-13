package net.thesimpleteam.jrevolt.commands;

import net.thesimpleteam.jrevolt.entities.Message;

public class PingCommand extends Command {

    public PingCommand() {
        super("ping");
    }

    @Override
    public void execute(String[] args, Message message) {
        message.reply("pong");
    }
}
