package com.example.homeshare.Model;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class User {
    //private String userImageLink;
    // private String graduationClass;
    //private String major;
    private String name;
    private String email;
    private String Uid;
    private String major;
    private String phone;
    private String aboutMe;
    private String photoUri;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

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
/*
    public String getUserImageLink() {
        return userImageLink;
    }

    public String getGraduationClass() {
        return graduationClass;
    }

    public String getMajor() {
        return major;
    }
    public void setUserImageLink(String userImageLink) {
        this.userImageLink = userImageLink;
        db.collection("users")
                .document(getUid())
                .update("userImageLink",userImageLink);
    }




    public void setGraduationClass(String graduationClass) {
        this.graduationClass = graduationClass;
        db.collection("users")
                .document(getUid())
                .update("graduationClass",graduationClass);
    }

    public void setMajor(String major) {
        this.major = major;
        db.collection("users")
                .document(getUid())
                .update("major",major);
    }
    */

}
