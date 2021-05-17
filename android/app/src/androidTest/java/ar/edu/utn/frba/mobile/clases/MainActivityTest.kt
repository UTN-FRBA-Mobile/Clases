package ar.edu.utn.frba.mobile.clases
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.core.IsNot.not
import androidx.test.rule.ActivityTestRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class MainActivityTest{

    @Rule
    @JvmField
    var activityRule = ActivityTestRule<MainViewActivity>(
        MainViewActivity::class.java
    )

    @Test
    fun botonesDesactivadosSinTexto (){
        onView(withId(R.id.temperaturaIngresadaC)).check(matches(withText("")))
        onView(withId(R.id.botonCelsius)).check(matches(not(isEnabled())))
    }

    @Test
    fun ingresoValorYconviertoCaF (){
        Espresso.onView(ViewMatchers.withId(R.id.temperaturaIngresadaC))
            .perform(ViewActions.typeText("10"))
        Espresso.onView(ViewMatchers.withId(R.id.botonCelsius)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.temperaturaIngresadaF))
            .check(ViewAssertions.matches(ViewMatchers.withText("50.0")))
    }
}