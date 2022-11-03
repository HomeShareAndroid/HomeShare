package com.example.homeshare;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homeshare.Model.Invitation;
import com.example.homeshare.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firestore.admin.v1.Index;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class InvitationFeedActivity extends AppCompatActivity {
    User user;
    private FirebaseAuth mAuth;
    private RecyclerView recyclerView;
    InvitationAdapter mostRecent;
    InvitationAdapter leastRecent;
    public static HashMap<Invitation, DocumentReference> invToRef = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invitationfeed2);

        RecyclerView.LayoutManager manager = new GridLayoutManager(getApplicationContext(),1);
        FirebaseUser fbUser = FirebaseAuth.getInstance().getCurrentUser();
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
                System.out.println("We got a Document!");
                usr[0] = documentSnapshot.toObject(User.class);
                user = usr[0];
                recyclerView = findViewById(R.id.recycler_view);
                Date date = new Date();
                List<Invitation> invites = new ArrayList<>();
                List<Invitation> invites2 = new ArrayList<>();
                List<Invitation> mostRentInvites = new ArrayList<>();
                List<Invitation> leastRentInvites = new ArrayList<>();
                List<Invitation> mostBedInvites = new ArrayList<>();
                List<Invitation> leastBedInvites = new ArrayList<>();
                List<Invitation> closestDistanceInvites = new ArrayList<>();
                List<Invitation> furthestDistanceInvites = new ArrayList<>();
                FirebaseFirestore.getInstance().collection("invitations")
                        .whereGreaterThan("deadline", new Timestamp(date))
                        .get()
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    if (!document.get("posterUid").equals(user.getUid())
                                            && document.contains("available")
                                            && (boolean) document.get("available")) {
                                        Invitation i = document.toObject(Invitation.class);
                                        System.out.println("Invitation = " + i.getAddress());
                                        invites.add(i);
                                        invites2.add(i);
                                        mostRentInvites.add(i);
                                        leastRentInvites.add(i);
                                        mostBedInvites.add(i);
                                        leastBedInvites.add(i);
                                        closestDistanceInvites.add(i);
                                        furthestDistanceInvites.add(i);
                                        invToRef.put(i, document.getReference());
                                    }
                                }
                                Collections.sort(invites);
                                mostRecent = new InvitationAdapter(invites);
                                recyclerView.setLayoutManager(manager);
                                //sorted = new InvitationAdapter(invites);
                                recyclerView.setAdapter(mostRecent);

                                Collections.sort(invites2, Collections.reverseOrder());
                                leastRecent = new InvitationAdapter(invites2);

                                System.out.println("Got Feed For User: " + user.getUid());
                                System.out.println("Feed is of Length: " + invites.size());
                            } else {
                                System.out.println("Getting Feed Not Successful");
                            }
                        });



            });

        } catch (Exception e) {
            System.out.println("User Not Found from Firestore Query");

        }
        if (usr[0] == null) {
            System.out.println("We couldn't get the User");
        }

        Switch sort = findViewById(R.id.Sort);
        sort.setOnCheckedChangeListener((compoundButton, b) -> {
            // sort by deadline
            if (b) {
                recyclerView.setAdapter(leastRecent);
            } else {
                recyclerView.setAdapter(mostRecent);
            }
        });
    }

    public void signOut(View view) {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void goToProfilePage(View view) {
        Intent intent = new Intent(getApplicationContext(), ProfilePageActivity.class);
        intent.putExtra("Uid", mAuth.getUid());
        startActivity(intent);
    }


}
