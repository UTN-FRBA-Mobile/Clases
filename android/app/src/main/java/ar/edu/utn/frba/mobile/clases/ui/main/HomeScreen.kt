package ar.edu.utn.frba.mobile.clases.ui.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import ar.edu.utn.frba.mobile.clases.R

@Composable
fun HomeScreen(navController: NavController? = null) {
    AppScaffold(navController = navController) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Button(onClick = {}) {
                Text(text = stringResource(id = R.string.soy_un_button))
            }
        }
    }
}

@Preview
@Composable
fun DefaultPreview() {
    HomeScreen()
}