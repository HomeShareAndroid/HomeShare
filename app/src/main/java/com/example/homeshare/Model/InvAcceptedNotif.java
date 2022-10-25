package com.example.homeshare.Model;

import java.util.ArrayList;
import java.util.List;

public class InvAcceptedNotif extends Notification{
    final String message1 = "Congratulations! Your housing invitation on HomeShare" +
            " has been accepted. Open HomeShare now to accept or reject the response! ";


    public InvAcceptedNotif(InvitationResponse response) {
        super();
        super.message = message1;
        List<User> recipients = new ArrayList<>();
        recipients.add(response.poster);
        super.recipients = recipients;
    }


}
