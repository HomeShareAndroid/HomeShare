package com.example.homeshare.NonFeedActivites;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.homeshare.FeedActivities.InvitationFeedActivity;
import com.example.homeshare.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class RegisterActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mAuth =   FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register_profile);



    }

    public void signUp(View text) {

        signUp(String.valueOf(((EditText)findViewById(R.id.reg_name)).getText()),
                String.valueOf(((EditText)findViewById(R.id.reg_email)).getText()),
                String.valueOf(((EditText)findViewById(R.id.reg_password)).getText()));;


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

                                    Intent myIntent = new Intent(getApplicationContext(), InvitationFeedActivity.class);
                                    startActivity(myIntent);

                                } else {

                                    Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();

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