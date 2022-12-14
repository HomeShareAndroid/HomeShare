package com.example.homeshare.FeedActivities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homeshare.Adapters.RoommateAdapter;
import com.example.homeshare.NonFeedActivites.CreateInvitationActivity;
import com.example.homeshare.NonFeedActivites.MainActivity;
import com.example.homeshare.Model.InvitationResponse;
import com.example.homeshare.Model.User;
import com.example.homeshare.NonFeedActivites.ProfilePageActivity;
import com.example.homeshare.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RoommateFeedActivity extends AppCompatActivity {
    User user;
    FirebaseUser fbUser;
    private RecyclerView recyclerView;
    public static HashMap<InvitationResponse, DocumentReference> responseToRef = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        fbUser = FirebaseAuth.getInstance().getCurrentUser();
        getSupportActionBar().hide();
        RecyclerView.LayoutManager manager = new GridLayoutManager(getApplicationContext(),1);
        setContentView(R.layout.activity_roommatefeed);


        if (fbUser == null) {
            System.out.println("No User Logged in");
            Toast.makeText(this, "Not Logged In.",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        final User[] usr = new User[1];
        try {
            DocumentReference documentReference = FirebaseFirestore
                    .getInstance()
                    .collection("users")
                    .document(fbUser.getUid());
            System.out.println(fbUser.getUid());

            documentReference.get().addOnSuccessListener(documentSnapshot -> {
                usr[0] = documentSnapshot.toObject(User.class);
                user = usr[0];
                RecyclerView recyclerView = findViewById(R.id.roommate_recycler_view);

                List<InvitationResponse> responses = new ArrayList<>();
                FirebaseFirestore.getInstance().collection("invitationresponses")
                        .whereEqualTo("posterRef", documentReference)
                        .get()
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    InvitationResponse r = document.toObject(InvitationResponse.class);
                                    if (document.contains("accepted") && (boolean)document.get("accepted")) {
                                        responses.add(r);
                                        responseToRef.put(r, document.getReference());
                                    }
                                }
                                FirebaseFirestore.getInstance().collection("invitationresponses")
                                        .whereEqualTo("responderRef", documentReference)
                                        .get()
                                        .addOnCompleteListener(task1 -> {
                                            if (task1.isSuccessful()) {
                                                for (QueryDocumentSnapshot document1 : task1.getResult()) {
                                                    InvitationResponse r1 = document1.toObject(InvitationResponse.class);
                                                    if (document1.contains("accepted") && (boolean) document1.get("accepted")) {
                                                        responses.add(r1);
                                                        responseToRef.put(r1, document1.getReference());
                                                    }
                                                }
                                                recyclerView.setLayoutManager(manager);
                                                recyclerView.setAdapter(new RoommateAdapter(getApplicationContext(), responses));
                                            } else {
                                                System.out.println("Getting Feed Not Successful");
                                            }
                                        });
                            } else {
                                System.out.println("Getting Feed Not Successful");
                            }
                        });
                System.out.println("Got Roommate Feed For User: " + user.getUid());
                System.out.println("Feed is of Length: " + responses.size() );


            });

        } catch (Exception e) {
            System.out.println("User Not Found from Firestore Query");

        }
        if (usr[0] == null) {
            System.out.println("We couldn't get the User");
        }
    }

    public void signOut(View view) {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void goToProfilePage(View view) {
        Intent intent = new Intent(getApplicationContext(), ProfilePageActivity.class);
        intent.putExtra("Uid", fbUser.getUid());
        startActivity(intent);
    }

    public void goToHomePage(View view) {
        Intent intent = new Intent(getApplicationContext(), InvitationFeedActivity.class);
        startActivity(intent);
    }

    public void gotoCreateAPost(View view) {
        Intent intent = new Intent(getApplicationContext(), CreateInvitationActivity.class);
        startActivity(intent);
    }

    public void gotoResponseFeed(View view) {
        Intent intent = new Intent(getApplicationContext(), ResponseFeedActivity.class);
        startActivity(intent);
    }

    public void signOutUser(View view) {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }


}
