package API.messages;

public class Message {

    private final String content;

    public Message(String content) {
        this.content = content;
    }


    public String getContent() {
        return content;
    }

    @Override
    public boolean equals(Object obj) {

        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj instanceof Message) {
            Message message = (Message) obj;
            return message.getContent().equals(this.content);
        }
        return false;
    }
}