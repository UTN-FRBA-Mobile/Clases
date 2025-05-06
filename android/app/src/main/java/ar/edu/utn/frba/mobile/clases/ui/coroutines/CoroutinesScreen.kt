package ar.edu.utn.frba.mobile.clases.ui.coroutines

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import ar.edu.utn.frba.mobile.clases.R
import ar.edu.utn.frba.mobile.clases.ui.theme.ClasesTheme

@Composable
fun CoroutinesScreen() {
    val viewModel = viewModel<CourotinesViewModel>()

    ClasesTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                BotonSample01(
                    onClick = {
                        viewModel.sample1_01()
                    }
                )

                BotonSample02(
                    onClick = {
                        viewModel.sample2_01()
                    }
                )

                val joke = viewModel.jokeFlow.collectAsStateWithLifecycle()
                BotonSample03(
                    onClick = {
                        viewModel.sample3_01()
                    }
                )
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    color = Color.Blue,
                    text = joke.value.joke ?: "Sin chiste"
                )

                TestButton()
            }
        }
    }
}

@Composable
fun BotonSample01(onClick: () -> Unit) {
    Button(
        onClick = onClick
    ) {
        Text(text = stringResource(R.string.coroutines_sample_1))
    }
}

@Composable
fun BotonSample02(onClick: () -> Unit) {
    Button(
        onClick = onClick
    ) {
        Text(text = stringResource(R.string.coroutines_sample_2))
    }
}

@Composable
fun BotonSample03(onClick: () -> Unit) {
    Button(
        onClick = onClick
    ) {
        Text(text = stringResource(R.string.coroutines_sample_3))
    }
}

@Composable
fun TestButton() {
    val counter = remember { mutableIntStateOf(0) }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(top = 16.dp)
            .fillMaxWidth()
    ) {
        Button(
            onClick = {
                counter.intValue += 1
            }
        ) {
            Text(text = stringResource(R.string.coroutines_test))
        }

        Text(
            modifier = Modifier
                .padding(start = 16.dp),
            text = counter.intValue.toString()
        )
    }
}


@Preview
@Composable
fun CoroutinesScreenPreview() {
    CoroutinesScreen()
}