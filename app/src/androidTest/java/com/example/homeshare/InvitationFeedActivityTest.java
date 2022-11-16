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
import com.example.homeshare.FeedActivities.ResponseFeedActivity;
import com.example.homeshare.FeedActivities.RoommateFeedActivity;
import com.example.homeshare.NonFeedActivites.CreateInvitationActivity;
import com.example.homeshare.NonFeedActivites.MainActivity;
import com.example.homeshare.NonFeedActivites.ProfilePageActivity;
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


public class InvitationFeedActivityTest {

    @Rule
    public ActivityScenarioRule<InvitationFeedActivity> activityScenarioRule
            = new ActivityScenarioRule<>(InvitationFeedActivity.class);

    @Before
    public void setUp(){
        Intents.init();
    }

    @After
    public void tearDown(){
        Intents.release();
    }

    @Test
    public void navigateToProfilePage(){
        onView(withId(R.id.top_menu_profile))
                .perform(click());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        intended(hasComponent(ProfilePageActivity.class.getName()));
    }

    @Test
    public void navigateToResponseFeed(){
        onView(withId(R.id.responseFeedMenuBotton))
                .perform(click());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        intended(hasComponent(ResponseFeedActivity.class.getName()));
    }

    @Test
    public void navigateToRoommateFeed(){
        onView(withId(R.id.roommateFeedMenuButton))
                .perform(click());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        intended(hasComponent(RoommateFeedActivity.class.getName()));
    }

    @Test
    public void navigateToCreateAnInvitationPage(){
        onView(withId(R.id.top_menu_create_a_post))
                .perform(click());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        intended(hasComponent(CreateInvitationActivity.class.getName()));
    }

}
