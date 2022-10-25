package com.example.homeshare;

import android.os.Bundle;

import java.util.Date;
import java.sql.Time;
import java.sql.Timestamp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.homeshare.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class CreateInvitationActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
    }


    public void addInvitation(String address, String rent, String utilities, String numBeds,
                              String otherInfo, String dailySchedule, String academicFocus,
                              String personality, String otherDetails, String deadline) throws Exception {

        Map<String,Object> docData = new HashMap<>();
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
            Date parsedDate = dateFormat.parse(deadline);
            java.sql.Timestamp result = new Timestamp(parsedDate.getTime());
            docData.put("deadline", result);
        } catch (Exception e) {
            System.out.println("Invalid Timestamp/Deadline");
        }


        docData.put("address",address);
        docData.put("rent",rent);
        docData.put("utilities",utilities);
        docData.put("numBeds",numBeds);
        docData.put("otherInfo",otherInfo);
        docData.put("dailySchedule",dailySchedule);
        docData.put("academicFocus",academicFocus);
        docData.put("personality",personality);
        docData.put("otherDetails",otherDetails);
        docData.put("deadline",deadline);
        docData.put("posterUid", FirebaseAuth.getInstance().getCurrentUser().getUid());

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("invitations").add(docData);
    }
}
