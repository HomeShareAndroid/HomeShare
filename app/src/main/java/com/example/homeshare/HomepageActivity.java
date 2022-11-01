package com.example.homeshare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homeshare.Model.User;
import com.example.homeshare.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class HomepageActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    private User user;


    private ActivityMainBinding binding;

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mAuth =   FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        BottomNavigationView navView = findViewById(R.id.nav_view);
        //Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);




    }
//
//    public void gotoProfilePage(View view) {
//        if (mAuth.getCurrentUser() == null) {
//            Toast.makeText(MainActivity.this, "Must Be Logged In to Visit Profile",
//                    Toast.LENGTH_SHORT).show();
//            return;
//        }
//        Intent intent = new Intent(getApplicationContext(), ProfilePageActivity.class);
//        intent.putExtra("Uid", mAuth.getUid());
//        startActivity(intent);
//    }
//
//
//    public void signUp(View text) {
////        TextView tv = (TextView) findViewById(R.id.text_home);
////        tv.setText("Hi " +((EditText)findViewById(R.id.inputName)).getText().toString());
//        signUp(String.valueOf(((EditText)findViewById(R.id.reg_name)).getText()),
//                String.valueOf(((EditText)findViewById(R.id.reg_email)).getText()),
//                String.valueOf(((EditText)findViewById(R.id.reg_password)).getText()));;
//        //signOutUser();
//
//
//    }
//    public void signIn(View text) {
//        signIn(String.valueOf(((EditText)findViewById(R.id.login_email)).getText()),
//                String.valueOf(((EditText)findViewById(R.id.login_password)).getText()));
//    }
//
//
//
//    public void signOutUser(View view) {
//        mAuth.signOut();
//    }
//
//    public void signUp(String name, String email, String password) {
//        try {
//            mAuth.createUserWithEmailAndPassword(email, password)
//                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                        @Override
//                        public void onComplete(@NonNull Task<AuthResult> task) {
//                            System.out.println(task.getResult().toString());
//                            if (task.isSuccessful()) {
//                                // Sign in success, update UI with the signed-in user's information
//                                FirebaseUser user = mAuth.getCurrentUser();
//                                user.updateProfile(new UserProfileChangeRequest
//                                        .Builder().setDisplayName(name).build());
//                                Map<String, Object> docData = new HashMap<>();
//                                docData.put("email", email);
//                                docData.put("name", name);
//                                docData.put("Uid", user.getUid());
//                                db.collection("users").document(user.getUid()).set(docData);
//
//
//                                /* IMPLEMENT THIS LATER*/
//                                //updateUI(user);
//                            } else {
//                                // If sign in fails, display a message to the user.
//                                //Log.w(TAG, "createUserWithEmail:failure", task.getException());
//                                Toast.makeText(MainActivity.this, "Authentication failed.",
//                                        Toast.LENGTH_SHORT).show();
//
//
//
//                                /* IMPLEMENT THIS LATER*/
//                                //updateUI(null);
//                            }
//                        }
//                    });
//        } catch (Exception e) {
//            Toast.makeText(MainActivity.this, "Sign Up Failed. Make sure email is valid and password length > 5",
//                    Toast.LENGTH_LONG).show();
//            System.out.println(e.toString());
//        }
//
//
//
//    }
//    public void addInvitation(View view)  {
//        Map<String,Object> docData = new HashMap<>();
//        docData.put("deadline", getTimestamp((DatePicker) findViewById(R.id.date_picker)));
//        docData.put("address",String.valueOf(((EditText)findViewById(R.id.address)).getText()));
//        docData.put("rent",String.valueOf(((EditText)findViewById(R.id.rent)).getText()));
//        docData.put("utilities",String.valueOf(((EditText)findViewById(R.id.utilities)).getText()));
//        docData.put("numBeds", ((EditText) findViewById(R.id.numBeds)).getText().toString());
//        docData.put("otherInfo",String.valueOf(((EditText)findViewById(R.id.otherInfo)).getText()));
//        docData.put("dailySchedule",String.valueOf(((EditText)findViewById(R.id.dailySchedule)).getText()));
//        docData.put("academicFocus",String.valueOf(((EditText)findViewById(R.id.academicFocus)).getText()));
//        docData.put("personality",String.valueOf(((EditText)findViewById(R.id.personality)).getText()));
//        docData.put("otherDetails",String.valueOf(((EditText)findViewById(R.id.otherDetails)).getText()));
//        docData.put("available" , true);
//
//        FirebaseUser  fbUser = mAuth.getCurrentUser();
//        if (fbUser != null) {
//            docData.put("posterUid", fbUser.getUid());
//            FirebaseFirestore db = FirebaseFirestore.getInstance();
//            db.collection("invitations").add(docData);
//        } else {
//            // NOT LOGGED IN!
//            Toast.makeText(MainActivity.this, "You Must Be Logged In to Create An Invitation",
//                    Toast.LENGTH_LONG).show();
//            System.out.println("NOT LOGGED IN");
//        }
//    }
//
//    public Timestamp getTimestamp(DatePicker dp) {
//        int year = dp.getYear();
//        int month = dp.getMonth();
//        int day = dp.getDayOfMonth();
//
//        Calendar calendar = Calendar.getInstance();
//        calendar.set(Calendar.YEAR, year);
//        calendar.set(Calendar.MONTH, month);
//        calendar.set(Calendar.DAY_OF_MONTH, day);
//        Date date = new Date();
//        date.setTime(calendar.getTimeInMillis());
//        return new Timestamp(date);
//
//    }
//
//    public void signIn(String email, String password) {
//        if (email == null || email.equals("") || password == null || password.equals("")) {
//            Toast.makeText(MainActivity.this, "Email / Password Fields Cannot Be Empty",
//                    Toast.LENGTH_LONG).show();
//            return;
//        }
//        try {
//            mAuth.signInWithEmailAndPassword(email, password)
//                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                        @Override
//                        public void onComplete(@NonNull Task<AuthResult> task) {
//                            if (!task.isSuccessful()) {
//                                // If sign in fails, display a message to the user.
//                                //Log.w(TAG, "signInWithEmail:failure", task.getException());
//                                Toast.makeText(MainActivity.this, "Authentication failed.",
//                                        Toast.LENGTH_SHORT).show();
//                                System.out.println(task.getException().toString());
//
//                                /* IMPLEMENT THIS LATER*/
//                                //updateUI(null);
//                            } else {
//                                //Intent myIntent = new Intent(getApplicationContext(), InvitationFeedActivity.class);
//                                //startActivity(myIntent);
//                            }
//                        }
//                    });
//
//        } catch (Exception e) {
//            Toast.makeText(MainActivity.this, "Authentication failed.",
//                    Toast.LENGTH_SHORT).show();
//            System.out.println(e.toString());
//
//        }
//    }
//
//
//    public void gotoResponseFeed(View view) {
//        Intent intent = new Intent(getApplicationContext(), ResponseFeedActivity.class);
//        startActivity(intent);
//    }
//
//    public void gotoInvitationFeed(View view) {
//        Intent intent = new Intent(getApplicationContext(), InvitationFeedActivity.class);
//        startActivity(intent);
//    }
//
//    public void gotoRoommateFeed(View view) {
//        Intent intent = new Intent(getApplicationContext(), RoommateFeedActivity.class);
//        startActivity(intent);
//    }

    public void goToProfilePage(View view) {
        Intent intent = new Intent(getApplicationContext(), ProfilePageActivity.class);
        startActivity(intent);
    }
}