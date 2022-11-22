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

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.not;

import com.example.homeshare.FeedActivities.InvitationFeedActivity;
import com.example.homeshare.NonFeedActivites.MainActivity;
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
public class MainActivityTest {


    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule
            = new ActivityScenarioRule<>(MainActivity.class);

    @Before
    public void setUp(){
        Intents.init();
    }

    @After
    public void tearDown(){
        Intents.release();
    }



    @Test
    public void loginInvalidPassword(){
        onView(ViewMatchers.withId(R.id.login_email))
                .perform(typeText("testuser1@usc.edu"), closeSoftKeyboard());
        onView(withId(R.id.login_password))
                .perform(typeText("654321"), closeSoftKeyboard());
        onView(withId(R.id.btn_login))
                .perform(click());
        //check if correct toast message was displayed
        isToastMessageDisplayed("Authentication failed.");
    }

    @Test
    public void loginEmptyData() throws InterruptedException {
        Thread.sleep(1000);
        onView(withId(R.id.btn_login))
                .perform(click());
        Thread.sleep(1000);
        //check if correct toast message was displayed
        isToastMessageDisplayed("Email / Password Fields Cannot Be Empty");
        Thread.sleep(1000);
    }

    @Test
    public void loginSuccess() throws InterruptedException {
        onView(withId(R.id.login_email))
                .perform(typeText("testuser1@usc.edu"), closeSoftKeyboard());
        onView(withId(R.id.login_password))
                .perform(typeText("123456"), closeSoftKeyboard());
        onView(withId(R.id.btn_login))
                .perform(click());
        Thread.sleep(2000);
        intended(hasComponent(InvitationFeedActivity.class.getName()));

    }


    public void isToastMessageDisplayed(String text) {
        onView(withText(text)).inRoot(MobileViewMatchers.isToast()).check(matches(isDisplayed()));
    }
}
