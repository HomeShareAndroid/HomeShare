package com.example.homeshare;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    private ActivityMainBinding binding;
    EditText inputName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mAuth =   FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
        inputName = (EditText) findViewById(R.id.inputName);
    }



    public void printName(View texv) {
        TextView tv = (TextView) findViewById(R.id.text_home);
        tv.setText("Hi " + inputName.getText());
        signUp(String.valueOf(inputName.getText()), "email@gmail.com", "samplePass");
        signOutUser();

    }


    public void signOutUser() {
        FirebaseAuth.getInstance().signOut();
    }

    public void signUp(String name, String email, String password) {
       /*
        Map<String, Object> docData = new HashMap<>();
        docData.put("email", email);
        docData.put("name", name);
        docData.put("Uid", "unused");
        db.collection("users").add(docData);

        */


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



    }

}