# JRevolt

A Java Api wrapper for [Revolt](https://revolt.chat/).

---

### Example

```java
public class Main {
    
    public static class MyListener implements RevoltListener {
        @Override
        public void onMessageReceived(MessageReceivedEvent event) {
            if(event.getContent().equalsIngoreCase("ping")) {
                event.getMessage().reply("Pong !");
            }
        }
    }
    
    public static class PingCommand extends Command {
        public PingCommand(String name, String description) {
            super(name, description);
        }

        @Override
        public void execute(String[] args, Message message) {
            message.reply("Pong !");
        }
    }
    
    public static void main(String[] args) {
        JRevolt revolt = new JRevolt("YOUR_TOKEN");
        revolt.addListener(new MyListener());
        revolt.addCommands(new PingCommand("ping", "Pong !"));
    }
    
}

```

---

## Events:

- [x] Error

- [x] Authenticated

- [x] Pong

- [x] Ready

- [x] Message

- [x] MessageUpdate

- [x] MessageDelete

- [x] ChannelCreate

- [x] ChannelUpdate

- [x] ChannelDelete

- [x] ChannelGroupJoin

- [x] ChannelGroupLeave

- [x] ChannelStartTyping

- [x] ChannelStopTyping

- [x] ChannelAck

- [x] ServerUpdate

- [x] ServerDelete

- [x] ServerMemberUpdate

- [x] ServerMemberJoin

- [x] ServerMemberLeave

- [x] ServerRoleUpdate

- [x] ServerRoleDelete

- [ ] UserUpdate

- [ ] UserRelationship
