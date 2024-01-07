//package it.feio.android.omninotes.ui;
//
//import static androidx.test.espresso.Espresso.onView;
//import static androidx.test.espresso.action.ViewActions.click;
//import static androidx.test.espresso.action.ViewActions.typeText;
//import static androidx.test.espresso.assertion.ViewAssertions.matches;
//import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
//import static androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA;
//import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
//import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
//import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
//import static androidx.test.espresso.matcher.ViewMatchers.withHint;
//import static androidx.test.espresso.matcher.ViewMatchers.withId;
//import static androidx.test.espresso.matcher.ViewMatchers.withText;
//import static androidx.test.espresso.action.ViewActions.swipeUp;
//import static org.hamcrest.Matchers.allOf;
//import static org.hamcrest.Matchers.anyOf;
//import static it.feio.android.omninotes.utils.IsEqualTrimmingAndIgnoringCase.equalToTrimmingAndIgnoringCase;
//import static it.feio.android.omninotes.utils.VisibleViewMatcher.isVisible;
//
//import android.view.View;
//
//import androidx.test.espresso.Espresso;
//import androidx.test.espresso.ViewInteraction;
//import androidx.test.ext.junit.runners.AndroidJUnit4;
//import androidx.test.filters.LargeTest;
//
//import org.hamcrest.Matcher;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//
//import it.feio.android.omninotes.R;
//
//@RunWith(AndroidJUnit4.class)
//@LargeTest
//public class StatsActivityTest extends BaseEspressoTest{
//
//    @Test
//    public void testStatsActivity() {
//        // 添加一个note
//        addNote();
//        openStats();
//        onView(allOf(withId(R.id.stat_notes_total), withText("1"))).check(matches(isDisplayed()));
//    }
//
//    private void addNote() {
//        // 点击add note
//        ViewInteraction note = onView(withId(R.id.fab_note));
//        note.check(matches(isDisplayed()));
//        note.perform(click());
//        note.perform(click());
//        // 校验detail显示
//        onView(withId(R.id.detail_root)).check(matches(isDisplayed()));
//        // 填写note
//        ViewInteraction title = onView(withId(R.id.detail_title));
//        ViewInteraction content = onView(withId(R.id.detail_content));
//        title.perform(typeText("test"));
//        content.perform(typeText("test"));
//        // 返回
//        Espresso.pressBack();
//        Espresso.pressBack();
//    }
//
//    private void openStats() {
//        // 打开左侧栏
//        onView(allOf(
//                withContentDescription(equalToTrimmingAndIgnoringCase("drawer open")),
//                isVisible(),
//                isDescendantOfA(
//                        allOf(withId(R.id.toolbar), isDescendantOfA(withId(R.id.drawer_layout))))))
//                .perform(getClickAction());
//        // 打开Settings界面，进入SettingsActivity
//        onView(allOf(
//                withId(R.id.settings_view), isVisible(),
//                hasDescendant(
//                        allOf(
//                                withId(R.id.settings),
//                                withTextOrHint(equalToTrimmingAndIgnoringCase("SETTINGS")))),
//                isDescendantOfA(
//                        allOf(
//                                withId(R.id.left_drawer),
//                                isDescendantOfA(
//                                        allOf(
//                                                withId(R.id.navigation_drawer),
//                                                isDescendantOfA(withId(R.id.drawer_layout))))))))
//                .perform(getClickAction());
//        // 向上滑
//        onView(isRoot()).perform(swipeUp());
//        ViewInteraction stats = onView(withText(R.string.settings_statistics));
//        stats.perform(getClickAction());
//    }
//
//    private static Matcher<View> withTextOrHint(final Matcher<String> stringMatcher) {
//        return anyOf(withText(stringMatcher), withHint(stringMatcher));
//    }
//
//}
