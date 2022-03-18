package net.thesimpleteam.jrevolt.entities.messages;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import net.thesimpleteam.jrevolt.entities.Server;
import org.jetbrains.annotations.Nullable;

public class Embed {

    @Expose
    private final String type;
    @Expose
    @SerializedName("icon_url")
    private final String iconUrl;
    @Expose
    private final String url;
    @Expose
    @SerializedName("site_name")
    private final String siteName;
    @Expose
    private final String title;
    @Expose
    private final String description;
    @Expose
    private final Special special;
    @Expose
    private final Content content;
    @Expose
    private int mediaIndex = -1;
    @SerializedName("media")
    @Expose(deserialize = false)
    private String mediaId;
    private Server.Icon media;
    @Expose
    private final String colour;

    public Embed(String type, String iconUrl, String url, String siteName, String title, String description, Special special, Content content, int mediaIndex, String colour) {
        this.type = type;
        this.iconUrl = iconUrl;
        this.url = url;
        this.siteName = siteName;
        this.title = title;
        this.description = description;
        this.special = special;
        this.content = content;
        this.mediaIndex = mediaIndex;
        this.colour = colour;
    }

    public String getType() {
        return type;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public String getUrl() {
        return url;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getMediaIndex() {
        return mediaIndex;
    }

    public Special getSpecial() {
        return special;
    }

    public String getColour() {
        return colour;
    }

    public String getSiteName() {
        return siteName;
    }

    public Content getContent() {
        return content;
    }

    public Server.Icon getMedia() {
        return media;
    }

    public class Special {
        @Expose
        private final String type, id, timestamp;
        @Expose
        @SerializedName("content_type")
        private final String contentType;

        private Special(String type, String id, String timestamp, String contentType) {
            this.type = type;
            this.id = id;
            this.timestamp = timestamp;
            this.contentType = contentType;
        }

        /**
         * It can either be {@code Youtube} or {@code Twitch} or {@code Spotify} or {@code Soundcloud} or {@code Bandcamp}
         *
         * @return the type of 3rd party embed
         */
        public String getType() {
            return type;
        }

        public String getId() {
            return id;
        }

        /**
         * @return the timestamp of the embed or null if the type is not {@code Youtube}. {@link #getType()}
         */
        @Nullable
        public String getTimestamp() {
            return timestamp;
        }

        /**
         * It can be null if the type is {@code Youtube} or {@code Soundcloud}
         * {@link #getContentTypeAsEnum()}
         */
        @Nullable
        public String getContentType() {
            return contentType;
        }

        public ContentType getContentTypeAsEnum() {
            return ContentType.valueOf(contentType.toUpperCase());
        }
    }

    /**
     * For {@link Special#contentType}
     */
    public enum ContentType {
        CHANNEL, CLIP, VIDEO, ALBUM, TRACK;
    }

    public class Content {
        @Expose
        private final String url, size;
        @Expose
        private final int width, height;

        private Content(String url, String size, int width, int height) {
            this.url = url;
            this.size = size;
            this.width = width;
            this.height = height;
        }

        public String getUrl() {
            return url;
        }

        /**
         * @return {@code Large} or {@code Small} or {@code null} if it's a video
         */
        @Nullable
        public String getSize() {
            return size;
        }

        public int getWidth() {
            return width;
        }

        public int getHeight() {
            return height;
        }
    }
}
