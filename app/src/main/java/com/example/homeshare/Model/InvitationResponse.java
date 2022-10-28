package com.example.homeshare.Model;

import com.google.firebase.firestore.DocumentReference;

public class InvitationResponse {
    private DocumentReference invitationRef;
    private DocumentReference posterRef;
    private DocumentReference responderRef;
    private boolean response;

    public DocumentReference getInvitationRef() {
        return invitationRef;
    }

    public void setInvitationRef(DocumentReference invitationRef) {
        this.invitationRef = invitationRef;
    }

    public DocumentReference getPosterRef() {
        return posterRef;
    }

    public void setPosterRef(DocumentReference posterRef) {
        this.posterRef = posterRef;
    }

    public DocumentReference getResponderRef() {
        return responderRef;
    }

    public void setResponderRef(DocumentReference responderRef) {
        this.responderRef = responderRef;
    }

    public boolean isResponse() {
        return response;
    }

    public void setResponse(boolean response) {
        this.response = response;
    }
}
