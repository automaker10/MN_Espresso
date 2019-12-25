package TestScript;

import android.os.RemoteException;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.action.GeneralLocation;
import android.support.test.espresso.action.GeneralSwipeAction;
import android.support.test.espresso.action.Press;
import android.support.test.espresso.action.Swipe;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.yossisegev.movienight.MainActivity;
import com.yossisegev.movienight.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Random;

import androidx.test.uiautomator.UiDevice;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class TC01_Popular {
    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void TC01_Popular() {
        Random random = new Random();
        ViewInteraction recycleView;
        String[] no = {"3","4"};

        try {
            Thread.sleep(2500);
            for(int i=0 ;i< no.length;i++){

                recycleView = onView(allOf(ViewMatchers.withId(R.id.popular_movies_recyclerview)));
                recycleView.perform(actionOnItemAtPosition(Integer.parseInt(no[i]), click()));
                Thread.sleep(1000);

                swipeFromTopToBottom();

                recycleView = onView(allOf(withId(R.id.details_videos),
                        childAtPosition(withId(R.id.details_video_section), 2)));
                recycleView.perform(actionOnItemAtPosition(0, click()));
                Thread.sleep(3000);

                UiDevice mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
                mDevice.pressRecentApps();
                mDevice.pressRecentApps();

                recycleView = onView(allOf(withId(R.id.details_favorite_fab), isDisplayed()));
                recycleView.perform(click());

                pressBack();
                Thread.sleep(1000);
            }

            recycleView = onView(allOf(withId(R.id.action_favorites),
                    withContentDescription("My Favorites"), isDisplayed()));
            recycleView.perform(click());

            for(int k = 0; k < no.length; k++){

                recycleView = onView(allOf(withId(R.id.favorite_movies_recyclerview)));
                recycleView.perform(actionOnItemAtPosition(0, click()));

                Thread.sleep(1000);

                recycleView = onView(allOf(withId(R.id.details_favorite_fab), isDisplayed()));
                recycleView.perform(click());

                pressBack();

            }


        }catch (InterruptedException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
    public static Matcher<View> childAtPosition(
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

    private static ViewAction swipeFromTopToBottom() {
        return new GeneralSwipeAction(Swipe.FAST, GeneralLocation.TOP_CENTER,
                GeneralLocation.BOTTOM_CENTER, Press.FINGER);
    }

}
