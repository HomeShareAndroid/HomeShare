package com.example.homeshare.Model;

import com.google.firebase.firestore.FirebaseFirestore;

public class User {
    private String userImageLink;
    private String name;
    private String graduationClass;
    private String major;
    private String email;
    private String Uid;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public User(){

    }

    public String getUserImageLink() {
        return userImageLink;
    }

    public String getName() {
        return name;
    }

    public String getGraduationClass() {
        return graduationClass;
    }

    public String getMajor() {
        return major;
    }

    public String getEmail() {
        return email;
    }

    public String getUid() {
        return Uid;
    }

    public void setUserImageLink(String userImageLink) {
        this.userImageLink = userImageLink;
        db.collection("users")
                .document(getUid())
                .update("userImageLink",userImageLink);
    }

    public void setName(String name) {
        this.name = name;
        db.collection("users")
                .document(getUid())
                .update("name",name);
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
}
