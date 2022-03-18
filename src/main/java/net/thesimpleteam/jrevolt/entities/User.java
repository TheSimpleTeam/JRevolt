package net.thesimpleteam.jrevolt.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("_id")
    @Expose
    private final String id;
    @Expose
    private final String username;
    @Expose
    private final Avatar avatar;
    @Expose
    private final Relation[] relations;
    @Expose
    private final int badges;
    @Expose
    private final Status status;
    @Expose
    private final String relationship;
    @Expose
    private final boolean online;
    @Expose
    private final int flags;
    @Expose
    private final Bot bot;

    public User(String id, String username, Avatar avatar, Relation[] relations, int badges, Status status, String relationship, boolean online, int flags, Bot bot) {
        this.id = id;
        this.username = username;
        this.avatar = avatar;
        this.relations = relations;
        this.badges = badges;
        this.status = status;
        this.relationship = relationship;
        this.online = online;
        this.flags = flags;
        this.bot = bot;
    }

    public static class Avatar {
        @SerializedName("_id")
        @Expose
        private final String id;
        @Expose
        private final String tag, filename;
        @SerializedName("content_type")
        @Expose
        private final String contentType;
        @Expose
        private final int size;
        @Expose
        private final Metadata metadata;

        public Avatar(String id, String tag, String filename, String contentType, int size, Metadata metadata) {
            this.id = id;
            this.tag = tag;
            this.filename = filename;
            this.contentType = contentType;
            this.size = size;
            this.metadata = metadata;
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

        public Metadata getMetadata() {
            return metadata;
        }
    }

    public static class Relation {
        @Expose
        private final String status;
        @SerializedName("_id")
        @Expose
        private final String id;

        public Relation(String status, String id) {
            this.status = status;
            this.id = id;
        }

        public String getStatus() {
            return status;
        }

        public String getId() {
            return id;
        }
    }

    public static class Status {
        @Expose
        private final String text, presence;

        public Status(String text, String presence) {
            this.text = text;
            this.presence = presence;
        }

        public String getText() {
            return text;
        }

        public String getPresence() {
            return presence;
        }
    }


    public static class Bot {
        @Expose
        private final String owner;

        public Bot(String owner) {
            this.owner = owner;
        }

        public String getOwner() {
            return owner;
        }
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public Avatar getAvatar() {
        return avatar;
    }

    public Relation[] getRelations() {
        return relations;
    }

    public int getBadges() {
        return badges;
    }

    public Status getStatus() {
        return status;
    }

    public String getRelationship() {
        return relationship;
    }

    public boolean isOnline() {
        return online;
    }

    public int getFlags() {
        return flags;
    }

    public Bot getBot() {
        return bot;
    }
}
