package com.example.homeshare.Model;

/*
import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.concurrent.Executor;


public class UserHelper extends Task<User> {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String Uid;
    DocumentReference doc;

    public UserHelper(String uid) {
        Uid = uid;
        doc = db.collection("users").document(uid);
    }

    @NonNull
    @Override
    public Task<User> addOnFailureListener(@NonNull OnFailureListener onFailureListener) {
        return null;
    }

    @NonNull
    @Override
    public Task<User> addOnFailureListener(@NonNull Activity activity, @NonNull OnFailureListener onFailureListener) {
        return null;
    }

    @NonNull
    @Override
    public Task<User> addOnFailureListener(@NonNull Executor executor, @NonNull OnFailureListener onFailureListener) {
        return null;
    }

    @NonNull
    @Override
    public Task<User> addOnSuccessListener(@NonNull OnSuccessListener<? super User> onSuccessListener) {
        final User[] user = new User[1];
        try {
            doc.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
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
        return null;
    }

    @NonNull
    @Override
    public Task<User> addOnSuccessListener(@NonNull Activity activity, @NonNull OnSuccessListener<? super User> onSuccessListener) {
        return null;
    }

    @NonNull
    @Override
    public Task<User> addOnSuccessListener(@NonNull Executor executor, @NonNull OnSuccessListener<? super User> onSuccessListener) {
        return null;
    }

    @Nullable
    @Override
    public Exception getException() {
        return null;
    }

    @Override
    public User getResult() {
        return null;
    }

    @Override
    public <X extends Throwable> User getResult(@NonNull Class<X> aClass) throws X {
        return null;
    }


    @Override
    public boolean isCanceled() {
        return false;
    }

    @Override
    public boolean isComplete() {
        return false;
    }

    @Override
    public boolean isSuccessful() {
        return false;
    }

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


    @Override protected User call() throws Exception{

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

 */