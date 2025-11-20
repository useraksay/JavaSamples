package lld.messageQueue;

public class Message {
    private final String content;

    public Message(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return content;
    }
}
