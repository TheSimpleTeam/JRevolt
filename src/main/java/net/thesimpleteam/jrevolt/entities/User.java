package net.thesimpleteam.jrevolt.entities;

import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("_id")
    private final String id;
    private final String username, relationship;
    private final int badges;
    private boolean online;

    private User(String id, String username, String relationship, int badges, boolean online) {
        this.id = id;
        this.username = username;
        this.relationship = relationship;
        this.badges = badges;
        this.online = online;
    }

    public static class Avatar {
        @SerializedName("_id")
        private final String id;
        private final String tag, filename;
        //private final Metadata metadata;
        @SerializedName("content_type")
        private final String contentType;
        private final int size;

        private Avatar(String id, String tag, String filename, /*Metadata metadata,*/ String contentType, int size) {
            this.id = id;
            this.tag = tag;
            this.filename = filename;
            //this.metadata = metadata;
            this.contentType = contentType;
            this.size = size;
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

        /*public Metadata getMetadata() {
            return metadata;
        }*/

        public String getContentType() {
            return contentType;
        }

        public int getSize() {
            return size;
        }
    }

    public static class Status {
        private final String text, presence;

        private Status(String text, String presence) {
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

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getRelationship() {
        return relationship;
    }

    public int getBadges() {
        return badges;
    }

    public boolean isOnline() {
        return online;
    }
}
