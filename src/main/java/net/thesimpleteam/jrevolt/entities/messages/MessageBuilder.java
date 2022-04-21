package net.thesimpleteam.jrevolt.entities.messages;

import com.google.gson.JsonObject;
import net.thesimpleteam.jrevolt.JRevolt;
import net.thesimpleteam.jrevolt.RevoltWSClient;
import net.thesimpleteam.jrevolt.entities.FileType;
import net.thesimpleteam.jrevolt.entities.Server;
import net.thesimpleteam.jrevolt.utils.RequestHelper;
import okhttp3.Request;

import java.io.File;
import java.util.*;

public class MessageBuilder {

    private final JRevolt revolt;
    private String content = "";
    private File[] attachments;
    private Embed[] embeds;
    private Reply[] replies;
    private Message.Masquerade masquerade;

    public MessageBuilder(JRevolt revolt) {
        this(revolt, " ");
    }

    public MessageBuilder(JRevolt revolt, String content) {
        this.revolt = revolt;
        content(content);
    }

    private MessageBuilder content(String content) {
        if(content.length() > 2000) {
            JRevolt.LOGGER.error("Message can't be more than 2000 characters");
        } else if(content.isEmpty()) {
            JRevolt.LOGGER.warn("Message is empty");
            content = " ";
        }
        this.content = content;
        return this;
    }

    public MessageBuilder attachments(File... attachments) {
        this.attachments = attachments;
        return this;
    }

    public MessageBuilder embeds(Embed... embeds) {
        if (embeds.length > 10) {
            JRevolt.LOGGER.error("Embeds can't be more than 10");
            embeds = Arrays.copyOfRange(embeds, 0, 10);
        }
        this.embeds = embeds;
        return this;
    }

    public MessageBuilder replies(Reply... replies) {
        this.replies = replies;
        return this;
    }

    public MessageBuilder masquerade(Message.Masquerade masquerade) {
        this.masquerade = masquerade;
        return this;
    }

    public Message sendMessage(String channelId) {
        JsonObject object = new JsonObject();
        object.addProperty("content", content);
        List<String> attachmentIds = new ArrayList<>();
        if(attachments != null) {
            for (File attachment : attachments) {
                attachmentIds.add(RequestHelper.uploadFile(FileType.ATTACHMENTS, attachment));
            }
            for (Embed embed : embeds) {
                if(embed.getMediaIndex() >= 0 && embed.getMediaIndex() < attachmentIds.size()) {
                    int i = embed.getMediaIndex();
                    RevoltWSClient.setValue(Embed.class, "mediaId", attachmentIds.get(i), embed);
                    attachmentIds.remove(i);
                }
            }
        }
        object.add("attachments", revolt.getGson().toJsonTree(attachmentIds.isEmpty() ? null : attachmentIds));
        object.add("embeds", revolt.getGson().toJsonTree(embeds));
        object.add("replies", revolt.getGson().toJsonTree(replies));
        object.add("masquerade", revolt.getGson().toJsonTree(masquerade));
        Request request = RequestHelper.post("channels/" + channelId + "/messages", revolt.getToken(), object);
        Optional<String> response = RequestHelper.getBody(request);
        Message msg = response.map(s -> revolt.getGson().fromJson(s, Message.class)).orElse(null);
        if(msg != null && msg.getEmbeds() != null) {
            for (int i = 0; i < msg.getEmbeds().length; i++) {
                RevoltWSClient.setValue(Embed.class, "media", revolt.getGson().fromJson(revolt.getGson().fromJson(response.get(), JsonObject.class).get("embeds").getAsJsonArray().get(i), Server.Icon.class), msg.getEmbeds()[i]);
            }
        }
        return msg;
    }

    public static class Reply {
        private final String id;
        private final boolean mention;

        public Reply(String id, boolean mention) {
            this.id = id;
            this.mention = mention;
        }

        public String getId() {
            return id;
        }

        public boolean isMention() {
            return mention;
        }
    }
}
