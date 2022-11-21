package com.example.homeshare.NonFeedActivities;



import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import java.util.Random;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.not;

import com.example.homeshare.FeedActivities.InvitationFeedActivity;
import com.example.homeshare.NonFeedActivites.RegisterActivity;
import com.example.homeshare.R;
import com.example.homeshare.Util.MobileViewMatchers;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;


/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class RegistrationActivityTest {


    @Rule
    public ActivityScenarioRule<RegisterActivity> activityScenarioRule
            = new ActivityScenarioRule<>(RegisterActivity.class);

    @Before
    public void setUp(){
        Intents.init();
    }

    @After
    public void tearDown(){
        Intents.release();
    }


    @Test
    public void signUpPasswordTooShort(){
        onView(ViewMatchers.withId(R.id.reg_name))
                .perform(typeText("Test User 1"), closeSoftKeyboard());
        onView(withId(R.id.reg_email))
                .perform(typeText("testUser@usc.edu"), closeSoftKeyboard());
        onView(withId(R.id.reg_password))
                .perform(typeText("12"), closeSoftKeyboard());
        onView(withId(R.id.btn_register))
                .perform(click());

        //check if correct toast message was displayed
        isToastMessageDisplayed("Sign Up Failed. Make sure email is valid and password length > 5");
    }

    @Test
    public void signUpInvalidEmail(){
        onView(withId(R.id.reg_name))
                .perform(typeText("Test User 1"), closeSoftKeyboard());
        onView(withId(R.id.reg_email))
                .perform(typeText("testUserWithNoAtSymbol"), closeSoftKeyboard());
        onView(withId(R.id.reg_password))
                .perform(typeText("123456"), closeSoftKeyboard());
        onView(withId(R.id.btn_register))
                .perform(click());

        //check if correct toast message was displayed
        isToastMessageDisplayed("Sign Up Failed. Make sure email is valid and password length > 5");
    }

    @Test
    public void signUpEmptyData(){
        onView(withId(R.id.btn_register))
                .perform(click());

        //check if correct toast message was displayed
        isToastMessageDisplayed("Sign Up Failed. Make sure email is valid and password length > 5");
    }

    @Test
    public void signUpAccountSuccess(){
        onView(withId(R.id.reg_name))
                .perform(typeText("Test User 1"), closeSoftKeyboard());

        Random rand = new Random(); //instance of random class
        int upperbound = Integer.MAX_VALUE;
        //generate random values from 0-24
        int emailNum = rand.nextInt(upperbound);

        onView(withId(R.id.reg_email))
                .perform(typeText("testUser" + emailNum + "@usc.edu"), closeSoftKeyboard());
        onView(withId(R.id.reg_password))
                .perform(typeText("123456"), closeSoftKeyboard());
        onView(withId(R.id.btn_register))
                .perform(click());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        intended(hasComponent(InvitationFeedActivity.class.getName()));
//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

    }

    public void isToastMessageDisplayed(String text) {
        onView(withText(text)).inRoot(MobileViewMatchers.isToast()).check(matches(isDisplayed()));
    }
}