package com.example.homeshare;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

import java.net.URI;
import java.util.ArrayList;
import java.util.Date;


import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class ProfilePageActivity extends AppCompatActivity {
    User userPage;
    private RecyclerView recyclerView;
    private ImageButton changeProfileImage;
    String pageUid;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        mAuth =   FirebaseAuth.getInstance();
        setContentView(R.layout.activity_profilepage);
        changeProfileImage = findViewById(R.id.user_profile_photo);

        System.out.println("Inputted UID: "  + getIntent().getStringExtra("Uid"));
        pageUid = getIntent().getStringExtra("Uid");


        FirebaseUser fbUser = FirebaseAuth.getInstance().getCurrentUser();
        if (fbUser == null) {
            System.out.println("No User Logged in");
            Toast.makeText(this, "Not Logged In.",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        if (fbUser.getUid().equals(pageUid)) {
            changeProfileImage.setOnClickListener(v -> {
                // open gallery
                Intent openGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(openGallery, 1000);

            });
        }



        try {
            DocumentReference documentReference = FirebaseFirestore
                    .getInstance()
                    .collection("users")
                    .document(pageUid);
            System.out.println("User document path: " + documentReference.getPath());
            documentReference.get().addOnSuccessListener(documentSnapshot -> {
                userPage = documentSnapshot.toObject(User.class);
                ((TextView) findViewById(R.id.userName)).setText(userPage.getName());
                ((TextView) findViewById(R.id.profileEmail)).setText(userPage.getEmail());
                ((EditText) findViewById(R.id.profileAboutMe)).setText(userPage.getAboutMe());
                ((EditText) findViewById(R.id.profileMajor)).setText(userPage.getMajor());
                ((EditText) findViewById(R.id.profilePhone)).setText(userPage.getPhone());

//                if (userPage.getAboutMe() == null) {
//                    ((EditText) findViewById(R.id.profileAboutMe)).setHint("About Me . . .");
//
//                } else {((EditText) findViewById(R.id.profileAboutMe)).setText(userPage.getAboutMe());}
//
//                if (userPage.getMajor() == null) {
//                    ((EditText) findViewById(R.id.profileMajor)).setHint("Major");
//                } else {((EditText) findViewById(R.id.profileMajor)).setText(userPage.getMajor());}
//
//                if (userPage.getPhone() == null) {
//                    ((EditText) findViewById(R.id.profilePhone)).setHint("Phone Number");
//
//                } else {((EditText) findViewById(R.id.profilePhone)).setText(userPage.getPhone());}

//                ((EditText) findViewById(R.id.profileMajor)).setText(userPage.getMajor());
//                ((EditText) findViewById(R.id.profilePhone)).setText(userPage.getPhone());
//                if (userPage.getPhotoUri() != null) {
//                    changeProfileImage.setImageURI(Uri.parse(userPage.getPhotoUri()));
//
//                } //else {((EditText) findViewById(R.id.profileAboutMe)).setText(user.getAboutMe());}
            });


        } catch (Exception e) {
            Toast.makeText(this, "Unable to Load User Info",
                    Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000) {
            if (resultCode == Activity.RESULT_OK) {
                Uri imageUri = data.getData();
                changeProfileImage.setImageURI(imageUri);
                FirebaseFirestore
                        .getInstance()
                        .collection("users")
                        .document(userPage.getUid())
                        .get()
                        .addOnCompleteListener((OnCompleteListener<DocumentSnapshot>) task -> {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                DocumentReference documentReference = document.getReference();
                                documentReference.update("photoUri", imageUri.toString());


                            } else {
                                System.out.println("Could Not Add Profile Photo");
                            }
                        });
            }
        }
    }

    public void saveInfo (View view) {
        if (!pageUid.equals(FirebaseAuth.getInstance().getUid())) {
            return;
        }
        String major = String.valueOf(((EditText) findViewById(R.id.profileMajor)).getText());
        String phone = String.valueOf(((EditText) findViewById(R.id.profilePhone)).getText());
        String aboutMe = String.valueOf(((EditText) findViewById(R.id.profileAboutMe)).getText());
        FirebaseFirestore
                .getInstance()
                .collection("users")
                .document(userPage.getUid())
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
        Intent intent = new Intent(getApplicationContext(), HomepageActivity.class);
        startActivity(intent);
    }

    public void signOutUser(View view) {
        mAuth.signOut();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }



}
