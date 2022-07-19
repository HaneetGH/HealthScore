package com.technorapper.onboarding.ui

import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.technorapper.onboarding.R
import org.hamcrest.Matchers.not
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
import org.junit.runners.MethodSorters


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class MainActivityTest  {

    @get:Rule
    val mActivityTestRule: ActivityTestRule<MainActivity> =
        object : ActivityTestRule<MainActivity>(MainActivity::class.java) {
            override fun getActivityIntent(): Intent {
                val targetContext = InstrumentationRegistry.getInstrumentation().targetContext
                return Intent(targetContext, MainActivity::class.java).apply {
                    putExtra(
                        "lat",
                        28.7041
                    )
                    putExtra(
                        "lng",
                        77.1025
                    )

                }
            }
        }


    @Test
    fun saveAd() {
        Thread.sleep(4_000)
       // onView(withId(R.id.preci)).check(matches(not(withText("0.0mm"))));

        onView(withId(R.id.current)).check(matches(not(withText("0"))));


    }


}