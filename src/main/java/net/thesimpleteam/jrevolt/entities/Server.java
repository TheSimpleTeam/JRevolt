package net.thesimpleteam.jrevolt.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Server {

    @SerializedName("_id")
    private final String id;
    private final String owner;
    private final String name;
    private final String[] channels;
    private final Category[] categories;
    @SerializedName("system_message")
    private final SystemMessage systemMessage;
    @SerializedName("default_permissions")
    private final int[] defaultPermissions;
    private final Icon icon;
    private final Icon banner;

    public Server(String id, String owner, String name, String[] channels, Category[] categories, SystemMessage systemMessage, int[] defaultPermissions, Icon icon, Icon banner) {
        this.id = id;
        this.owner = owner;
        this.name = name;
        this.channels = channels;
        this.categories = categories;
        this.systemMessage = systemMessage;
        this.defaultPermissions = defaultPermissions;
        this.icon = icon;
        this.banner = banner;
    }

    public static class SystemMessage {
        @SerializedName("user_joined")
        private final String userJoined;
        @SerializedName("user_left")
        private final String userLeft;
        @SerializedName("user_kicked")
        private final String userKicked;
        @SerializedName("user_banned")
        private final String userBanned;

        private SystemMessage(String userJoined, String userLeft, String userKicked, String userBanned) {
            this.userJoined = userJoined;
            this.userLeft = userLeft;
            this.userKicked = userKicked;
            this.userBanned = userBanned;
        }

        public String getUserJoined() {
            return userJoined;
        }

        public String getUserLeft() {
            return userLeft;
        }

        public String getUserKicked() {
            return userKicked;
        }

        public String getUserBanned() {
            return userBanned;
        }
    }

    /**
     * It can be either an icon or a banner or an attachment {@link Icon#getTag()}
     */
    public static class Icon {
        @SerializedName("_id")
        @Expose
        private final String id;
        @Expose
        private final String tag;
        @Expose
        private final String filename;
        @Expose
        @SerializedName("content_type")
        private final String contentType;
        @Expose
        private final int size;
        @Expose
        private final Metadata metadata;

        private Icon(String id, String tag, String filename, String contentType, int size, Metadata metadata) {
            this.id = id;
            this.tag = tag;
            this.filename = filename;
            this.contentType = contentType;
            this.size = size;
            this.metadata = metadata;
        }

        public Metadata getMetadata() {
            return metadata;
        }

        public String getId() {
            return id;
        }

        public String getTag() {
            return tag;
        }

        public String getFilename() {
            return filename;
        }

        public String getContentType() {
            return contentType;
        }

        public int getSize() {
            return size;
        }
    }

    public class Category {
        @Expose
        public String id, title;
        @Expose
        public String[] channels;
    }

    public String getId() {
        return id;
    }

    public String getOwner() {
        return owner;
    }

    public String getName() {
        return name;
    }

    public String[] getChannels() {
        return channels;
    }

    public Category[] getCategories() {
        return categories;
    }

    public SystemMessage getSystemMessage() {
        return systemMessage;
    }

    public int[] getDefaultPermissions() {
        return defaultPermissions;
    }

    public Icon getIcon() {
        return icon;
    }

    public Icon getBanner() {
        return banner;
    }
}
