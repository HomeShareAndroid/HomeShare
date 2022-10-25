package com.example.homeshare.Model;


import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class UserHelper {
    public static User getUser(String Uid) {
        final User[] user = new User[1];
        try {
            DocumentReference documentReference = FirebaseFirestore.getInstance().collection("users").document(Uid);
            documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            user[0] = document.toObject(User.class);

                        } else {

                        }
                    } else {

                    }
                }
            });

        } catch (Exception e) {

        }
        return user[0];

    }


    public static User getUser() {
        FirebaseUser fbUser = FirebaseAuth.getInstance().getCurrentUser();
        final User[] user = new User[1];
        try {
            DocumentReference documentReference = FirebaseFirestore
                    .getInstance()
                    .collection("users")
                    .document(fbUser.getUid());
            documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            user[0] = document.toObject(User.class);

                        } else {

                        }
                    } else {

                    }
                }
            });

        } catch (Exception e) {

        }
        return user[0];

    }

}
