package com.example.homeshare;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homeshare.Model.Invitation;
import com.example.homeshare.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;


import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class ProfilePageActivity extends AppCompatActivity {
    User user;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profilepage);


        FirebaseUser fbUser = FirebaseAuth.getInstance().getCurrentUser();
        if (fbUser == null) {
            System.out.println("No User Logged in");
            Toast.makeText(this, "Not Logged In.",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            DocumentReference documentReference = FirebaseFirestore
                    .getInstance()
                    .collection("users")
                    .document(fbUser.getUid());
            System.out.println(fbUser.getUid());

            documentReference.get().addOnSuccessListener(documentSnapshot -> {
                user = documentSnapshot.toObject(User.class);
                ((TextView) findViewById(R.id.userName)).setText(user.getName());
                ((TextView) findViewById(R.id.profileEmail)).setText(user.getEmail());
                if (user.getAboutMe() == null) {
                    ((EditText) findViewById(R.id.profileAboutMe)).setHint("About Me . . .");

                } else {((EditText) findViewById(R.id.profileAboutMe)).setText(user.getAboutMe());}

                if (user.getMajor() == null) {
                    ((EditText) findViewById(R.id.profileMajor)).setHint("Major");
                } else {((EditText) findViewById(R.id.profileMajor)).setText(user.getMajor());}

                if (user.getPhone() == null) {
                    ((EditText) findViewById(R.id.profilePhone)).setHint("Phone Number");

                } else {((EditText) findViewById(R.id.profilePhone)).setText(user.getPhone());}

                if (user.getPhotoUri() == null) {

                } //else {((EditText) findViewById(R.id.profileAboutMe)).setText(user.getAboutMe());}
            });


        } catch (Exception e) {
            Toast.makeText(this, "Unable to Load User Info",
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void saveInfo (View view) {
        String major = String.valueOf(((EditText) findViewById(R.id.profileMajor)).getText());
        String phone = String.valueOf(((EditText) findViewById(R.id.profilePhone)).getText());
        String aboutMe = String.valueOf(((EditText) findViewById(R.id.profileAboutMe)).getText());
        FirebaseFirestore
                .getInstance()
                .collection("users")
                .document(user.getUid())
                .get()
                .addOnCompleteListener((OnCompleteListener<DocumentSnapshot>) task -> {
                    if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            DocumentReference documentReference = document.getReference();
                            if (!major.equals(""))
                                documentReference.update("major", major);
                            if (!phone.equals(""))
                                documentReference.update("phone", phone);
                            if (!aboutMe.equals(""))
                                documentReference.update("aboutMe", aboutMe);

                    } else {
                        System.out.println("Could Not Update Info");
                    }
                });
        //finish();
        //startActivity(getIntent());
    }
    public void goHome(View view) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }



}
