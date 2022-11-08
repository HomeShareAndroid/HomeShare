package com.example.homeshare.FeedActivities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homeshare.NonFeedActivites.CreateInvitationActivity;
import com.example.homeshare.Adapters.InvitationAdapter;
import com.example.homeshare.NonFeedActivites.MainActivity;
import com.example.homeshare.Model.Invitation;
import com.example.homeshare.Model.User;
import com.example.homeshare.NonFeedActivites.ProfilePageActivity;
import com.example.homeshare.R;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class InvitationFeedActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    User user;
    private FirebaseAuth mAuth;
    private RecyclerView recyclerView;
    InvitationAdapter adapt;

    Spinner sortSpinner;
    List<Invitation> invites = new ArrayList<>();
    boolean hasBeenChanged = false;
    public static HashMap<Invitation, DocumentReference> invToRef = new HashMap<>();
    RecyclerView.LayoutManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invitationfeed2);

         manager = new GridLayoutManager(getApplicationContext(),1);
        FirebaseUser fbUser = FirebaseAuth.getInstance().getCurrentUser();
        if (fbUser == null) {
            System.out.println("No User Logged in");
            Toast.makeText(this, "Not Logged In.",
                    Toast.LENGTH_SHORT).show();
            return;
        }


        sortSpinner = (Spinner) findViewById(R.id.sort_spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.
                createFromResource(this, R.array.sort_array,
                        android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sortSpinner.setAdapter(adapter);
        sortSpinner.setOnItemSelectedListener(this);

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
                FirebaseFirestore.getInstance().collection("invitations")
                        .whereGreaterThan("deadline", new Timestamp(date))
                        .get()
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                int[] iteration = new int[1];
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    if (!document.get("posterUid").equals(user.getUid())
                                            && document.contains("available")
                                            && (boolean) document.get("available")) {
                                        DocumentReference invitationRef = document.getReference();
                                        System.out.println("Searching for Invitation Reference: /" + invitationRef.getPath());

                                        FirebaseFirestore.getInstance().collection("invitationresponses")
                                                .whereEqualTo("invitationRef", invitationRef)
                                                .whereEqualTo("responderRef", documentReference)
                                                .get()
                                                .addOnCompleteListener(response_task -> {
                                                    Invitation i = document.toObject(Invitation.class);
                                                    if ((response_task.isSuccessful() && response_task.getResult().isEmpty())|| !response_task.isSuccessful()) {
                                                        System.out.println("Valid Invitation = " + i.getAddress());
                                                        invites.add(i);
                                                        invToRef.put(i, document.getReference());

                                                    } else {
                                                        System.out.println("I already responded to Invitation with Address: " + i.getAddress());
                                                    }
                                                    if (iteration[0] == (task.getResult().size())) {
                                                        Collections.sort(invites);
                                                        System.out.println("Number of Invites to Be Shown: " + invites.size());
                                                        adapt = new InvitationAdapter(invites);
                                                        recyclerView.setLayoutManager(manager);
                                                        //sorted = new InvitationAdapter(invites);
                                                        recyclerView.setAdapter(adapt);


                                                        System.out.println("Got Feed For User: " + user.getUid());
                                                        System.out.println("Feed is of Length: " + invites.size());
                                                    }
                                                    System.out.println("Iteration of Potential Feed Items: " + iteration[0]);
                                                    System.out.println("Total number of potential feed items: " + task.getResult().size());
                                                });

                                    }
                                    iteration[0] = iteration[0] + 1;
                                }

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

    public void gotoCreateAPost(View view) {
        Intent intent = new Intent(getApplicationContext(), CreateInvitationActivity.class);
        startActivity(intent);
    }

    public void gotoResponseFeed(View view) {
        Intent intent = new Intent(getApplicationContext(), ResponseFeedActivity.class);
        startActivity(intent);
    }

    public void gotoRoommateFeed(View view) {
        Intent intent = new Intent(getApplicationContext(), RoommateFeedActivity.class);
        startActivity(intent);
    }

    public void signOutUser(View view) {
        mAuth.signOut();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }


    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
        parent.getBackground().setColorFilter(getResources().getColor(R.color.white),
                PorterDuff.Mode.SRC_ATOP);
        if (hasBeenChanged) {
            manager = new GridLayoutManager(getApplicationContext(),1);
            System.out.println("we can change adapter now");

            switch (pos) {
                case 0 :{
                    Collections.sort(adapt.data);
                    System.out.println("Items in Feed: " + adapt.data.size());
                    System.out.println("Sorting by Most Recent Deadline: ");
                    for (Invitation i : adapt.data) {
                        System.out.println(i.getDeadline().toString());
                    }
                    adapt.notifyDataSetChanged();
                    recyclerView.invalidate();
                    manager.scrollToPosition(0);
                    recyclerView.smoothScrollToPosition(0);
                    break;
                }

                case 1 : {
                    Collections.sort(adapt.data, Collections.reverseOrder());
                    System.out.println("Items in Feed: " + adapt.data.size());
                    System.out.println("Sorting by Least Recent Deadline: ");
                    for (Invitation i : adapt.data) {
                        System.out.println(i.getDeadline().toString());
                    }
                    adapt.notifyDataSetChanged();
                    recyclerView.invalidate();
                    manager.scrollToPosition(0);
                    recyclerView.smoothScrollToPosition(0);
                    break;
                }
                case 2: {
                    adapt.data.sort((invitation, t1) -> {
                        if (invitation.getRent() < t1.getRent()) {
                            return 1;
                        } else if (invitation.getRent() > t1.getRent()) {
                            return -1;
                        }
                        return 0;
                    });
                    adapt.notifyDataSetChanged();
                    manager.scrollToPosition(0);
                    recyclerView.smoothScrollToPosition(0);
                    break;
                }
                case 3: {
                    adapt.data.sort((invitation, t1) -> {
                        if (invitation.getRent() > t1.getRent()) {
                            return 1;
                        } else if (invitation.getRent() < t1.getRent()) {
                            return -1;
                        }
                        return 0;
                    });

                    adapt.notifyDataSetChanged();
                    manager.scrollToPosition(0);
                    recyclerView.smoothScrollToPosition(0);
                    break;
                }

                case 4: {
                    adapt.data.sort((invitation, t1) -> {
                        if (invitation.getMilesFromCampus() < t1.getMilesFromCampus()) {
                            return 1;
                        } else if (invitation.getMilesFromCampus() > t1.getMilesFromCampus()) {
                            return -1;
                        }
                        return 0;
                    });

                    adapt.notifyDataSetChanged();
                    manager.scrollToPosition(0);
                    recyclerView.smoothScrollToPosition(0);
                    break;
                }

                case 5: {
                    adapt.data.sort((invitation, t1) -> {
                        if (invitation.getMilesFromCampus() > t1.getMilesFromCampus()) {
                            return 1;
                        } else if (invitation.getMilesFromCampus() < t1.getMilesFromCampus()) {
                            return -1;
                        }
                        return 0;
                    });

                    adapt.notifyDataSetChanged();
                    manager.scrollToPosition(0);
                    recyclerView.smoothScrollToPosition(0);
                    break;
                }

                case 6: {

                    adapt.data.sort((invitation, t1) -> {
                        if (invitation.getNumBeds() < t1.getNumBeds()) {
                            return 1;
                        } else if (invitation.getNumBeds() > t1.getNumBeds()) {
                            return -1;
                        }
                        return 0;
                    });

                    adapt.notifyDataSetChanged();
                    manager.scrollToPosition(0);
                    recyclerView.smoothScrollToPosition(0);
                    break;
                }


                case 7: {
                    adapt.data.sort((invitation, t1) -> {
                        if (invitation.getNumBeds() > t1.getNumBeds()) {
                            return 1;
                        } else if (invitation.getNumBeds() < t1.getNumBeds()) {
                            return -1;
                        }
                        return 0;
                    });

                    adapt.notifyDataSetChanged();
                    manager.scrollToPosition(0);
                    recyclerView.smoothScrollToPosition(0);
                    break;
                }

            }
        }
        hasBeenChanged = true;

    }
    public void onNothingSelected(AdapterView<?> parent) {
        // we're good. This just needs to be here for interface implementation
    }

}
