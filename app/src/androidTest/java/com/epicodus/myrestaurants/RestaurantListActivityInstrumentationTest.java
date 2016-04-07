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

    @Rule
    public ActivityTestRule<RestaurantListActivity> activityTestRule = new ActivityTestRule<>(RestaurantListActivity.class);

    @Test
    public void validateSearchWidget() {
        onView(withId(R.id.action_search)).perform(click());
        onView(isAssignableFrom(EditText.class)).perform(typeText("Portland")).check(matches(withText("Portland")));
    }

    @Test
    public void restaurantListShows() {
        onView(withId(R.id.action_search)).perform(click());
        onView(isAssignableFrom(EditText.class)).perform(typeText("97202"), pressKey(KeyEvent.KEYCODE_ENTER));
        onView(withId(R.id.recyclerView)).check(matches(hasDescendant(withText("Shut Up and Eat"))));
    }

    @Test
    public void detailFragmentShows() {
        onView(withId(R.id.action_search)).perform(click());
        onView(isAssignableFrom(EditText.class)).perform(typeText("97202"), pressKey(KeyEvent.KEYCODE_ENTER));
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItem(
                hasDescendant(withText("Shut Up and Eat")), click()));
//        onView(withId(R.id.viewPager)).check(matches(hasDescendant(withText("Shut Up and Eat"))));
        onView(allOf(withId(R.id.restaurantNameTextView), isDisplayed())).check(matches(withText("Shut Up and Eat")));

    }

}
