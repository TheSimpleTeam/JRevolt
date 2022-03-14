package net.thesimpleteam.jrevolt.entities;

import java.util.Arrays;

public enum ChannelType {

    TEXT_CHANNEL,
    VOICE_CHANNEL,
    ;

    public static ChannelType getChannelType(String type) {
        return valueOf(Arrays.stream(values()).map(Enum::name).map(String::toLowerCase).map(s -> s.replace("_", ""))
                .filter(s -> s.equalsIgnoreCase(type))
                .findFirst().orElseThrow(() -> new IllegalArgumentException("Invalid channel type: " + type)));
    }

}
