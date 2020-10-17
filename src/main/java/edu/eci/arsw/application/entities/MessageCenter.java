package edu.eci.arsw.application.entities;

import java.util.List;

public abstract class MessageCenter {
    protected int chat;
    protected List<Message> messages;

    public int getChat() {
        return chat;
    }

    public void setChat(int chat) {
        this.chat = chat;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

}
