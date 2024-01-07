//package it.feio.android.omninotes.ui;
//
//import static androidx.test.espresso.Espresso.onView;
//import static androidx.test.espresso.assertion.ViewAssertions.matches;
//import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
//import static androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA;
//import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
//import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
//import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
//import static androidx.test.espresso.matcher.ViewMatchers.withHint;
//import static androidx.test.espresso.matcher.ViewMatchers.withId;
//import static androidx.test.espresso.matcher.ViewMatchers.withParent;
//import static androidx.test.espresso.matcher.ViewMatchers.withText;
//import static it.feio.android.omninotes.utils.IsEqualTrimmingAndIgnoringCase.equalToTrimmingAndIgnoringCase;
//import static it.feio.android.omninotes.utils.VisibleViewMatcher.isVisible;
//import static org.hamcrest.Matchers.allOf;
//import static org.hamcrest.Matchers.anyOf;
//import static org.hamcrest.Matchers.instanceOf;
//
//import android.content.Context;
//import android.os.SystemClock;
//import android.view.View;
//import android.widget.TextView;
//
//import androidx.test.espresso.Espresso;
//import androidx.test.espresso.ViewInteraction;
//import androidx.test.ext.junit.runners.AndroidJUnit4;
//import androidx.test.filters.LargeTest;
//import androidx.test.platform.app.InstrumentationRegistry;
//
//import it.feio.android.omninotes.R;
//import it.feio.android.omninotes.testutils.ClickPercentKt;
//
//import org.hamcrest.Matcher;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//
//@LargeTest
//@RunWith(AndroidJUnit4.class)
//public class SettingsActivityTest extends BaseEspressoTest {
//
//  @Test
//  public void testReplaceFragment() {
//    // 测试在SettingsActivity界面点击各个选项，是否正确切换fragment
//    openSettings();
//    // 获取content_frame
//    ViewInteraction content_frame = onView(withId(R.id.content_frame));
//    content_frame.check(matches(isDisplayed()));
//    // 获取context
//    Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
//    // 获取toolbar text
//    ViewInteraction toolbar = onView(allOf(instanceOf(TextView.class), withParent(withId(R.id.toolbar))));
//    // 1、点击Data并进入settings_screen_data(SettingsFragment)
//    onView(withText(R.string.settings_screen_data)).perform(getClickAction()).check(matches(isDisplayed()));
//    // 校验toolbar的内容
//    toolbar.check(matches(withText(context.getString(R.string.settings_screen_data))));
//    Espresso.pressBack();
//    content_frame.check(matches(isDisplayed()));
//    // 2、点击Interface
//    onView(withText(R.string.settings_screen_interface)).perform(getClickAction()).check(matches(isDisplayed()));
//    toolbar.check(matches(withText(context.getString(R.string.settings_screen_interface))));
//    Espresso.pressBack();
//    content_frame.check(matches(isDisplayed()));
//    // 3、Navigation
//    onView(withText(R.string.settings_screen_navigation)).perform(getClickAction());
//    toolbar.check(matches(withText(context.getString(R.string.settings_screen_navigation))));
//    Espresso.pressBack();
//    content_frame.check(matches(isDisplayed()));
//    // 4、Behaviors
//    onView(withText(R.string.settings_screen_behaviors)).perform(getClickAction());
//    toolbar.check(matches(withText(context.getString(R.string.settings_screen_behaviors))));
//    Espresso.pressBack();
//    content_frame.check(matches(isDisplayed()));
//    // 5、Notifications
//    onView(withText(R.string.settings_screen_notifications)).perform(getClickAction());
//    toolbar.check(matches(withText(context.getString(R.string.settings_screen_notifications))));
//    Espresso.pressBack();
//    content_frame.check(matches(isDisplayed()));
//    // 6、Privacy
//    onView(withText(R.string.settings_screen_privacy)).perform(getClickAction());
//    toolbar.check(matches(withText(context.getString(R.string.settings_screen_privacy))));
//    Espresso.pressBack();
//    content_frame.check(matches(isDisplayed()));
//  }
//
//  @Test
//  public void testBackPressed() {
//    // 测试连续多次后是否会正常按fragment顺序返回
//    openSettings();
//    // 获取content_frame
//    ViewInteraction content_frame = onView(withId(R.id.content_frame));
//    content_frame.check(matches(isDisplayed()));
//    // 获取context
//    Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
//    // 获取toolbar text
//    ViewInteraction toolbar = onView(allOf(instanceOf(TextView.class), withParent(withId(R.id.toolbar))));
//    // 1、点击Data并进入settings_screen_data(SettingsFragment)
//    onView(withText(R.string.settings_screen_data)).perform(getClickAction()).check(matches(isDisplayed()));
//    // 校验toolbar的内容
//    toolbar.check(matches(withText(context.getString(R.string.settings_screen_data))));
//    // 2、点击Sync and Backups
//    onView(withText(R.string.settings_screen_sync_import_export)).perform(getClickAction()).check(matches(isDisplayed()));
//    toolbar.check(matches(withText(context.getString(R.string.settings_screen_sync_import_export))));
//    // 3、模拟返回，触发onBackPressed()
//    ViewInteraction back = onView(withId(R.id.toolbar));
//    back.check(matches(isDisplayed()));
//    back.perform(ClickPercentKt.clickPercent(0.1f, 0.1f));
//    onView(withText(R.string.settings_screen_data)).check(matches(isDisplayed()));
//    toolbar.check(matches(withText(context.getString(R.string.settings_screen_data))));
//  }
//
//  @Test
//  public void testImport() {
//    // 测试importNotes（覆盖showMessages）
//    openSettings();
//    // 获取content_frame
//    ViewInteraction content_frame = onView(withId(R.id.content_frame));
//    content_frame.check(matches(isDisplayed()));
//    // 获取context
//    Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
//    // 获取toolbar text
//    ViewInteraction toolbar = onView(allOf(instanceOf(TextView.class), withParent(withId(R.id.toolbar))));
//    // 1、点击Data并进入settings_screen_data(SettingsFragment)
//    onView(withText(R.string.settings_screen_data)).perform(getClickAction()).check(matches(isDisplayed()));
//    // 校验toolbar的内容
//    toolbar.check(matches(withText(context.getString(R.string.settings_screen_data))));
//    // 2、点击Sync and Backups
//    onView(withText(R.string.settings_screen_sync_import_export)).perform(getClickAction()).check(matches(isDisplayed()));
//    toolbar.check(matches(withText(context.getString(R.string.settings_screen_sync_import_export))));
//    // 3、模拟点击restore
//    onView(withText(R.string.settings_import)).perform(getClickAction());
//    waitToScrollEnd();
//    onView(allOf(withText(R.string.no_backups_available))).check(matches(isDisplayed()));
//  }
//
//  @Test
//  public void testPointerCaptureChanged() {
//    // 覆盖onPointerCaptureChanged
//    openSettings();
//    // 获取content_frame
//    onView(isRoot()).perform(getSwipeAction(540, 1002, 540, 1302));
//  }
//
//  private void openSettings() {
//    // 打开左侧栏
//    onView(allOf(
//            withContentDescription(equalToTrimmingAndIgnoringCase("drawer open")),
//            isVisible(),
//            isDescendantOfA(
//                    allOf(withId(R.id.toolbar), isDescendantOfA(withId(R.id.drawer_layout))))))
//            .perform(getClickAction());
////    waitToScrollEnd();
//    // 打开Settings界面，进入SettingsActivity
//    onView(allOf(
//            withId(R.id.settings_view), isVisible(),
//            hasDescendant(
//                    allOf(
//                            withId(R.id.settings),
//                            withTextOrHint(equalToTrimmingAndIgnoringCase("SETTINGS")))),
//            isDescendantOfA(
//                    allOf(
//                            withId(R.id.left_drawer),
//                            isDescendantOfA(
//                                    allOf(
//                                            withId(R.id.navigation_drawer),
//                                            isDescendantOfA(withId(R.id.drawer_layout))))))))
//            .perform(getClickAction());
////    waitToScrollEnd();
//  }
//
//  private static Matcher<View> withTextOrHint(final Matcher<String> stringMatcher) {
//    return anyOf(withText(stringMatcher), withHint(stringMatcher));
//  }
//
//  private void waitToScrollEnd() {
//    SystemClock.sleep(1500);
//  }
//
//}
