package com.example.homeshare.Model;


import android.net.Uri;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


public class User  {

    static FirebaseUser fbUser;
    static FirebaseFirestore db;
    static {
        fbUser = FirebaseAuth.getInstance().getCurrentUser();
        db = FirebaseFirestore.getInstance();
    }

    public static String getEmail() {
        return fbUser.getEmail();
    }

    public String getName() {
        return fbUser.getDisplayName();
    }

    public Uri getPhotoUrl() {
        return fbUser.getPhotoUrl();
    }

    public static String getMajor() {
        final String[] major = new String[1];
        try {
        Query query = db.collection("users").whereEqualTo("email", getEmail());
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {

                        major[0] = (String) document.getData().get("major");

                    }
                }
            }
        });

        } catch (Exception ignored) {

        }
        return major[0];
    }

    public static String getGraduationClass() {
        final String[] grad = new String[1];
        try {
            Query query = db.collection("users").whereEqualTo("email", getEmail());
            query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            grad[0] = (String) document.getData().get("graduationClass");

                        }
                    }
                }
            });

        } catch (Exception ignored) {

        }
        return grad[0];
    }

    public static User getOtherUser(String email) {
        final User[] user = new User[1];
        try {
            Query query = FirebaseFirestore.getInstance().collection("users").whereEqualTo("email", email);
            query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            user[0] = document.toObject(User.class);

                        }
                    }
                }
            });
        } catch (Exception e) {

        }
        return user[0];

    }









}
