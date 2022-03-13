package net.thesimpleteam.jrevolt.commands;

import net.thesimpleteam.jrevolt.JRevolt;
import net.thesimpleteam.jrevolt.entities.Message;
import org.jetbrains.annotations.NotNull;

public abstract class Command {

    private final String name, description, usage;
    private final String[] aliases;
    private JRevolt revolt;

    protected Command(@NotNull String name, String description, String usage, String[] aliases) {
        this.name = name;
        this.description = description;
        this.usage = usage;
        this.aliases = aliases;
    }

    protected Command(String name, String description) {
        this(name, description, "", new String[]{});
    }

    protected Command(String name) {
        this(name, "", "", new String[]{});
    }

    public abstract void execute(String[] args, Message message);

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getUsage() {
        return usage;
    }

    public String[] getAliases() {
        return aliases;
    }

    protected JRevolt getRevolt() {
        return revolt;
    }
}
