package com.example.homeshare.Model;

import java.util.ArrayList;
import java.util.List;

public class ResRejectedNotif extends Notification{
    final private String message1 = "Your response to a housing invitation of HomeShare " +
            "has been rejected. Open HomeShare to see more details!";

    public ResRejectedNotif(List<InvitationResponse> rejected) {
        super();
        super.message = message1;
        List<User> recipients = new ArrayList<>();
        /*for (InvitationResponse invitationResponse : rejected) {
            recipients.add(invitationResponse.responder);
        }*/
        super.recipients = recipients;

    }
}
