package com.epicodus.myrestaurants;

import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

import com.epicodus.myrestaurants.ui.RestaurantListActivity;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.pressKey;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

public class RestaurantListActivityInstrumentationTest {
    public static final String RESTAURANT_NAME = "Shut Up and Eat";
    public static final String RESTAURANT_ZIP = "97202";
    public static final String RESTAURANT_CITY = "Portland";

    @Rule
    public ActivityTestRule<RestaurantListActivity> activityTestRule = new ActivityTestRule<>(RestaurantListActivity.class);

    @Test
    public void validateSearchWidget() {
        onView(withId(R.id.action_search)).perform(click());
        onView(isAssignableFrom(EditText.class)).perform(typeText(RESTAURANT_CITY)).check(matches(withText(RESTAURANT_CITY)));
    }

    @Test
    public void restaurantListShows() {
        onView(withId(R.id.action_search)).perform(click());
        onView(isAssignableFrom(EditText.class)).perform(typeText(RESTAURANT_ZIP), pressKey(KeyEvent.KEYCODE_ENTER));
        onView(withId(R.id.recyclerView)).check(matches(hasDescendant(withText(RESTAURANT_NAME))));
    }

    @Test
    public void detailFragmentShows() {
        onView(withId(R.id.action_search)).perform(click());
        onView(isAssignableFrom(EditText.class)).perform(typeText(RESTAURANT_ZIP), pressKey(KeyEvent.KEYCODE_ENTER));
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItem(hasDescendant(withText(RESTAURANT_NAME)), click()));
        onView(allOf(withId(R.id.restaurantNameTextView), isDisplayed())).check(matches(withText(RESTAURANT_NAME)));
    }

    @Test
    public void savedToastIsDisplayed() {
        onView(withId(R.id.action_search)).perform(click());
        onView(isAssignableFrom(EditText.class)).perform(typeText(RESTAURANT_ZIP), pressKey(KeyEvent.KEYCODE_ENTER));
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItem(hasDescendant(withText(RESTAURANT_NAME)), click()));
        onView(allOf(withId(R.id.saveRestaurantButton), isDisplayed())).perform(click());
        onView(withText(R.string.restaurant_saved)).inRoot(new ToastMatcher()).check(matches(withText("Restaurant Saved")));
    }

}
