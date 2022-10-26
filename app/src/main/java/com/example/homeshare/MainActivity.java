package com.example.homeshare;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.homeshare.databinding.ActivityMainBinding;
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

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private Calendar date;

    private ActivityMainBinding binding;
    EditText inputName;
    EditText inputPassword;
    EditText inputEmail;



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
        inputName = (EditText) findViewById(R.id.inputName);
        inputPassword = (EditText) findViewById(R.id.inputPassword);
        inputEmail = (EditText) findViewById(R.id.inputEmail);

    }



    public void signUp(View text) {
        TextView tv = (TextView) findViewById(R.id.text_home);
        tv.setText("Hi " +inputName.getText());
        signUp(String.valueOf(inputName.getText()),
                String.valueOf(inputEmail.getText()),
                String.valueOf(inputPassword.getText()));
        //signOutUser();


    }



    public void signOutUser() {
        FirebaseAuth.getInstance().signOut();
    }

    public void signUp(String name, String email, String password) {
        try {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            System.out.println(task.getResult().toString());
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                FirebaseUser user = mAuth.getCurrentUser();
                                user.updateProfile(new UserProfileChangeRequest
                                        .Builder().setDisplayName(name).build());
                                Map<String, Object> docData = new HashMap<>();
                                docData.put("email", email);
                                docData.put("name", name);
                                docData.put("Uid", user.getUid());
                                db.collection("users").document(user.getUid()).set(docData);


                                /* IMPLEMENT THIS LATER*/
                                //updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user.
                                //Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                Toast.makeText(MainActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();



                                /* IMPLEMENT THIS LATER*/
                                //updateUI(null);
                            }
                        }
                    });
        } catch (Exception e) {
            System.out.println(e.toString());
        }



    }
    public void addInvitation(View view)  {
        Map<String,Object> docData = new HashMap<>();
        docData.put("deadline", getTimestamp((DatePicker) findViewById(R.id.date_picker)));
        docData.put("address",String.valueOf(((EditText)findViewById(R.id.address)).getText()));
        docData.put("rent",String.valueOf(((EditText)findViewById(R.id.rent)).getText()));
        docData.put("utilities",String.valueOf(((EditText)findViewById(R.id.utilities)).getText()));
        docData.put("numBeds",String.valueOf(((EditText)findViewById(R.id.numBeds)).getText()));
        docData.put("otherInfo",String.valueOf(((EditText)findViewById(R.id.otherInfo)).getText()));
        docData.put("dailySchedule",String.valueOf(((EditText)findViewById(R.id.dailySchedule)).getText()));
        docData.put("academicFocus",String.valueOf(((EditText)findViewById(R.id.academicFocus)).getText()));
        docData.put("personality",String.valueOf(((EditText)findViewById(R.id.personality)).getText()));
        docData.put("otherDetails",String.valueOf(((EditText)findViewById(R.id.otherDetails)).getText()));

        FirebaseUser  fbUser = mAuth.getCurrentUser();
        if (fbUser != null) {
            docData.put("posterUid", fbUser.getUid());
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("invitations").add(docData);
        } else {
            // NOT LOGGED IN!
            System.out.println("NOT LOGGED IN");
        }
    }

    public Timestamp getTimestamp(DatePicker dp) {
        int year = dp.getYear();
        int month = dp.getMonth();
        int day = dp.getDayOfMonth();

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        Date date = new Date();
        date.setTime(calendar.getTimeInMillis());
        return new Timestamp(date);

    }

    public void signIn(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            //Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();


                            /* IMPLEMENT THIS LATER*/
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            //Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                            /* IMPLEMENT THIS LATER*/
                            //updateUI(null);
                        }
                    }
                });
    }

}