package com.example.homeshare.Model;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;


public class Invitation implements Comparable<Invitation> {

    public Invitation() {

    }

    @ServerTimestamp
    private Date deadline;

    private String posterUid;

    private String imageURI;


    // Housing Info
    private String address;
    private Double rent;
    private String utilities;
    private Double numBeds;
    private String otherInfo;
    private Double milesFromCampus;


    // Biography
    private String dailySchedule;
    private String academicFocus;
    private String personality;
    private String otherDetails;

    private boolean available;


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

    public Double getRent() {
        return rent;
    }

    public void setRent(Double rent) {
        this.rent = rent;
    }

    public String getUtilities() {
        return utilities;
    }

    public void setUtilities(String utilities) {
        this.utilities = utilities;
    }

    public Double getNumBeds() {
        return numBeds;
    }

    public void setNumBeds(Double numBeds) {
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


    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public int compareTo(Invitation invitation) {
        return deadline.compareTo(invitation.deadline);
    }

    public Double getMilesFromCampus() {
        return milesFromCampus;
    }

    public void setMilesFromCampus(Double milesFromCampus) {
        this.milesFromCampus = milesFromCampus;
    }

    public String getImageURI() {
        return imageURI;
    }

    public void setImageURI(String imageURI) {
        this.imageURI = imageURI;
    }
}