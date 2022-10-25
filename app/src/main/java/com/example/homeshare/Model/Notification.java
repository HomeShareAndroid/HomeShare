package com.example.homeshare.Model;

import java.util.List;

public abstract class Notification {
    protected String message = "";
    protected List<User> recipients;

    public Notification(String message, List<User> recipients) {
        this.message = message;
        this.recipients = recipients;
    }

    public Notification() {

    }

    public void notifyUser() {

        for (User r : recipients ) {
            // sendMessage(message, r.getEmail());
        }
    }
}
