package ar.edu.utn.frba.mobile.clases.ui.main

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import ar.edu.utn.frba.mobile.clases.R
import ar.edu.utn.frba.mobile.clases.ui.theme.ClasesTheme

@Composable
fun AppScaffold(
    title: String? = null,
    navController: NavController? = null,
    content: @Composable (PaddingValues) -> Unit) {
    val navigationIcon: (@Composable () -> Unit)? =
        if (navController?.previousBackStackEntry != null) {
            {
                IconButton(onClick = {
                    navController.popBackStack()
                }) {
                    Icons.Filled.ArrowBack
                }
            }
        } else null
    ClasesTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = title ?: stringResource(id = R.string.app_name))
                    },
                    navigationIcon = navigationIcon,
                )
            },
            content = {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    content(it)
                }
            }
        )
    }
}