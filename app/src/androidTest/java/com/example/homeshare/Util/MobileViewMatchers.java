package com.example.homeshare.Util;

import androidx.test.espresso.Root;

import org.hamcrest.Matcher;

public class MobileViewMatchers {


    public static Matcher<Root> isToast() {
        return new ToastMatcher();
    }
}
