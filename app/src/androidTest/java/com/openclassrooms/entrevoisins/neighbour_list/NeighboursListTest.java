
package com.openclassrooms.entrevoisins.neighbour_list;

import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.events.RemoveFavoriteNeighbourEvent;
import com.openclassrooms.entrevoisins.ui.neighbour_list.ListNeighbourActivity;
import com.openclassrooms.entrevoisins.utils.DeleteViewAction;
import com.openclassrooms.entrevoisins.utils.RemoveViewAction;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withChild;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.openclassrooms.entrevoisins.utils.RecyclerViewItemCountAssertion.withItemCount;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsNull.notNullValue;



/**
 * Test class for list of neighbours
 */
@RunWith(AndroidJUnit4.class)
public class NeighboursListTest {

    // This is fixed
    private static int ITEMS_COUNT = 12;
    private static int ITEM_POSITION = 0;

    private ListNeighbourActivity mActivity;

    @Rule
    public ActivityTestRule<ListNeighbourActivity> mActivityRule =
            new ActivityTestRule(ListNeighbourActivity.class);

    @Before
    public void setUp() {
        mActivity = mActivityRule.getActivity();
        assertThat(mActivity, notNullValue());

    }


    /**
     * We ensure that our recyclerview is displaying at least on item
     */
    @Test
    public void myNeighboursList_shouldNotBeEmpty() {
        // First scroll to the position that needs to be matched and click on it.
        onView(allOf(isDisplayed(),ViewMatchers.withId(R.id.list_neighbours)))
                .check(matches(hasMinimumChildCount(1)));
    }

    /**
     * When we add an item, the item should be displayed
     */
    @Test
    public void myNeighboursList_addNeighbour_shouldAddItem(){
        onView(allOf(isDisplayed(),withId(R.id.list_neighbours))).check(withItemCount(ITEMS_COUNT));
        onView(allOf(isDisplayed(),withId(R.id.add_neighbour))).perform(click());
        onView(allOf(isDisplayed(),withId(R.id.name))).perform(replaceText("Olivier"));
        onView(allOf(isDisplayed(),withId(R.id.create))).perform(click());
        onView(allOf(isDisplayed(),withId(R.id.list_neighbours))).check(withItemCount(ITEMS_COUNT+1));
    }

    /**
     * When we delete an item, the item is no more shown
     */
    @Test
    public void myNeighboursList_deleteAction_shouldRemoveItem() {
        // Given : We remove the element at position 2
        onView(allOf(isDisplayed(),withId(R.id.list_neighbours))).check(withItemCount(ITEMS_COUNT+1));
        // When perform a click on a delete icon
        onView(allOf(isDisplayed(),withId(R.id.list_neighbours)))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, new DeleteViewAction()));
        // Then : the number of element is 11
        onView(allOf(isDisplayed(),withId(R.id.list_neighbours))).check(withItemCount(ITEMS_COUNT));
    }

    /**
     * When we click on a neighbour, the detail is show in a new activity
     */
    @Test
    public void myNeighbour_detailActivity_shouldAppear_And_NameNotNull(){
        onView(allOf(isDisplayed(), withId(R.id.list_neighbours))).perform(RecyclerViewActions.actionOnItemAtPosition(0,click()));
        onView(allOf(isDisplayed(),withId(R.id.activity_detail_card_name))).check(matches(isDisplayed()));
        onView(allOf(isDisplayed(),withId(R.id.activity_detail_card_name))).check(matches(withText("Caroline")));
    }

    @Test
    public void myNeighbourFavoriteList_onlyFavoriteShouldBeDisplayed() {

        onView(allOf(isDisplayed(),withId(R.id.list_neighbours))).perform(actionOnItemAtPosition(ITEM_POSITION,click()));
        onView(allOf(isDisplayed(),withId(R.id.activity_detail_fab))).perform(click());

        ViewInteraction appCompatImageButton = onView(
                allOf(withContentDescription("Navigate up"),
                        childAtPosition(
                                allOf(withId(R.id.activity_detail_toolbar),
                                        childAtPosition(
                                                withId(R.id.activity_detail_collapsing),
                                                1)),
                                0),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        onView(allOf(isDisplayed(),withId(R.id.list_neighbours))).perform(actionOnItemAtPosition(ITEM_POSITION+1,click()));
        onView(allOf(isDisplayed(),withId(R.id.activity_detail_fab))).perform(click());

        ViewInteraction appCompatImageButton2 = onView(
                allOf(withContentDescription("Navigate up"),
                        childAtPosition(
                                allOf(withId(R.id.activity_detail_toolbar),
                                        childAtPosition(
                                                withId(R.id.activity_detail_collapsing),
                                                1)),
                                0),
                        isDisplayed()));
        appCompatImageButton2.perform(click());

        ViewInteraction tabView = onView(
                allOf(withContentDescription("Favorites"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.tabs),
                                        0),
                                1),
                        isDisplayed()));
        tabView.perform(click());

        onView(allOf(isDisplayed(),withId(R.id.list_neighbours))).check(withItemCount(2));
    }

    @Test
    public void myNeighbourFavoriteList_removeNeighbourFromFavorite(){
       //onView(allOf(isDisplayed(),withId(R.id.list_neighbours))).perform(actionOnItemAtPosition(ITEM_POSITION,click()));
       //onView(allOf(isDisplayed(),withId(R.id.activity_detail_fab))).perform(click());

       //ViewInteraction appCompatImageButton = onView(
       //        allOf(withContentDescription("Navigate up"),
       //                childAtPosition(
       //                        allOf(withId(R.id.activity_detail_toolbar),
       //                                childAtPosition(
       //                                        withId(R.id.activity_detail_collapsing),
       //                                        1)),
       //                        0),
       //                isDisplayed()));
       //appCompatImageButton.perform(click());

        ViewInteraction tabView = onView(
                allOf(withContentDescription("Favorites"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.tabs),
                                        0),
                                1),
                        isDisplayed()));
        tabView.perform(click());

        onView(allOf(isDisplayed(),withId(R.id.list_neighbours))).check(withItemCount(1));
        onView(allOf(isDisplayed(),withId(R.id.list_neighbours)))
                .perform(actionOnItemAtPosition(ITEM_POSITION,new RemoveViewAction()));
        onView(allOf(isDisplayed(),withId(R.id.list_neighbours))).check(withItemCount(0));

    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }

}