package TestScript;

import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.yossisegev.movienight.MainActivity;
import com.yossisegev.movienight.R;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.InputStream;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeTextIntoFocusedView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class TC02_Search {


    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void TC02_Search(){

        String json = null;
        String name = null;
        String jsonObjectAsString ;
        String flag ;

        try{
            ViewInteraction bottomNavigationItemView = onView(allOf(ViewMatchers.withId(R.id.action_search),
                    withContentDescription("Search"),isDisplayed()));
            bottomNavigationItemView.perform(click());

            //JSON ** Convert JSON to Java
            InputStream is = mActivityTestRule.getActivity().getAssets().open("MovieNight.json");
            if(is!=null) {
                flag = "Pass";
                int size = is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();
                json = new String(buffer, "UTF-8");

                JSONArray jsonArray = new JSONArray(json);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    jsonObjectAsString = jsonObject.toString();
                }

                //Input data JSON to App
                for (int i = 0; i < jsonArray.length(); i++) {
                    ViewInteraction SearchEditText = onView(withId(R.id.search_movies_edit_text));

                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    jsonObjectAsString = jsonObject.getString("Name");

                    SearchEditText.perform(typeTextIntoFocusedView(jsonObjectAsString));

                    Thread.sleep(3000);
                    SearchEditText.perform(clearText());
                    pressBack();
                    Thread.sleep(1000);

                }

                Thread.sleep(3000);
            }else{
                flag="Fail";
            }
        }catch (Exception e){
            flag ="Fail";
            e.printStackTrace();
        }
    }

}
