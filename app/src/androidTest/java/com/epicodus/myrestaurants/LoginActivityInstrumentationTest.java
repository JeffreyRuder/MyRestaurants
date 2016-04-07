package com.epicodus.myrestaurants;


import android.support.test.rule.ActivityTestRule;

import com.epicodus.myrestaurants.ui.LoginActivity;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

public class LoginActivityInstrumentationTest {
    public static final String EMAIL = "hi@111.com";
    public static final String PASSWORD = "111";
    public static final String FAIL = "222";

    @Rule
    public ActivityTestRule<LoginActivity> activityTestRule = new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void logInWithPassword() {
        onView(withId(R.id.emailEditText)).perform(typeText(EMAIL));
        onView(withId(R.id.passwordEditText)).perform(typeText(PASSWORD));
        onView(withId(R.id.passwordLoginButton)).perform(click());
        onView(withId(R.id.appNameTextView)).check(matches(withText("EatAt")));
    }

    @Test
    public void failedLogin() {
        onView(withId(R.id.emailEditText)).perform(typeText(EMAIL));
        onView(withId(R.id.passwordEditText)).perform(typeText(FAIL));
        onView(withId(R.id.passwordLoginButton)).perform(click());
        onView(withText("FirebaseError: The specified password is incorrect.")).check(matches(isDisplayed()));
    }
}
