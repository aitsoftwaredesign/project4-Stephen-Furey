package com.example.parrot.Model;

public class Chat
{
    private String sender;
    private String receiver;
    private String message;
    private boolean isseen;

    public Chat(String sender, String receiver, String message, boolean seenTrue)
    {
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
        this.isseen = isseen;
    }

    public Chat()
    {

    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setIsseen(boolean isseen) {
        this.isseen = isseen;
    }

    public boolean isIsseen() {
        return isseen;
    }
}
