package com.exercise.trendyrepos.ui.splash

import android.app.Instrumentation
import android.content.Intent
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers.isRoot
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.exercise.trendyrepos.base.Utils.waitFor
import com.exercise.trendyrepos.ui.dashboard.main.DashboardActivity
import org.hamcrest.Matcher
import org.hamcrest.core.AllOf
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class SplashActivityTest {
    @Before
    fun setUp() {
        Intents.init()
    }

    @After
    fun tearDown() {
        Intents.release()
    }

    @Test
    fun moveToDashboardActivity_whenAnimationIsCompletedOnSplashActivity() {
        ActivityScenario.launch(SplashActivity::class.java).use {
            val expectedIntent: Matcher<Intent> =
                AllOf.allOf(
                    hasComponent(DashboardActivity::class.java.canonicalName)
                )
            Intents.intending(expectedIntent).respondWith(Instrumentation.ActivityResult(0, null))
            onView(isRoot()).perform(waitFor(2000))
            intended(expectedIntent)
        }
    }

}