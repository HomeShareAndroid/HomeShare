package com.example.homeshare;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
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

public class CreateInvitationActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    private User user;




    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mAuth =   FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_create_invitation);


    }

    public void gotoProfilePage(View view) {
        if (mAuth.getCurrentUser() == null) {
            Toast.makeText(CreateInvitationActivity.this, "Must Be Logged In to Visit Profile",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(getApplicationContext(), ProfilePageActivity.class);
        intent.putExtra("Uid", mAuth.getUid());
        startActivity(intent);
    }


    public void addInvitation(View view)  {

        //http://dev.virtualearth.net/REST/V1/Routes/Walking?wp.0=11025%20NE%208th%20St%20Bellevue%20WA&wp.1=700%20Bellevue%20Way%20NE%20Bellevue WA&key=BingMapsKey
        Map<String,Object> docData = new HashMap<>();
        docData.put("deadline", getTimestamp((DatePicker) findViewById(R.id.date_picker)));
        docData.put("address",String.valueOf(((EditText)findViewById(R.id.address)).getText()));
        docData.put("utilities",String.valueOf(((EditText)findViewById(R.id.utilities)).getText()));

        docData.put("otherInfo",String.valueOf(((EditText)findViewById(R.id.otherInfo)).getText()));
        docData.put("dailySchedule",String.valueOf(((EditText)findViewById(R.id.dailySchedule)).getText()));
        docData.put("academicFocus",String.valueOf(((EditText)findViewById(R.id.academicFocus)).getText()));
        docData.put("personality",String.valueOf(((EditText)findViewById(R.id.personality)).getText()));
        docData.put("otherDetails",String.valueOf(((EditText)findViewById(R.id.otherDetails)).getText()));
        docData.put("milesFromCampus",String.valueOf(((EditText)findViewById(R.id.milesFromCampus)).getText()));
        docData.put("available" , true);

        try {
            docData.put("rent", Double.parseDouble(String.valueOf(((EditText) findViewById(R.id.rent)).getText())));
            docData.put("numBeds", Double.parseDouble((((EditText)findViewById(R.id.numBeds)).getText().toString())));
            docData.put("milesFromCampus",Double.parseDouble(String.valueOf(((EditText)findViewById(R.id.milesFromCampus)).getText())));
        } catch (Exception e) {
            Toast.makeText(CreateInvitationActivity.this, "Rent, number of beds, and miles from campus must be numbers",
                    Toast.LENGTH_LONG).show();
            System.out.println("Rent and number of beds must be integers");
            return;

        }

        FirebaseUser  fbUser = mAuth.getCurrentUser();
        docData.put("posterUid", fbUser.getUid());
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("invitations").add(docData);
        Intent intent = new Intent(getApplicationContext(), InvitationFeedActivity.class);
        intent.putExtra("InvitationToast", "Toast");
        startActivity(intent);

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


    public void gotoResponseFeed(View view) {
        Intent intent = new Intent(getApplicationContext(), ResponseFeedActivity.class);
        startActivity(intent);
    }

    public void gotoInvitationFeed(View view) {
        Intent intent = new Intent(getApplicationContext(), InvitationFeedActivity.class);
        startActivity(intent);
    }

    public void gotoRoommateFeed(View view) {
        Intent intent = new Intent(getApplicationContext(), RoommateFeedActivity.class);
        startActivity(intent);
    }
}
