package ar.edu.utn.frba.mobile.clases

import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class MainActivityTestSecundaria {

    @Rule
    @JvmField
    var activityRule = ActivityTestRule<MainActivity>(
        MainActivity::class.java
    )

    @Test
    fun ingresoValorYconviertoKaC() {
        onView(withId(R.id.temperaturaIngresadaK)).perform(ViewActions.typeText("283.15"))
        onView(withId(R.id.botonKelvin)).perform(ViewActions.click())
        onView(withId(R.id.temperaturaIngresadaC)).check(matches(withText("10.0")))

    }
}