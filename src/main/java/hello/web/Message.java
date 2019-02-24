package hello.web;

public class Message {

    private final long id;
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
    public boolean equals(Object o) {

        if(o == null){
            return false;
        }if(o == this){
            return true;
        }
        if(o instanceof Message){
            Message g = (Message) o;
            return (g.getId() == this.id && g.getContent().equals(this.content));
        }
        return false;
    }
}