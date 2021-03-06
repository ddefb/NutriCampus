package com.nutricampus.app.acceptance;

import android.app.Activity;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;
import android.view.WindowManager;

import com.nutricampus.app.R;
import com.nutricampus.app.activities.CadastrarUsuarioActivity;
import com.nutricampus.app.activities.MainActivity;
import com.nutricampus.app.database.SharedPreferencesManager;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Collection;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.closeSoftKeyboard;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.runner.lifecycle.Stage.RESUMED;

/**
 * Created by Mateus on 29/06/2017.
 * For project NutriCampus.
 * Contact: <paulomatew@gmail.com>
 */

@java.lang.SuppressWarnings("squid:S2925") //  SonarQube ignora o sleep())
@RunWith(AndroidJUnit4.class)
@LargeTest
public class AcceptanceTestCadastrarUsuarioActivity {
    private Activity currentActivity;

    @Rule
    public ActivityTestRule<CadastrarUsuarioActivity> mActivityRule = new ActivityTestRule<>(
            CadastrarUsuarioActivity.class);

    @Before
    public void unlockScreen() {
        final CadastrarUsuarioActivity activity = mActivityRule.getActivity();
        Runnable wakeUpDevice = new Runnable() {
            public void run() {
                activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON |
                        WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                        WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            }
        };
        activity.runOnUiThread(wakeUpDevice);
    }

    @Test
    public void attempToCreateAccountSuccessfully() throws Exception {
        doLogout();

        onView(withId(R.id.edtNome)).perform(typeText("attempToCreateAccountSuccessfully"));
        closeKeyboard();
        onView(withId(R.id.edtCpf)).perform(typeText("44502396605"));
        closeKeyboard();
        onView(withId(R.id.edtRegistro)).perform(typeText("44502396605"));
        closeKeyboard();
        onView(withId(R.id.edtEmail)).perform(typeText("attempToCreateAccountSuccessfully@email.com"));
        closeKeyboard();
        onView(withId(R.id.edtSenha)).perform(typeText("12345"));
        closeKeyboard();

        onView(withId(R.id.btn_salvar_cadastro)).perform(click());

        try {
            new ToastMatcher().isToastMessageDisplayedWithText("attempToCreateAccountSuccessfully cadastrado com sucesso!");
            Thread.sleep(3500);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Thread.sleep(3000);
    }

    public void closeKeyboard() throws Exception {
        closeSoftKeyboard();
        Thread.sleep(1000);
    }

    public void doLogout() throws Exception {
        if (getActivityInstance() instanceof MainActivity) {
            new SharedPreferencesManager(mActivityRule.getActivity()).logoutUser();
            currentActivity.finish();
        }
    }

    public Activity getActivityInstance() {
        getInstrumentation().runOnMainSync(new Runnable() {
            public void run() {
                Collection resumedActivities = ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(RESUMED);
                if (resumedActivities.iterator().hasNext()) {
                    currentActivity = (Activity) resumedActivities.iterator().next();
                }
            }
        });

        return currentActivity;
    }
}
