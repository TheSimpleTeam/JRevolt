package net.thesimpleteam.jrevolt.event;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import net.thesimpleteam.jrevolt.entities.messages.Message;

public class MessageUpdatedEvent {

    @Expose
    private final String id;
    @SerializedName("channel")
    @Expose
    private final String channelID;
    @Expose
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

    public class Data {
        @Expose private final Message.Edited edited;
        @Expose private final String content;

        public Data(Message.Edited edited, String content) {
            this.edited = edited;
            this.content = content;
        }

        public Message.Edited getEdited() {
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
