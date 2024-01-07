//package it.feio.android.omninotes.ui;
//
//import static androidx.test.espresso.Espresso.onView;
//import static androidx.test.espresso.assertion.ViewAssertions.matches;
//import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
//import static androidx.test.espresso.matcher.ViewMatchers.hasErrorText;
//import static androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA;
//import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
//import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
//import static androidx.test.espresso.matcher.ViewMatchers.withHint;
//import static androidx.test.espresso.matcher.ViewMatchers.withId;
//import static androidx.test.espresso.matcher.ViewMatchers.withText;
//import static androidx.test.espresso.action.ViewActions.typeText;
//import static androidx.test.espresso.action.ViewActions.clearText;
//import static org.hamcrest.Matchers.allOf;
//import static org.hamcrest.Matchers.anyOf;
//import static it.feio.android.omninotes.utils.IsEqualTrimmingAndIgnoringCase.equalToTrimmingAndIgnoringCase;
//import static it.feio.android.omninotes.utils.VisibleViewMatcher.isVisible;
//
//import android.content.Context;
//import android.os.SystemClock;
//import android.view.View;
//
//import androidx.test.espresso.Espresso;
//import androidx.test.espresso.ViewInteraction;
//import androidx.test.ext.junit.runners.AndroidJUnit4;
//import androidx.test.filters.LargeTest;
//import androidx.test.platform.app.InstrumentationRegistry;
//
//import org.hamcrest.Matcher;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//
//import it.feio.android.omninotes.R;
//
//@RunWith(AndroidJUnit4.class)
//@LargeTest
//public class PasswordActivityTest extends BaseEspressoTest{
//
//    @Test
//    public void testRemovePassword() {
//        // 测试remove
//        openPassword();
//        // 获取context
//        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
//        // 未设置密码时点击remove并校验message
//        onView(withId(R.id.password_remove)).perform(getClickAction());
//        onView(withText(context.getString(R.string.password_not_set))).check(matches(isDisplayed()));
//        waitToScrollEnd();
//        // 设置密码
//        setPasswordWithoutOldPassword();
//        waitToScrollEnd();
//        // 再次打开password
//        onView(withText(R.string.settings_password)).perform(getClickAction());
//        onView(withId(R.id.password_root)).check(matches(isDisplayed()));
//        // 1、点击remove
//        onView(withId(R.id.password_remove)).perform(getClickAction());
//        // 2、校验edit打开
//        ViewInteraction edit = onView(withId(R.id.password_request));
//        ViewInteraction button = onView(withText(context.getString(R.string.ok)));
//        edit.check(matches(isDisplayed()));
//        // 3、设置text为错的old password
//        edit.perform(typeText("wrong password"));
//        button.perform(getClickAction());
//        // 4、校验error
//        edit.check(matches(hasErrorText(context.getString(R.string.wrong_password))));
//        // 5、设置text为正确的old password
//        edit.perform(clearText());
//        edit.perform(typeText("test"));
//        button.perform(getClickAction());
//        // 6、校验dialog显示并点击ok
//        onView(withText(context.getString(R.string.agree_unlocking_all_notes))).check(matches(isDisplayed()));
//        onView(withText(context.getString(R.string.ok))).perform(getClickAction());
//        // 7、校验message
////        waitToScrollEnd();
////        onView(withText(context.getString(R.string.password_successfully_removed))).check(matches(isDisplayed()));
//    }
//
//    @Test
//    public void testForgottenPassword() {
//        // 设置密码后测试forgotten
//        openPassword();
//        // 获取context
//        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
//        // 未设置密码时点击forgotten并校验message
//        onView(withId(R.id.password_forgotten)).perform(getClickAction());
//        onView(withText(context.getString(R.string.password_not_set))).check(matches(isDisplayed()));
//        waitToScrollEnd();
//        // 设置密码
//        setPasswordWithoutOldPassword();
//        waitToScrollEnd();
//        // 再次打开password
//        onView(withText(R.string.settings_password)).perform(getClickAction());
//        onView(withId(R.id.password_root)).check(matches(isDisplayed()));
//        // 1、点击forgotten
//        onView(withId(R.id.password_forgotten)).perform(getClickAction());
//        // 获取对应View
//        ViewInteraction edit = onView(withId(R.id.reset_password_answer));
//        ViewInteraction button = onView(withText(context.getString(R.string.ok)));
//        // 2、校验edit打开
//        edit.check(matches(isDisplayed()));
//        button.check(matches(isDisplayed()));
//        // 3、设置text为错误的answer并点击
//        edit.perform(typeText("wrong answer"));
//        button.perform(getClickAction());
//        // 4、校验wrong answer
//        edit.check(matches(hasErrorText(context.getString(R.string.wrong_answer))));
//        // 5、设置answer为正确的
//        edit.perform(clearText());
//        edit.perform(typeText("test"));
//        button.perform(getClickAction());
//        // 6、校验message
//        onView(withText(context.getString(R.string.password_successfully_removed))).check(matches(isDisplayed()));
//    }
//
//    @Test
//    public void testConfirmPassword() {
//        // 测试confirm的情况
//        openPassword();
//        // 获取context
//        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
//        // 获取各个text
//        ViewInteraction password = onView(withId(R.id.password));
//        ViewInteraction password_check = onView(withId(R.id.password_check));
//        ViewInteraction question = onView(withId(R.id.question));
//        ViewInteraction answer = onView(withId(R.id.answer));
//        ViewInteraction answer_check = onView(withId(R.id.answer_check));
//        ViewInteraction confirm = onView(withId(R.id.password_confirm));
//        // case1：各项不通过check
//        // 1、填写各个text都不过check的情况
//        password.perform(typeText(""));                 // password为空
//        password_check.perform(typeText("password"));   // password check与password不一致
//        Espresso.pressBack();
//        question.perform(typeText(""));     // question为空
//        answer.perform(typeText(""));                   // answer为空
//        answer_check.perform(typeText("answer"));   // answer check与answer不一致
//        Espresso.pressBack();
//        // 2、点击confirm
//        confirm.perform(getClickAction());
//        // 3、校验error
//        password.check(matches(hasErrorText(context.getString(R.string.settings_password_not_matching))));
//        password_check.check(matches(hasErrorText(context.getString(R.string.settings_password_not_matching))));
//        question.check(matches(hasErrorText(context.getString(R.string.settings_password_question))));
//        answer.check(matches(hasErrorText(context.getString(R.string.settings_answer_not_matching))));
//        answer_check.check(matches(hasErrorText(context.getString(R.string.settings_answer_not_matching))));
//        // case2：全为空，通过check
//        // 4、设置为全为空
//        password.perform(clearText());
//        password_check.perform(clearText());
//        question.perform(clearText());
//        answer.perform(clearText());
//        answer_check.perform(clearText());
//        // 5、点击confirm，校验message
//        confirm.perform(getClickAction());
//        onView(withText(context.getString(R.string.empty_password))).check(matches(isDisplayed()));
//        waitToScrollEnd();
//        // case3：设置为test，通过check
//        // 6、设置为test
//        password.perform(typeText("test"));
//        Espresso.pressBack();
//        password_check.perform(typeText("test"));
//        Espresso.pressBack();
//        question.perform(typeText("test"));
//        Espresso.pressBack();
//        answer.perform(typeText("test"));
//        Espresso.pressBack();
//        answer_check.perform(typeText("test"));
//        Espresso.pressBack();
//        // 7、点击confirm，校验message
//        confirm.perform(getClickAction());
//        onView(withText(context.getString(R.string.password_successfully_changed))).check(matches(isDisplayed()));
//        waitToScrollEnd();
//        waitToScrollEnd();
//        // case4：已有旧密码，旧密码填写错误
//        // 8、重新打开password
//        onView(withText(R.string.settings_password)).perform(getClickAction());
//        onView(withId(R.id.password_root)).check(matches(isDisplayed()));
//        // 9、设置为change
//        password.perform(typeText("check"));
//        Espresso.pressBack();
//        password_check.perform(typeText("check"));
//        Espresso.pressBack();
//        question.perform(typeText("check"));
//        Espresso.pressBack();
//        answer.perform(typeText("check"));
//        Espresso.pressBack();
//        answer_check.perform(typeText("check"));
//        Espresso.pressBack();
//        // 10、点击confirm，校验dialog
//        confirm.perform(getClickAction());
//        ViewInteraction edit = onView(withId(R.id.password_request));
//        ViewInteraction button = onView(withText(context.getString(R.string.ok)));
//        edit.check(matches(isDisplayed()));
//        button.check(matches(isDisplayed()));
//        // 11、输入错误的旧密码并校验error
//        edit.perform(typeText("wrong password"));
//        button.perform(getClickAction());
//        edit.check(matches(hasErrorText(context.getString(R.string.wrong_password))));
//        // case5：已有旧密码，旧密码填写正确
//        // 12、输入正确的密码，并校验message
//        edit.perform(clearText());
//        edit.perform(typeText("test"));
//        button.perform(getClickAction());
//        onView(withText(context.getString(R.string.password_successfully_changed))).check(matches(isDisplayed()));
//    }
//
//    private void openPassword() {
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
//        onView(withText(R.string.settings_screen_data)).perform(getClickAction());
//        onView(withText(R.string.settings_password)).perform(getClickAction());
//        onView(withId(R.id.password_root)).check(matches(isDisplayed()));
//    }
//
//    private void setPasswordWithoutOldPassword() {
//        onView(withId(R.id.password)).perform(typeText("test"));
//        Espresso.pressBack();
//        onView(withId(R.id.password_check)).perform(typeText("test"));
//        Espresso.pressBack();
//        onView(withId(R.id.question)).perform(typeText("test"));
//        Espresso.pressBack();
//        onView(withId(R.id.answer)).perform(typeText("test"));
//        Espresso.pressBack();
//        onView(withId(R.id.answer_check)).perform(typeText("test"));
//        Espresso.pressBack();
//        onView(withId(R.id.password_confirm)).perform(getClickAction());
//        waitToScrollEnd();
//    }
//
//    private static Matcher<View> withTextOrHint(final Matcher<String> stringMatcher) {
//        return anyOf(withText(stringMatcher), withHint(stringMatcher));
//    }
//
//    private void waitToScrollEnd() {
//        SystemClock.sleep(500);
//    }
//
//}
