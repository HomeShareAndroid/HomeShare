package com.example.homeshare.Model;

import java.sql.Timestamp;


public class Invitation {

    public Invitation() {

    }

    private Timestamp deadline;
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


    public Timestamp getDeadline() {
        return deadline;
    }

    public void setDeadline(Timestamp deadline) {
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
}
