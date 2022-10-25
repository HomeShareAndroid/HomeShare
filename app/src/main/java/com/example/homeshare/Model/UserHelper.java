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


public class UserHelper {
    public static User getUser(String Uid) {
        final User[] user = new User[1];
        try {
            Query query = FirebaseFirestore.getInstance().collection("users").whereEqualTo("email", Uid);
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

    public static User getUser() {
        final User[] user = new User[1];
        FirebaseUser fbUser = FirebaseAuth.getInstance().getCurrentUser();
        try {
            Query query = FirebaseFirestore.getInstance().collection("users")
                    .whereEqualTo("email", fbUser.getUid());
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
        } catch (Exception ignored) {

        }
        return user[0];
    }

}
