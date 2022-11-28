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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;


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
                .perform(typeText("505 W 31st Street, Los Angeles, CA"), closeSoftKeyboard());
        onView(withId(R.id.address))
                .check(matches(withText("505 W 31st Street, Los Angeles, CA")));
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
                .perform(typeText("505 W 31st Street, Los Angeles, CA"), closeSoftKeyboard());
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
    public void successfullyCreateInvitation() throws InterruptedException {
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

        Thread.sleep(2000);
        onView(withId(R.id.top_menu_create_a_post))
                .perform(click());
        onView(withId(R.id.address))
                .perform(typeText("505 W 31st Street, Los Angeles, CA"), closeSoftKeyboard());
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

        Thread.sleep(2000);
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

        Thread.sleep(2000);
        //Check post was successfully created
        onView(allOf(withId(R.id.otherInfo), withText("Testing other info")))
                .check(matches(withText("Testing other info")));

        deleteInvitationAfterTesting();
    }

    public void deleteInvitationAfterTesting(){
        FirebaseFirestore
                .getInstance()
                .collection("invitations")
                .whereEqualTo("academicFocus", "Testing Major")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            DocumentReference documentReference = document.getReference();
                            documentReference.delete();
                        }
                    } else {
                        System.out.println("Could Not Accept Response");
                    }
                });
    }

    public void isToastMessageDisplayed(String text) {
        onView(withText(text)).inRoot(MobileViewMatchers.isToast()).check(matches(isDisplayed()));
    }
}
