package com.example.homeshare;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.homeshare.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.RecyclerView;

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


public class RegisterActivity extends AppCompatActivity {
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

//        binding = ActivityMainBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());
        setContentView(R.layout.activity_register_profile);



//        BottomNavigationView navView = findViewById(R.id.nav_view);
        //Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
//        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
//                .build();
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
//        NavigationUI.setupWithNavController(binding.navView, navController);

    }

    public void signUp(View text) {
//      TextView tv = (TextView) findViewById(R.id.text_home);
//      tv.setText("Hi " +((EditText)findViewById(R.id.inputName)).getText().toString());
        signUp(String.valueOf(((EditText)findViewById(R.id.reg_name)).getText()),
                String.valueOf(((EditText)findViewById(R.id.reg_email)).getText()),
                String.valueOf(((EditText)findViewById(R.id.reg_password)).getText()));;
        //signOutUser();


    }

    public void signUp(String name, String email, String password) {
        try {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            try{
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

                                    Intent myIntent = new Intent(getApplicationContext(), HomepageActivity.class);
                                    startActivity(myIntent);
                                    /* IMPLEMENT THIS LATER*/
                                    //updateUI(user);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    //Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();



                                    /* IMPLEMENT THIS LATER*/
                                    //updateUI(null);
                                }
                            }
                            catch(Exception e) {
                                Toast.makeText(RegisterActivity.this, "Sign Up Failed. Make sure email is valid and password length > 5",
                                        Toast.LENGTH_LONG).show();
                                System.out.println(e.toString());
                            }
                        }
                    });
        } catch (Exception e) {
            Toast.makeText(RegisterActivity.this, "Sign Up Failed. Make sure email is valid and password length > 5",
                    Toast.LENGTH_LONG).show();
            System.out.println(e.toString());
        }



    }

    public void goToLoginPage(View view) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

}