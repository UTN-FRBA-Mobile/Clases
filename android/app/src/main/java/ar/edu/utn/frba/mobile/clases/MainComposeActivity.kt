package ar.edu.utn.frba.mobile.clases

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ar.edu.utn.frba.mobile.clases.ui.UiString
import ar.edu.utn.frba.mobile.clases.ui.composables.SimpleAlert
import ar.edu.utn.frba.mobile.clases.ui.main.HomeScreen
import ar.edu.utn.frba.mobile.clases.ui.main.MainViewModel

class MainComposeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            App()
        }
    }
}

@Composable
private fun App() {
    val navController = rememberNavController()
    val showAlert = remember { mutableStateOf(Pair<Boolean, UiString?>(false, null)) }
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            val viewModel = viewModel<MainViewModel>()
            viewModel.label = stringResource(id = R.string.soy_un_button)
            HomeScreen(
                viewModel = viewModel,
                onButtonClicked = { text -> showAlert.value = Pair(true, text) },
                navController = navController
            )
        }
    }
    SimpleAlert(show = showAlert.value.first, text = showAlert.value.second, onDismiss = { showAlert.value = Pair(false, null) })
}
