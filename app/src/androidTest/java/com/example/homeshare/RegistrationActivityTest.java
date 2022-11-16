package com.example.homeshare;



import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.*;

import com.example.homeshare.FeedActivities.InvitationFeedActivity;
import com.example.homeshare.NonFeedActivites.CreateInvitationActivity;
import com.example.homeshare.NonFeedActivites.MainActivity;
import com.example.homeshare.NonFeedActivites.RegisterActivity;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.google.android.material.internal.ContextUtils.getActivity;


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
    public void signUpPasswordTooShortTest(){
        onView(withId(R.id.reg_name))
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
    public void signUpInvalidEmailTest(){
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
    public void signUpEmptyDataTest(){
        onView(withId(R.id.btn_register))
                .perform(click());

        //check if correct toast message was displayed
        isToastMessageDisplayed("Sign Up Failed. Make sure email is valid and password length > 5");
    }

    @Test
    public void signUpAccountSuccess(){
        onView(withId(R.id.reg_name))
                .perform(typeText("Test User 1"), closeSoftKeyboard());

        onView(withId(R.id.reg_email))
                .perform(typeText("testUser4756475@usc.edu"), closeSoftKeyboard());
        onView(withId(R.id.reg_password))
                .perform(typeText("123456"), closeSoftKeyboard());
        onView(withId(R.id.btn_register))
                .perform(click());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //Will only be able to find the home button if login was successful
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