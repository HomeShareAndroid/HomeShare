package com.example.homeshare.NonFeedActivities;

import androidx.test.espresso.contrib.PickerActions;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.not;

import com.example.homeshare.NonFeedActivites.CreateInvitationActivity;
import com.example.homeshare.R;
import com.example.homeshare.Util.MobileViewMatchers;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;


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
    public void fillTextBoxesCorrectly(){
        onView(ViewMatchers.withId(R.id.address))
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
        onView(withId(R.id.personality))
                .perform(scrollTo(), typeText("Funny"), closeSoftKeyboard());
        onView(withId(R.id.personality))
                .check(matches(withText("Funny")));
        onView(withId(R.id.otherInfo))
                .perform(scrollTo(), typeText("This property is awesome"), closeSoftKeyboard());
        onView(withId(R.id.otherInfo))
                .check(matches(withText("This property is awesome")));
    }

    @Test
    public void numBedsRentNotNumbers(){
        onView(withId(R.id.address))
                .perform(typeText("2116 Beechwood"), closeSoftKeyboard());
        onView(withId(R.id.milesFromCampus))
                .perform(typeText("NOT AN INTEGER"), closeSoftKeyboard());
        onView(withId(R.id.utilities))
                .perform(typeText("Basketball Court"), closeSoftKeyboard());
        onView(withId(R.id.rent))
                .perform(typeText("NOT AN INTEGER"), closeSoftKeyboard());
        onView(withId(R.id.numBeds))
                .perform(typeText("NOT AN INTEGER"), closeSoftKeyboard());
        onView(withId(R.id.dailySchedule))
                .perform(typeText("9-5"), closeSoftKeyboard());
        onView(withId(R.id.academicFocus))
                .perform(typeText("Computer Science"), closeSoftKeyboard());
        onView(withId(R.id.personality))
                .perform(scrollTo(), typeText("Testing Personality"), closeSoftKeyboard());
        onView(withId(R.id.otherInfo))
                .perform(scrollTo(), typeText("Testing other info"), closeSoftKeyboard());
        onView(withId(R.id.date_picker)).perform(scrollTo(), PickerActions.setDate(2022, 12, 24));
        onView(withId(R.id.button))
                .perform(scrollTo(), click());

        isToastMessageDisplayed("Rent, number of beds, and miles from campus must be numbers");
    }


    @Test
    public void successfullyCreateInvitation(){
        onView(withId(R.id.top_menu_profile))
                .perform(click());
        onView(withId(R.id.login_logout))
                .perform(click());
        onView(withId(R.id.login_email))
                .perform(typeText("createInvTestUser@usc.edu"), closeSoftKeyboard());
        onView(withId(R.id.login_password))
                .perform(typeText("123456"), closeSoftKeyboard());
        onView(withId(R.id.btn_login))
                .perform(click());

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.top_menu_create_a_post))
                .perform(click());
        onView(withId(R.id.address))
                .perform(typeText("Testing address"), closeSoftKeyboard());
        onView(withId(R.id.milesFromCampus))
                .perform(typeText("1"), closeSoftKeyboard());
        onView(withId(R.id.utilities))
                .perform(typeText("Testing Utilities"), closeSoftKeyboard());
        onView(withId(R.id.rent))
                .perform(typeText("700"), closeSoftKeyboard());
        onView(withId(R.id.numBeds))
                .perform(typeText("1"), closeSoftKeyboard());
        onView(withId(R.id.dailySchedule))
                .perform(typeText("9-5"), closeSoftKeyboard());
        onView(withId(R.id.academicFocus))
                .perform(typeText("Testing Major"), closeSoftKeyboard());
        onView(withId(R.id.personality))
                .perform(scrollTo(), typeText("Testing Personality"), closeSoftKeyboard());
        onView(withId(R.id.otherInfo))
                .perform(scrollTo(), typeText("Testing other info"), closeSoftKeyboard());
        onView(withId(R.id.date_picker)).perform(scrollTo(), PickerActions.setDate(2022, 11, 30));
        onView(withId(R.id.button))
                .perform(scrollTo(), click());

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.top_menu_profile))
                .perform(click());
        onView(withId(R.id.login_logout))
                .perform(click());
        onView(withId(R.id.login_email))
                .perform(typeText("testUser1@usc.edu"), closeSoftKeyboard());
        onView(withId(R.id.login_password))
                .perform(typeText("123456"), closeSoftKeyboard());
        onView(withId(R.id.btn_login))
                .perform(click());

        try {
            Thread.sleep(7000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //Check post was successfully created
        onView(allOf(withId(R.id.otherInfo), withText("Testing other info")))
                .check(matches(withText("Testing other info")));

    }

//    @Test
//    public void calendarTest(){
//        onView(withId(R.id.address))
//                .perform(typeText(""), closeSoftKeyboard());
//        onView(withId(R.id.academicFocus)).perform(ViewActions.swipeUp());
//        onView(withId(R.id.academicFocus)).perform(ViewActions.swipeUp());
//        onView(withId(R.id.academicFocus)).perform(ViewActions.swipeUp());
//        onView(withId(R.id.academicFocus)).perform(ViewActions.swipeUp());
//        onView(withId(R.id.academicFocus)).perform(ViewActions.swipeUp());
//        onView(withId(R.id.academicFocus)).perform(ViewActions.swipeUp());
//        onView(withId(R.id.academicFocus)).perform(ViewActions.swipeUp());
//        onView(withId(R.id.academicFocus)).perform(ViewActions.swipeUp());
//        onView(withId(R.id.academicFocus)).perform(ViewActions.swipeUp());
//        onView(withId(R.id.academicFocus)).perform(ViewActions.swipeUp());
//        onView(withId(R.id.academicFocus)).perform(ViewActions.swipeUp());
//        onView(withId(R.id.academicFocus)).perform(ViewActions.swipeUp());
//        onView(withId(R.id.academicFocus)).perform(ViewActions.swipeUp());
//        onView(withId(R.id.academicFocus)).perform(ViewActions.swipeUp());
//        onView(withId(R.id.academicFocus)).perform(ViewActions.swipeUp());
//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        onView(withId(R.id.date_picker)).perform(PickerActions.setDate(2022, 12, 24));
//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        onView(withId(R.id.date_picker)).check(matches(matchesDate(2022, 12, 24)));
//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
//
//
//    public static Matcher<View> matchesDate(final int year, final int month, final int day) {
//        return new BoundedMatcher<View, DatePicker>(DatePicker.class) {
//
//            @Override
//            public void describeTo(Description description) {
//                description.appendText("matches date:");
//            }
//
//            @Override
//            protected boolean matchesSafely(DatePicker item) {
//                return (year == item.getYear() && month == item.getMonth() && day == item.getDayOfMonth());
//            }
//        };
//    }

    public void isToastMessageDisplayed(String text) {
        onView(withText(text)).inRoot(MobileViewMatchers.isToast()).check(matches(isDisplayed()));
    }
}
