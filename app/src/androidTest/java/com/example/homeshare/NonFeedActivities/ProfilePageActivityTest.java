package com.example.homeshare.NonFeedActivities;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.example.homeshare.FeedActivities.InvitationFeedActivity;
import com.example.homeshare.NonFeedActivites.CreateInvitationActivity;
import com.example.homeshare.NonFeedActivites.MainActivity;
import com.example.homeshare.NonFeedActivites.ProfilePageActivity;
import com.example.homeshare.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class ProfilePageActivityTest {
    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule
            = new ActivityScenarioRule<>(MainActivity.class);

    @Before
    public void setUp() throws InterruptedException {
        onView(ViewMatchers.withId(R.id.login_email))
                .perform(typeText("testuser1@usc.edu"), closeSoftKeyboard());
        onView(withId(R.id.login_password))
                .perform(typeText("123456"), closeSoftKeyboard());
        onView(withId(R.id.btn_login))
                .perform(click());
        Thread.sleep(2000);
        onView(withId(R.id.top_menu_profile))
                .perform(click());
        Intents.init();
    }

    @After
    public void tearDown(){
        Intents.release();
    }

    @Test
    public void emailAndNamePopulate(){
        onView(withId(R.id.userName))
                .check(matches(withText("Test User")));
        onView(withId(R.id.profileEmail))
                .check(matches(withText("testuser1@usc.edu")));
    }


    @Test
    public void fillTextBoxes() {
        onView(withId(R.id.profilePhone))
                .perform(typeText("123-456-7890"), closeSoftKeyboard());
        onView(withId(R.id.profilePhone))
                .check(matches(withText("123-456-7890")));
        onView(withId(R.id.profileMajor))
                .perform(typeText("Computer Science"), closeSoftKeyboard());
        onView(withId(R.id.profileMajor))
                .check(matches(withText("Computer Science")));
        onView(withId(R.id.profileAboutMe))
                .perform(typeText("Just a silly guy"), closeSoftKeyboard());
        onView(withId(R.id.profileAboutMe))
                .check(matches(withText("Just a silly guy")));
    }

    @Test
    public void navigateToInvitationFeed(){
        onView(withId(R.id.profileGoHome))
                .perform(click());
        intended(hasComponent(InvitationFeedActivity.class.getName()));
    }

    @Test
    public void logOut(){
        onView(withId(R.id.login_logout))
                .perform(click());
        intended(hasComponent(MainActivity.class.getName()));
    }

    @Test
    public void profileInformationNotSaved() {
        onView(withId(R.id.profilePhone))
                .perform(typeText("123-456-7890"), closeSoftKeyboard());
        onView(withId(R.id.profileGoHome))
                .perform(click());
        onView(withId(R.id.top_menu_profile))
                .perform(click());
        onView(withId(R.id.profilePhone))
                .check(matches(withText("")));
    }

    @Test
    public void saveButtonSuccess() {

        onView(withId(R.id.profilePhone))
                .perform(typeText("123-456-7890"), closeSoftKeyboard());
        onView(withId(R.id.edit_profile))
                .perform(click());
        onView(withId(R.id.profileGoHome))
                .perform(click());
        onView(withId(R.id.top_menu_profile))
                .perform(click());
        onView(withId(R.id.profilePhone))
                .check(matches(withText("123-456-7890")));

        onView(withId(R.id.profilePhone))
                .perform(clearText());
        onView(withId(R.id.edit_profile))
                .perform(click());
    }
}
