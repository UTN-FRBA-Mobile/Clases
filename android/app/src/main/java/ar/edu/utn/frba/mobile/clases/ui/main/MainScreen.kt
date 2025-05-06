package ar.edu.utn.frba.mobile.clases.ui.main

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import ar.edu.utn.frba.mobile.clases.CoroutinesActivity
import ar.edu.utn.frba.mobile.clases.R
import ar.edu.utn.frba.mobile.clases.ui.theme.ClasesTheme

@Composable
fun MainScreen() {
    ClasesTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

            val context = LocalContext.current

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)) {
                Button(
                    onClick = {
                        val intent = Intent(context, CoroutinesActivity::class.java)
                        context.startActivity(intent)
                    }
                ) {
                    Text(text = stringResource(R.string.main_go_to_coroutines))
                }
            }
        }
    }
}

@Composable
@Preview
fun MainScreenPreview() {
    MainScreen()
}