package com.example.homeshare.Model;

import java.util.ArrayList;
import java.util.List;

public class ResAcceptedNotif extends Notification {
    final private String message1 = "Congratulations! Your response to a housing invitation of HomeShare " +
            "has been accepted. Open HomeShare to see more details!";
    public ResAcceptedNotif(InvitationResponse accepted) {
        super();
        super.message = message1;
        List<User> recipients = new ArrayList<>();
        recipients.add(accepted.responder);
        super.recipients = recipients;
        // do stuff
    }
}
