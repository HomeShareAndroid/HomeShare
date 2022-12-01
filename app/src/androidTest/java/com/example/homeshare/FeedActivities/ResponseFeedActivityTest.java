package com.example.homeshare.FeedActivities;

import androidx.test.espresso.contrib.PickerActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
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

import com.example.homeshare.NonFeedActivites.MainActivity;
import com.example.homeshare.Util.MobileViewMatchers;
import com.example.homeshare.Util.MyViewAction;
import com.example.homeshare.NonFeedActivites.CreateInvitationActivity;
import com.example.homeshare.NonFeedActivites.ProfilePageActivity;
import com.example.homeshare.R;
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


public class ResponseFeedActivityTest {

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
    public void clickOnRoommateProfile() throws InterruptedException {
        createInvitationForTestingAndAcceptIt();
        Thread.sleep(2000);
        onView(withId(R.id.visitResponderProfile))
                .perform(click());

//        intended(hasComponent(ProfilePageActivity.class.getName()));
        Thread.sleep(2000);

        onView(withId(R.id.userName))
                .check(matches(withText("Test User")));
        onView(withId(R.id.profileEmail))
                .check(matches(withText("testuser1@usc.edu")));
        deleteInvitationAfterTesting();
        deleteInvitationResponseAfterTesting();
    }

    @Test
    public void clickOnInvitationResponseAcceptButton() throws InterruptedException {
        createInvitationForTestingAndAcceptIt();
        Thread.sleep(2000);
        onView(withId(R.id.acceptResponse))
                .perform(click());

        Thread.sleep(2000);
        intended(hasComponent(RoommateFeedActivity.class.getName()));
//        isToastMessageDisplayed("Accepted Response! New Roommate");
        deleteInvitationAfterTesting();
        deleteInvitationResponseAfterTesting();
    }


    public void createInvitationForTestingAndAcceptIt() throws InterruptedException {
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
        onView(withId(R.id.date_picker)).perform(scrollTo(), PickerActions.setDate(2022, 12, 14));
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
        onView(withId(R.id.recycler_view)).perform(
                RecyclerViewActions.actionOnItemAtPosition(0, MyViewAction.clickChildViewWithId(R.id.acceptInvitation2)));

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
        onView(withId(R.id.responseFeedMenuBotton))
                .perform(click());
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

    public void deleteInvitationResponseAfterTesting(){
        DocumentReference posterDoc = FirebaseFirestore
                .getInstance()
                .collection("users")
                .document("1o7RnvvZRtgiWN54DAleGIvPSwN2");

        FirebaseFirestore
                .getInstance()
                .collection("invitationresponses")
                .whereEqualTo("posterRef", posterDoc)
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
