package com.technorapper.onboarding.ui.location

import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.rule.ActivityTestRule
import com.technorapper.onboarding.R
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
import org.junit.runners.MethodSorters

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class LocationFetchFromMapActivityTest {

    @Rule
    @JvmField
    var activityRule = ActivityTestRule(LocationFetchFromMapActivity::class.java)

    @Test
    fun A_isViewsVisible() {
        onView(withId(R.id.map)).check(matches(isDisplayed()))

    }

    @Test
    fun saveAd() {
        Thread.sleep(2_000)
        onView(withId(R.id.btnDone))
            .perform(ClickOnButtonView(R.id.btnDone))
    }

    inner class ClickOnButtonView(continueBtn: Int) : ViewAction {
        internal var click = ViewActions.click()

        override fun getConstraints(): org.hamcrest.Matcher<View> {
            return click.constraints
        }

        override fun getDescription(): String {
            return " click on custom button view"
        }

        override fun perform(uiController: UiController, view: View) {
            //btnClickMe -> Custom row item view button
            click.perform(uiController, view.findViewById(R.id.btnDone))
        }
    }
}