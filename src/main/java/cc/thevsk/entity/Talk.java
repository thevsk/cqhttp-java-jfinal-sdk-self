package cc.thevsk.entity;

public class Talk {
    private String message;
    private Type type;
    private Long privateUser;
    private String reply;
    private Long author;

    public Long getPrivateUser() {
        return privateUser;
    }

    public void setPrivateUser(Long privateUser) {
        this.privateUser = privateUser;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getAuthor() {
        return author;
    }

    public void setAuthor(Long author) {
        this.author = author;
    }

    public static enum Type {
        contains,equals,startsWith
    }
}