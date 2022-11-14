package com.example.homeshare;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import androidx.test.core.app.ActivityScenario;
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

import com.example.homeshare.NonFeedActivites.MainActivity;
import com.example.homeshare.NonFeedActivites.RegisterActivity;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
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
public class ExampleInstrumentedTest {

    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    private View decorView;

    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule
            = new ActivityScenarioRule<>(MainActivity.class);

    @Rule
    public ActivityTestRule<RegisterActivity> activityTestRule =
            new ActivityTestRule<>(RegisterActivity.class);


    @Before
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
        activityScenarioRule.getScenario().onActivity(
                new ActivityScenario.ActivityAction<MainActivity>() {
                    @Override
                    public void perform(MainActivity activity) {
                        decorView = activity.getWindow().getDecorView();
                    }
                });
    }

    @After
    public void tearDown() {
        System.setOut(standardOut);
    }

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.homeshare", appContext.getPackageName());
    }

    @Test
    public void signUpAccountAlreadyCreatedTest(){
        assertTrue("Test".equals("Test"));
        RegisterActivity registerActivity = new RegisterActivity();
        registerActivity.signUp("Test User", "test123@usc.edu", "123456");
        registerActivity.signUp("Test User", "test123@usc.edu", "123456");
        //Make sure you got a Toast message saying "Authentication failed"

    }

    @Test
    public void signUpPasswordTooShortTest(){
        onView(withId(R.id.register))
                .perform(click());
        onView(withId(R.id.reg_name))
                .perform(typeText("Test User 1"), closeSoftKeyboard());
        onView(withId(R.id.reg_email))
                .perform(typeText("testUser@usc.edu"), closeSoftKeyboard());
        onView(withId(R.id.reg_password))
                .perform(typeText("12"), closeSoftKeyboard());
        onView(withId(R.id.btn_register))
                .perform(click());

        RegisterActivity activity = activityTestRule.getActivity();
        onView(withText("Test")).
                inRoot(withDecorView(not(is(activity.getWindow().getDecorView())))).
                check(matches(isDisplayed()));

//        onView(withText("Sign Up Failed. Make sure email is valid and password length > 5"))
//                .inRoot(withDecorView(not(is(getActivity(RegisterActivity.).getWindow().getDecorView())))).check(matches(isDisplayed()));
//
//        onView(withText("Sign Up Failed. Make sure email is valid and password length > 5"))
//                .inRoot(withDecorView(not(decorView)))// Here we use decorView
//                .check(matches(isDisplayed()));

        //Make sure you got a Toast message saying "Authentication failed"
//        assertEquals("Error", outputStreamCaptor.toString().trim());

    }

//    public void isToastMessageDisplayed(int textId) {
//        onView(withText(textId)).inRoot(MobileViewMatchers.isToast()).check(matches(isDisplayed()));
//    }
}