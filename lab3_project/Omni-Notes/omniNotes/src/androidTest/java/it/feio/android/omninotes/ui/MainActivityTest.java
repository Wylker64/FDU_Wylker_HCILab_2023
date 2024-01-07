package it.feio.android.omninotes.ui;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.longClick;
import static androidx.test.espresso.action.ViewActions.pressKey;
import static androidx.test.espresso.action.ViewActions.swipeLeft;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.hasErrorText;
import static androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withHint;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anyOf;
import static it.feio.android.omninotes.utils.ConstantsBase.PREF_PASSWORD;
import static it.feio.android.omninotes.utils.ConstantsBase.PREF_PASSWORD_ANSWER;
import static it.feio.android.omninotes.utils.ConstantsBase.PREF_PASSWORD_QUESTION;
import static it.feio.android.omninotes.utils.IsEqualTrimmingAndIgnoringCase.equalToTrimmingAndIgnoringCase;
import static it.feio.android.omninotes.utils.VisibleViewMatcher.isVisible;

import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.view.KeyEvent;
import android.view.View;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.pixplicity.easyprefs.library.Prefs;

import org.hamcrest.Matcher;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import it.feio.android.omninotes.R;
import it.feio.android.omninotes.models.Tag;
import it.feio.android.omninotes.testutils.ClickPercentKt;
import it.feio.android.omninotes.utils.Security;
import it.feio.android.omninotes.utils.TagsHelper;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest extends BaseEspressoTest {

    @Test
    public void testEnterMainActivityWithLock() {
        // 第一次打开，上锁
        lock();
        activityRule.finishActivity();
        // 模拟第二次打开，不输入密码
        activityRule.launchActivity(new Intent());
        Espresso.pressBack();
        Espresso.pressBackUnconditionally();
        assert activityRule.getActivity().isDestroyed();
        // 模拟第三次打开，先错误密码
        activityRule.launchActivity(new Intent());
        ViewInteraction edit = onView(withId(R.id.password_request));
        edit.check(matches(isDisplayed()));
        ViewInteraction button = onView(withText(R.string.ok));
        edit.perform(typeText("wrong password"));
        button.perform(click());
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        edit.check(matches(hasErrorText(context.getString(R.string.wrong_password))));
        // 正确密码
        edit.perform(clearText());
        edit.perform(typeText("test"));
        button.perform(click());
        onView(withId(R.id.fragment_container)).check(matches(isDisplayed()));
        activityRule.finishActivity();
        // 模拟第四次打开，忘记密码，删除掉密码
        activityRule.launchActivity(new Intent());
        button = onView(withText(R.string.password_forgot));
        button.check(matches(isDisplayed()));
        button.perform(click());
        // 输入answer
        edit = onView(withId(R.id.reset_password_answer));
        edit.check(matches(isDisplayed()));
        edit.perform(typeText("test"));
        onView(withText(R.string.ok)).perform(click());
        // 再设置password但不上锁
        setPassword();
        activityRule.finishActivity();
        // 模拟第五次打开，直接进入
        activityRule.launchActivity(new Intent());
        onView(withId(R.id.fragment_container)).check(matches(isDisplayed()));
    }

    @Test
    public void testOpenDrawerAndSettings() {
        openSettings();
        // 回到main
        Espresso.pressBack();
        Espresso.pressBack();
        // 校验container
        onView(withId(R.id.fragment_container)).check(matches(isDisplayed()));
        // 退出
        Espresso.pressBackUnconditionally();
    }

    @Test
    public void testSearchEmptyNote() {
        // 点击search
        ViewInteraction search = onView(allOf(withId(R.id.menu_search), isDisplayed()));
        search.check(matches(isDisplayed()));
        search.perform(click());
        // 输入search内容
        ViewInteraction text = onView(withId(R.id.search_src_text));
        text.check(matches(isDisplayed()));
        text.perform(typeText("test"));
        // 按下确定
        onView(allOf(withId(R.id.menu_search), isDisplayed())).perform(pressKey(KeyEvent.KEYCODE_ENTER));
        // 返回
        Espresso.pressBack();
    }

    @Test
    public void testAddEmptyNote() {
        // 点击add note
        ViewInteraction note = onView(withId(R.id.fab_note));
        note.check(matches(isDisplayed()));
        note.perform(click());
        note.perform(click());
        // 校验detail显示
        onView(withId(R.id.detail_root)).check(matches(isDisplayed()));
        // 不填写note返回
        Espresso.pressBack();
        // 校验message
        onView(withText(R.string.empty_note_not_saved)).check(matches(isDisplayed()));
    }

    @Test
    public void testSearchNote() {
        addNote();
        // 点击search
        ViewInteraction search = onView(allOf(withId(R.id.menu_search), isDisplayed()));
        search.check(matches(isDisplayed()));
        search.perform(click());
        // 输入search内容
        ViewInteraction text = onView(withId(R.id.search_src_text));
        text.check(matches(isDisplayed()));
        text.perform(typeText("test"));
        // 按下确定
        onView(allOf(withId(R.id.menu_search), isDisplayed())).perform(pressKey(KeyEvent.KEYCODE_ENTER));
        // 返回
        Espresso.pressBack();
    }

    @Test
    public void testChangeKey() {
        // 点击menu sort
        ViewInteraction sort = onView(withId(R.id.menu_sort));
        sort.check(matches(isDisplayed()));
        sort.perform(click());
        // TODO
        // 获取上下文
//        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
//        String[] column = context.getResources().getStringArray(R.array.sortable_columns_human_readable);
//        onView(withText("Title")).perform(click());
    }

    @Test
    public void testReduceAndExpand() {
        addNote();
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Espresso.openActionBarOverflowOrOptionsMenu(context);
        onView(withText(R.string.contracted_view)).perform(click());
        Espresso.openActionBarOverflowOrOptionsMenu(context);
        onView(withText(R.string.expanded_view)).perform(click());
    }

    @Test
    public void testSharedNoteWithoutAttachment() {
        addNote();
        onView(withId(R.id.list)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.menu_share)).perform(click());
        waitToScrollEnd();
        waitToScrollEnd();
        waitToScrollEnd();
    }

    @Test
    public void testSharedNoteWithOneAttachment() {
        addNote();
        onView(withId(R.id.list)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        // 添加附件
        onView(withId(R.id.menu_attachment)).perform(click());
        waitToScrollEnd();
        // 校验attachment_dialog
        onView(withId(R.id.attachment_dialog_root)).check(matches(isDisplayed()));
        // 点击sketch并校验sketch fragment
        onView(withId(R.id.sketch)).perform(getClickAction());
        onView(withId(R.id.drawing_question)).check(matches(isDisplayed()));
        // 进行涂鸦
        onView(withId(R.id.drawing)).perform(swipeLeft());
        Espresso.pressBack();
        // 已有附件，点击shared
        onView(withId(R.id.menu_share)).perform(click());
        waitToScrollEnd();
        waitToScrollEnd();
        waitToScrollEnd();
    }

    @Test
    public void testSharedNoteWithTwoAttachments() {
        addNote();
        onView(withId(R.id.list)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        // 添加第一个附件
        onView(withId(R.id.menu_attachment)).perform(click());
        waitToScrollEnd();
        // 校验attachment_dialog
        onView(withId(R.id.attachment_dialog_root)).check(matches(isDisplayed()));
        // 点击sketch并校验sketch fragment
        onView(withId(R.id.sketch)).perform(getClickAction());
        onView(withId(R.id.drawing_question)).check(matches(isDisplayed()));
        // 进行涂鸦
        onView(withId(R.id.drawing)).perform(swipeLeft());
        Espresso.pressBack();
        // 添加第二个附件
        onView(withId(R.id.menu_attachment)).perform(click());
        waitToScrollEnd();
        // 校验attachment_dialog
        onView(withId(R.id.attachment_dialog_root)).check(matches(isDisplayed()));
        // 点击sketch并校验sketch fragment
        onView(withId(R.id.sketch)).perform(getClickAction());
        onView(withId(R.id.drawing_question)).check(matches(isDisplayed()));
        // 进行涂鸦
        onView(withId(R.id.drawing)).perform(swipeLeft());
        Espresso.pressBack();
        // shared
        onView(withId(R.id.menu_share)).perform(click());
        waitToScrollEnd();
        waitToScrollEnd();
        waitToScrollEnd();
    }

    @Test
    public void testSwipedToArchived() {
        addNote();
        onView(withId(R.id.list)).perform(RecyclerViewActions.actionOnItemAtPosition(0, swipeLeft()));
    }

    @Test
    public void testCategory() {
        createCategory();
        // 打开左侧栏
        onView(allOf(
                withContentDescription(equalToTrimmingAndIgnoringCase("drawer open")),
                isVisible(),
                isDescendantOfA(
                        allOf(withId(R.id.toolbar), isDescendantOfA(withId(R.id.drawer_layout))))))
                .perform(getClickAction());
        onView(withId(R.id.left_drawer)).check(matches(isDisplayed()));
        // 校验TestCategory存在
        ViewInteraction category = onView(withText("TestCategory"));
        category.check(matches(isDisplayed()));
        // 长按test
        category.perform(longClick());
        // 点击delete
        onView(withText(R.string.delete)).perform(click());
        onView(withText(R.string.confirm)).perform(click());
    }

    @Test
    public void testDeleteNote() {
        addNote();
        // 设置swipe to trash
        Prefs.putBoolean("settings_swipe_to_trash", true);
        // swipe
        onView(withId(R.id.list)).perform(RecyclerViewActions.actionOnItemAtPosition(0, swipeLeft()));
        waitToScrollEnd();
        // 打开左侧，校验trash
        openLeftDrawer();
        ViewInteraction trash = onView(withText(R.string.trash));
        trash.check(matches(isDisplayed()));
        // 点击trash并点击第一个note
        trash.perform(getClickAction());
        onView(withId(R.id.list)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        // delete
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Espresso.openActionBarOverflowOrOptionsMenu(context);
        onView(withText(R.string.delete)).perform(click());
        onView(withText(R.string.ok)).perform(click());
        waitToScrollEnd();
    }

    @Test
    public void testAddTag() {
        addTag();
        // 校验
        List<Tag> tags = TagsHelper.getAllTags();
        assert tags.size() == 1;
        assert "#test".equals(tags.get(0).toString());
    }

    @Test
    public void testCreateShortcut() {
        addNote();
        onView(withId(R.id.list)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Espresso.openActionBarOverflowOrOptionsMenu(context);
        // 点击add short cut
        ViewInteraction shortcut = onView(withText(R.string.add_shortcut));
        shortcut.check(matches(isDisplayed()));
        shortcut.perform(getClickAction());
        waitToScrollEnd();
        waitToScrollEnd();
        // 启动快捷方式
//        activityRule.finishActivity();
//        activityRule.getActivity().switchToList();
//        ActivityTestRule<ShortcutActivity> rule = new ActivityTestRule<>(ShortcutActivity.class, false, false);
//        Uri uri = new Uri.Builder().scheme("app")
//                .authority("it.feio.android.omninotes.alpha")
//                .appendQueryParameter("id", String.valueOf(0))
//                .build();
//        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//        rule.launchActivity(intent);
    }

    @Test
    public void testSearchTagByClickTag() {
        addTag();
        waitToScrollEnd();
        waitToScrollEnd();
        // 点击第一个note
        onView(withId(R.id.list)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.detail_content)).perform(ClickPercentKt.clickPercent(0.2f, 0.1f));
        ViewInteraction open = onView(withText(R.string.open));
        open.check(matches(isDisplayed()));
        open.perform(click());
        waitToScrollEnd();
    }

    private void setPassword() {
        Prefs.putString(PREF_PASSWORD, Security.md5("test"));
        Prefs.putString(PREF_PASSWORD_QUESTION, "test");
        Prefs.putString(PREF_PASSWORD_ANSWER, Security.md5("test"));
    }

    private void lock() {
        // lock
        Prefs.putBoolean("settings_password_access", true);
        setPassword();
    }

    private void addNote() {
        // 点击add note
        ViewInteraction note = onView(withId(R.id.fab_note));
        note.check(matches(isDisplayed()));
        note.perform(click());
        note.perform(click());
        // 校验detail显示
        onView(withId(R.id.detail_root)).check(matches(isDisplayed()));
        // 填写note
        ViewInteraction title = onView(withId(R.id.detail_title));
        ViewInteraction content = onView(withId(R.id.detail_content));
        title.perform(typeText("test"));
        content.perform(typeText("test"));
        // 返回
        Espresso.pressBack();
        Espresso.pressBack();
    }

    private void createCategory() {
        addNote();
        onView(withId(R.id.list)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        ViewInteraction category = onView(withId(R.id.menu_category));
        category.check(matches(isDisplayed()));
        category.perform(getClickAction());
        ViewInteraction button = onView(withText(R.string.add_category));
        button.check(matches(isDisplayed()));
        button.perform(getClickAction());
        ViewInteraction title = onView(withId(R.id.category_title));
        title.check(matches(isDisplayed()));
        title.perform(typeText("TestCategory"));
        onView(withText(R.string.ok)).perform(click());
        waitToScrollEnd();
        Espresso.pressBack();
    }

    private void openLeftDrawer() {
        // 打开左侧栏
        onView(allOf(
                withContentDescription(equalToTrimmingAndIgnoringCase("drawer open")),
                isVisible(),
                isDescendantOfA(
                        allOf(withId(R.id.toolbar), isDescendantOfA(withId(R.id.drawer_layout))))))
                .perform(getClickAction());
        onView(withId(R.id.left_drawer)).check(matches(isDisplayed()));
    }

    private void openSettings() {
        openLeftDrawer();
        // 打开Settings界面，进入SettingsActivity（覆盖activity结束的生命周期函数）
        onView(allOf(
                withId(R.id.settings_view), isVisible(),
                hasDescendant(
                        allOf(
                                withId(R.id.settings),
                                withTextOrHint(equalToTrimmingAndIgnoringCase("SETTINGS")))),
                isDescendantOfA(
                        allOf(
                                withId(R.id.left_drawer),
                                isDescendantOfA(
                                        allOf(
                                                withId(R.id.navigation_drawer),
                                                isDescendantOfA(withId(R.id.drawer_layout))))))))
                .perform(getClickAction());
    }

    private void addTag() {
        // 点击add note
        ViewInteraction note = onView(withId(R.id.fab_note));
        note.check(matches(isDisplayed()));
        note.perform(click());
        note.perform(click());
        // 校验detail显示
        onView(withId(R.id.detail_root)).check(matches(isDisplayed()));
        // 填写note
        ViewInteraction title = onView(withId(R.id.detail_title));
        ViewInteraction content = onView(withId(R.id.detail_content));
        title.perform(typeText("test"));
        content.perform(typeText("test tag: #test is tag"));
        // 返回
        Espresso.pressBack();
        Espresso.pressBack();
    }

    private static Matcher<View> withTextOrHint(final Matcher<String> stringMatcher) {
        return anyOf(withText(stringMatcher), withHint(stringMatcher));
    }

    private void waitToScrollEnd() {
        SystemClock.sleep(500);
    }
}
