package com.example.homeshare;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homeshare.Model.InvitationResponse;
import com.example.homeshare.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class ResponseFeedActivity extends AppCompatActivity {
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
        setContentView(R.layout.activity_responsefeed);
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
                RecyclerView recyclerView = findViewById(R.id.response_recycler_view);
                Date date = new Date();

                List<InvitationResponse> responses = new ArrayList<>();
                FirebaseFirestore.getInstance().collection("invitationresponses")
                        .whereEqualTo("posterRef", documentReference)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        InvitationResponse r = document.toObject(InvitationResponse.class);
                                        System.out.println("Getting response with poster uid: " + r.getPosterRef());
                                        if (r.isResponse() && (!document.contains("accepted")) ) {
                                            responses.add(r);
                                            responseToRef.put(r, document.getReference());
                                        }

                                    }
                                    recyclerView.setLayoutManager(manager);
                                    recyclerView.setAdapter(new ResponseAdapter(getApplicationContext(), responses));

                                } else {
                                    System.out.println("Getting Feed Not Successful");
                                }
                            }
                        });
                System.out.println("Got Feed For User: " + user.getUid());
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

    public void gotoRoommateFeed(View view) {
        Intent intent = new Intent(getApplicationContext(), RoommateFeedActivity.class);
        startActivity(intent);
    }

    public void signOutUser(View view) {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }


}
