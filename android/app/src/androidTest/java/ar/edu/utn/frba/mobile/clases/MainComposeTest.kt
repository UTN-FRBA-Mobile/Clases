package ar.edu.utn.frba.mobile.clases

import android.content.Context
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.core.app.ApplicationProvider
import ar.edu.utn.frba.mobile.clases.ui.main.HomeScreen
import org.junit.Rule
import org.junit.Test

class MainComposeTest {

    @get:Rule val composeTestRule = createComposeRule()

    @Test
    fun botonesDesactivadosSinTexto() {
        composeTestRule.setContent {
            HomeScreen()
        }
        val context: Context = ApplicationProvider.getApplicationContext()
        composeTestRule
            .onNodeWithTag(context.getString(R.string.unit_celsius))
            .assertTextEquals("", context.getString(R.string.unit_celsius), context.getString(R.string.hint_celsius))
        composeTestRule
            .onNodeWithContentDescription(context.getString(R.string.hint_celsius))
            .assertIsNotEnabled()
    }

    @Test
    fun ingresoValorYconviertoCaF() {
        composeTestRule.setContent {
            HomeScreen()
        }
        val context: Context = ApplicationProvider.getApplicationContext()
        composeTestRule
            .onNodeWithTag(context.getString(R.string.unit_celsius))
            .performTextInput("10")
        composeTestRule
            .onNodeWithContentDescription(context.getString(R.string.hint_celsius))
            .performClick()
        composeTestRule
            .onNodeWithTag(context.getString(R.string.unit_fahrenheit))
            .assertTextEquals("50.0", context.getString(R.string.unit_fahrenheit))
    }
}
