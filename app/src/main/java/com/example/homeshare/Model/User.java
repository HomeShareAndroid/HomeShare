package com.example.homeshare.Model;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

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

    public List<Invitation> getFeed() {
        Invitation.deleteExpiredInvitations();
        List<Invitation> invites = new ArrayList<>();
        db.collection("invitations")
                .whereNotEqualTo("posterUid", getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                invites.add(document.toObject(Invitation.class));
                            }
                        }
                    }
                });
        return invites;
    }


    public List<Invitation> getFeed(String orderBy) {
        Invitation.deleteExpiredInvitations();
        List<Invitation> invites = new ArrayList<>();
        db.collection("invitations")
                .whereNotEqualTo("posterUid", getUid()).orderBy(orderBy)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                invites.add(document.toObject(Invitation.class));
                            }
                        }
                    }
                });
        return invites;
    }
}
