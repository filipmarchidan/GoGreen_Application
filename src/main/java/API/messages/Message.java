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
    public boolean equals(Object o) {

        if(o == null){
            return false;
        }if(o == this){
            return true;
        }
        if(o instanceof Message){
            Message g = (Message) o;
            return g.getContent().equals(this.content);
        }
        return false;
    }
}