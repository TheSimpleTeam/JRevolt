package net.thesimpleteam.jrevolt.event;

import com.google.gson.annotations.SerializedName;
import net.thesimpleteam.jrevolt.entities.ChannelType;

public class ChannelCreatedEvent implements Event {

    private final String channelType;
    private transient ChannelType type;
    @SerializedName("_id")
    private final String id;
    private final String server;
    private final String name;

    public ChannelCreatedEvent(String channelType, String id, String server, String name) {
        this.channelType = channelType;
        this.type = ChannelType.getChannelType(channelType);
        this.id = id;
        this.server = server;
        this.name = name;
    }

    public String getChannelType() {
        return channelType;
    }

    public ChannelType getType() {
        return type;
    }

    public String getId() {
        return id;
    }

    public String getServer() {
        return server;
    }

    public String getName() {
        return name;
    }
}
