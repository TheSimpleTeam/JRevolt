package net.thesimpleteam.jrevolt.event;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import net.thesimpleteam.jrevolt.entities.ChannelType;

public class ChannelCreatedEvent implements Event {

    @Expose
    private final String channelType;
    private final ChannelType type;
    @SerializedName("_id")
    @Expose
    private final String id;
    @Expose
    private final String server;
    @Expose
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
