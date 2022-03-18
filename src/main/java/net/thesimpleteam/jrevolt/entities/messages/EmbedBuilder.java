package net.thesimpleteam.jrevolt.entities.messages;

import java.awt.Color;
import java.net.MalformedURLException;
import java.net.URL;

public class EmbedBuilder {

    private final String content;
    private String iconUrl;
    private String url;
    private String title;
    private String description;
    private int media;
    private String colour;

    public EmbedBuilder() {
        this.content = "Text";
    }

    public EmbedBuilder setTitle(String title) {
        this.title = title;
        return this;
    }

    public EmbedBuilder setDescription(String description) {
        this.description = description;
        return this;
    }

    public EmbedBuilder setUrl(String url) {
        try {
            new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return this;
        }
        this.url = url;
        return this;
    }

    public EmbedBuilder setIconUrl(String iconUrl) {
        try {
            new URL(iconUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return this;
        }
        this.iconUrl = iconUrl;
        return this;
    }

    public EmbedBuilder setMediaIndex(int mediaIndex) {
        this.media = mediaIndex;
        return this;
    }

    public EmbedBuilder setColour(String colour) {
        return setColour(Color.decode(colour));
    }

    public EmbedBuilder setColour(int colour) {
        return setColour(String.valueOf(colour));
    }

    public EmbedBuilder setColour(Color color) {
        this.colour = rgbToHex(color.getRed(), color.getGreen(), color.getBlue());
        return this;
    }

    private String rgbToHex(int r, int g, int b) {
        return String.format("#%06X", (0xFFFFFF & (r << 16) + (g << 8) + b));
    }

    public Embed build() {
        return new Embed(this.content, this.iconUrl, this.url, null, this.title,this.description, null, null, this.media, this.colour);
    }
}
