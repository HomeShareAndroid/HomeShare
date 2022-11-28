package com.example.homeshare.NonFeedActivites;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.homeshare.FeedActivities.InvitationFeedActivity;
import com.example.homeshare.FeedActivities.ResponseFeedActivity;
import com.example.homeshare.FeedActivities.RoommateFeedActivity;
import com.example.homeshare.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import io.grpc.Context;

public class CreateInvitationActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    Uri housingPicUri;


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

    public void goToProfilePage(View view) {
        Intent intent = new Intent(getApplicationContext(), ProfilePageActivity.class);
        intent.putExtra("Uid", mAuth.getUid());
        startActivity(intent);
    }


    public String getMilesFromCampus(String sourceAddress){
        System.out.println("TESTING123");
        System.out.println(sourceAddress);
        String translatedAddress = sourceAddress.replace(" ","+");
        String url ="https://maps.googleapis.com/maps/api/distancematrix/json?destinations=3501+Trousdale+Pkwy,+Los Angeles,+CA+90089&origins="+translatedAddress+"&units=imperial&key=AIzaSyB2ZRiF2Q21nkCor-aakdFJl_6XRpcLV1o";
        StringBuffer response = null;
        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new
                    StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        try {
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            // optional default is GET
            con.setRequestMethod("GET");
            //add request header
            con.setRequestProperty("User-Agent", "Mozilla/5.0");
            int responseCode = con.getResponseCode();
            System.out.println("\nSending 'GET' request to URL : " + url);
            System.out.println("Response Code : " + responseCode);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
        }catch (Exception e){
            System.out.println("ERROR ++++");
            System.out.println(e.getMessage());
        }
        JSONObject jsonObject = null;
        String miles = null;
        try {
            jsonObject = new JSONObject(response.toString());
            System.out.println(jsonObject.getJSONArray("rows").getJSONObject(0).getJSONArray("elements").getJSONObject(0).getJSONObject("distance").getString("text"));
            miles= jsonObject.getJSONArray("rows").getJSONObject(0).getJSONArray("elements").getJSONObject(0).getJSONObject("distance").getString("text");
        }catch (Exception e){
            e.printStackTrace();
        }
        return miles;

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
        docData.put("available" , true);
        String parseInt = null;
        double milesParsed = 0;
        try {
            parseInt = getMilesFromCampus(String.valueOf(((EditText)findViewById(R.id.address)).getText()));
            parseInt = parseInt.replace(",", ".");
             milesParsed = Double.parseDouble(parseInt.substring(0,parseInt.length()-3));

            try {
                docData.put("rent", Double.parseDouble(String.valueOf(((EditText) findViewById(R.id.rent)).getText())));
                docData.put("numBeds", Double.parseDouble((((EditText)findViewById(R.id.numBeds)).getText().toString())));
                docData.put("milesFromCampus",milesParsed);
            } catch (Exception e) {
                Toast.makeText(CreateInvitationActivity.this, "Rent, number of beds, and miles from campus must be numbers",
                        Toast.LENGTH_LONG).show();
                System.out.println("Rent and number of beds must be integers");
                return;

            }

            FirebaseUser  fbUser = mAuth.getCurrentUser();
            docData.put("posterUid", fbUser.getUid());
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("invitations").add(docData).addOnSuccessListener(documentReference -> {
                String invitePath = documentReference.getPath();
                System.out.println("We are adding an invitation image with the path: " + "images/" + invitePath);
                StorageReference ref = FirebaseStorage.getInstance().getReference().child("images/" + invitePath);
                if (housingPicUri != null) {
                    ref.putFile(housingPicUri);
                    documentReference.update("imageURI","images/" + invitePath);
                }

            });
            Intent intent = new Intent(getApplicationContext(), InvitationFeedActivity.class);
            intent.putExtra("InvitationToast", "Toast");
            startActivity(intent);
        }catch (Exception e){
            Toast.makeText(CreateInvitationActivity.this, "Invalid Address",
                    Toast.LENGTH_LONG).show();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000) {
            if (resultCode == Activity.RESULT_OK) {
                housingPicUri = data.getData();
                ((ImageButton) findViewById(R.id.housing_photo)).setImageURI(housingPicUri);
            }
        }
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

    public void gotoCreateAPost(View view) {
        Intent intent = new Intent(getApplicationContext(), CreateInvitationActivity.class);
        startActivity(intent);
    }


    public void addInvitePhoto(View view) {
        Intent openGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(openGallery, 1000);

    }
}

