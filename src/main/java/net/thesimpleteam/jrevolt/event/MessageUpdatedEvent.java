package net.thesimpleteam.jrevolt.event;

import com.google.gson.annotations.SerializedName;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MessageUpdatedEvent {

    private final String id;
    @SerializedName("channel")
    private final String channelID;
    private final Data data;

    public MessageUpdatedEvent(String id, String channelID, Data data) {
        this.id = id;
        this.channelID = channelID;
        this.data = data;
    }

    public String getId() {
        return id;
    }

    public String getChannelID() {
        return channelID;
    }

    public Data getData() {
        return data;
    }

    public static class Data {
        private final Edited edited;
        private final String content;

        public Data(Edited edited, String content) {
            this.edited = edited;
            this.content = content;
        }

        public static class Edited {
            @SerializedName("$date")
            private final String date;

            public Edited(String date) {
                this.date = date;
            }

            public String getDate() {
                return date;
            }

            public LocalDateTime getDateTime() {
                return LocalDateTime.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-ddTHH:mm:ss.SSSZ"));
            }
        }

        public Edited getEdited() {
            return edited;
        }

        /**
         * @return the edited message
         */
        public String getContent() {
            return content;
        }
    }

}
