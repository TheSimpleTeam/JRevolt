package net.thesimpleteam.jrevolt.entities;

import com.google.gson.annotations.Expose;

public class Metadata {

    @Expose
    private final String type;

    public Metadata(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
