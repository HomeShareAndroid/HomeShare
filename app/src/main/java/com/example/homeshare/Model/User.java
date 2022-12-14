package com.example.homeshare.Model;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class User {

    private String name;
    private String email;
    private String Uid;
    private String major;
    private String phone;
    private String aboutMe;
    private String photoUri;

    public User(String name, String email, String uid, String major, String phone, String aboutMe, String photoUri) {
        this.name = name;
        this.email = email;
        this.Uid = uid;
        this.major = major;
        this.phone = phone;
        this.aboutMe = aboutMe;
        this.photoUri = photoUri;
    }

    public User(){

    }
    public String getUid() {
        return Uid;
    }
    public String getEmail() {
        return email;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        /*db.collection("users")
                .document(getUid())
                .update("name",name);*/
    }

    public void acceptInvitation(Invitation inv) {

    }
    public void rejectInvitation(Invitation inv) {

    }
    public void acceptResponse(InvitationResponse response) {

    }
    public void rejectResponse(InvitationResponse response){

    }

    public List<InvitationResponse> getOutgoingNoResponses() {
        return new ArrayList<>();
    }

    public List<InvitationResponse> getOutgoingYesResponses() {
        return new ArrayList<>();
    }

    public List<InvitationResponse> getIncomingYesResponses() {
        return new ArrayList<>();
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUid(String uid) {
        Uid = uid;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAboutMe() {
        return aboutMe;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }

    public String getPhotoUri() {
        return photoUri;
    }

    public void setPhotoUri(String photoUri) {
        this.photoUri = photoUri;
    }


}
