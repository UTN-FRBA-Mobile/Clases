package ar.edu.utn.frba.mobile.clases

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ar.edu.utn.frba.mobile.clases.ui.main.HomeScreen
import ar.edu.utn.frba.mobile.clases.ui.status_update.StatusUpdateScreen
import ar.edu.utn.frba.mobile.clases.ui.theme.ClasesTheme

class MainComposeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            App()
        }
    }
}

@Composable
private fun App() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(
                onButtonClick = { navController.navigate("post") },
                navController = navController)
        }
        composable("post") {
            StatusUpdateScreen(
                navController = navController)
        }
    }
}