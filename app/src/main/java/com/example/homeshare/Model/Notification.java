package com.example.homeshare.Model;

import java.util.List;

public abstract class Notification {
    String message = "";
    List<User> recipients;
    User otherUser;

    public void notifyUser() {

        for (User r : recipients ) {
            // sendMessage(message, r)
        }
    }
}
