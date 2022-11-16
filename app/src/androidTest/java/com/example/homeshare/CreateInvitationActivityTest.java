package com.example.homeshare;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.contrib.PickerActions;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.matcher.BoundedMatcher;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
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

import android.view.View;
import android.widget.DatePicker;


public class CreateInvitationActivityTest {

    @Rule
    public ActivityScenarioRule<CreateInvitationActivity> activityScenarioRule
            = new ActivityScenarioRule<>(CreateInvitationActivity.class);

    @Before
    public void setUp(){
        Intents.init();
    }

    @After
    public void tearDown(){
        Intents.release();
    }

    @Test
    public void fillTextBoxesTest(){
        onView(withId(R.id.address))
                .perform(typeText("2116 Beechwood"), closeSoftKeyboard());
        onView(withId(R.id.address))
                .check(matches(withText("2116 Beechwood")));
        onView(withId(R.id.milesFromCampus))
                .perform(typeText("1"), closeSoftKeyboard());
        onView(withId(R.id.milesFromCampus))
                .check(matches(withText("1")));
        onView(withId(R.id.utilities))
                .perform(typeText("Basketball Court"), closeSoftKeyboard());
        onView(withId(R.id.utilities))
                .check(matches(withText("Basketball Court")));
        onView(withId(R.id.rent))
                .perform(typeText("1400"), closeSoftKeyboard());
        onView(withId(R.id.rent))
                .check(matches(withText("1400")));
        onView(withId(R.id.numBeds))
                .perform(typeText("10"), closeSoftKeyboard());
        onView(withId(R.id.numBeds))
                .check(matches(withText("10")));
        onView(withId(R.id.dailySchedule))
                .perform(typeText("9-5"), closeSoftKeyboard());
        onView(withId(R.id.dailySchedule))
                .check(matches(withText("9-5")));
        onView(withId(R.id.academicFocus))
                .perform(typeText("Computer Science"), closeSoftKeyboard());
        onView(withId(R.id.academicFocus))
                .check(matches(withText("Computer Science")));

        onView(withId(R.id.academicFocus)).perform(ViewActions.swipeUp());
        onView(withId(R.id.academicFocus)).perform(ViewActions.swipeUp());
        onView(withId(R.id.academicFocus)).perform(ViewActions.swipeUp());
        onView(withId(R.id.academicFocus)).perform(ViewActions.swipeUp());
        onView(withId(R.id.academicFocus)).perform(ViewActions.swipeUp());

        onView(withId(R.id.personality))
                .perform(typeText("Funny"), closeSoftKeyboard());
        onView(withId(R.id.personality))
                .check(matches(withText("Funny")));
        onView(withId(R.id.otherInfo))
                .perform(typeText("This property is awesome"), closeSoftKeyboard());
        onView(withId(R.id.otherInfo))
                .check(matches(withText("This property is awesome")));
    }

    @Test
    public void calendarTest(){
        onView(withId(R.id.address))
                .perform(typeText(""), closeSoftKeyboard());
        onView(withId(R.id.academicFocus)).perform(ViewActions.swipeUp());
        onView(withId(R.id.academicFocus)).perform(ViewActions.swipeUp());
        onView(withId(R.id.academicFocus)).perform(ViewActions.swipeUp());
        onView(withId(R.id.academicFocus)).perform(ViewActions.swipeUp());
        onView(withId(R.id.academicFocus)).perform(ViewActions.swipeUp());
        onView(withId(R.id.academicFocus)).perform(ViewActions.swipeUp());
        onView(withId(R.id.academicFocus)).perform(ViewActions.swipeUp());
        onView(withId(R.id.academicFocus)).perform(ViewActions.swipeUp());
        onView(withId(R.id.academicFocus)).perform(ViewActions.swipeUp());
        onView(withId(R.id.academicFocus)).perform(ViewActions.swipeUp());
        onView(withId(R.id.academicFocus)).perform(ViewActions.swipeUp());
        onView(withId(R.id.academicFocus)).perform(ViewActions.swipeUp());
        onView(withId(R.id.academicFocus)).perform(ViewActions.swipeUp());
        onView(withId(R.id.academicFocus)).perform(ViewActions.swipeUp());
        onView(withId(R.id.academicFocus)).perform(ViewActions.swipeUp());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.date_picker)).perform(PickerActions.setDate(2022, 12, 24));
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.date_picker)).check(matches(matchesDate(2022, 12, 24)));
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public static Matcher<View> matchesDate(final int year, final int month, final int day) {
        return new BoundedMatcher<View, DatePicker>(DatePicker.class) {

            @Override
            public void describeTo(Description description) {
                description.appendText("matches date:");
            }

            @Override
            protected boolean matchesSafely(DatePicker item) {
                return (year == item.getYear() && month == item.getMonth() && day == item.getDayOfMonth());
            }
        };
    }

}
