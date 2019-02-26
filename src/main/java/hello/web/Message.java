package hello.web;

public class Message {

    //id of the message
    private final long id;
    //content of the message
    private final String content;

    public Message(long id, String content) {
        this.id = id;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }


    @Override
    public boolean equals(Object other) {

        if (other == null) {
            return false;
        }
        if (other == this) {
            return true;
        }
        if (other instanceof Message) {
            Message message = (Message) other;
            return message.getId() == this.id && message.getContent().equals(this.content);
        }
        return false;
    }
}