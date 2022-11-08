package com.example.homeshare.NonFeedActivites;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.homeshare.FeedActivities.InvitationFeedActivity;
import com.example.homeshare.Model.User;
import com.example.homeshare.NonFeedActivites.MainActivity;
import com.example.homeshare.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ProfilePageActivity extends AppCompatActivity {
    User userPage;
    private ImageButton changeProfileImage;
    String pageUid;
    private FirebaseAuth mAuth;
    FirebaseStorage storage =FirebaseStorage.getInstance();
    StorageReference storageReference = storage.getReference();
    Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
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
                storageReference.child("images/" + userPage.getUid()).getDownloadUrl().
                        addOnSuccessListener(uri -> {

                            System.out.println("Successfully got uri: " + uri);
                            if (uri!=null) Glide.with(getApplicationContext())
                                    .load(uri)
                                    .circleCrop()
                                    .into(changeProfileImage);
                            else {
                                storageReference.child("images/upload-photo-icon-illustration-vector-260nw-1633768528.webp").getDownloadUrl()
                                        .addOnSuccessListener(myuri -> Glide.with(getApplicationContext())
                                                .load(myuri)
                                                .circleCrop()
                                                .into(changeProfileImage));
                            }
                        })
                        .addOnFailureListener(e -> {
                            System.out.println("Failed and got URI: " + e);
                            storageReference.child("images/upload-photo-icon-illustration-vector-260nw-1633768528.webp").getDownloadUrl()
                                    .addOnSuccessListener(a-> Glide.with(getApplicationContext())
                                            .load(a)
                                            .circleCrop()
                                            .into(changeProfileImage));
                        });
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
                imageUri = data.getData();
                Glide.with(getApplicationContext())
                        .load(imageUri)
                        .circleCrop()
                        .into(changeProfileImage);
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

        StorageReference ref = storageReference.child("images/"+ userPage.getUid());

        if (imageUri != null) {
            ref.putFile(imageUri);
        }
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
        Intent intent = new Intent(getApplicationContext(), InvitationFeedActivity.class);
        startActivity(intent);
    }

    public void signOutUser(View view) {
        mAuth.signOut();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }



}
