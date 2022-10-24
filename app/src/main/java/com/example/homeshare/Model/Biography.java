package com.example.homeshare.Model;

public class Biography {
    private String dailySchedule;
    private String academicFocus;
    private String personality;
    private String otherDetails;

    public Biography(String d, String a, String p, String o) {
        dailySchedule = d;
        academicFocus = a;
        personality = p;
        otherDetails = o;
    }


    public String getDailySchedule() {
        return dailySchedule;
    }

    public String getAcademicFocus() {
        return academicFocus;
    }

    public String getPersonality() {
        return personality;
    }

    public String getOtherDetails() {
        return otherDetails;
    }
}
