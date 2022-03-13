package net.thesimpleteam.jrevolt.commands;

import net.thesimpleteam.jrevolt.entities.Message;

public class SayCommand extends Command {

    public SayCommand() {
        super("say", "Say something", "say <message>", null);
    }

    @Override
    public void execute(String[] args, Message message) {
        if (args.length == 0) {
            message.reply("Usage: " + getRevolt().getPrefix() + getUsage());
            return;
        }
        message.reply(message.getAuthor().getUsername() + " said \n >" + String.join(" ", args));
    }
}
