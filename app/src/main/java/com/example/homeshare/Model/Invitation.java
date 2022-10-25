package com.example.homeshare.Model;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.ServerTimestamp;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Invitation {

    public Invitation() {

    }

    @ServerTimestamp
    private Date deadline;

    private String posterUid;


    // Housing Info
    private String address;
    private String rent;
    private String utilities;
    private String numBeds;
    private String otherInfo;


    // Biography
    private String dailySchedule;
    private String academicFocus;
    private String personality;
    private String otherDetails;


    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public String getPosterUid() {
        return posterUid;
    }

    public void setPosterUid(String posterUid) {
        this.posterUid = posterUid;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRent() {
        return rent;
    }

    public void setRent(String rent) {
        this.rent = rent;
    }

    public String getUtilities() {
        return utilities;
    }

    public void setUtilities(String utilities) {
        this.utilities = utilities;
    }

    public String getNumBeds() {
        return numBeds;
    }

    public void setNumBeds(String numBeds) {
        this.numBeds = numBeds;
    }

    public String getOtherInfo() {
        return otherInfo;
    }

    public void setOtherInfo(String otherInfo) {
        this.otherInfo = otherInfo;
    }

    public String getDailySchedule() {
        return dailySchedule;
    }

    public void setDailySchedule(String dailySchedule) {
        this.dailySchedule = dailySchedule;
    }

    public String getAcademicFocus() {
        return academicFocus;
    }

    public void setAcademicFocus(String academicFocus) {
        this.academicFocus = academicFocus;
    }

    public String getPersonality() {
        return personality;
    }

    public void setPersonality(String personality) {
        this.personality = personality;
    }

    public String getOtherDetails() {
        return otherDetails;
    }

    public void setOtherDetails(String otherDetails) {
        this.otherDetails = otherDetails;
    }

    public static void deleteExpiredInvitations() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference invites = db.collection("invitations");

        invites.whereLessThan("deadline", FieldValue.serverTimestamp())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                document.getReference().delete();

                            }
                        }
                    }
                });

    }
}
