package ar.edu.utn.frba.mobile.clases.ui.main

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import ar.edu.utn.frba.mobile.clases.ConversorUnidades

@Composable
fun HomeScreen(navController: NavController? = null) {
    val conversor = remember { ConversorUnidades() }
    val (kelvin, setKelvin) = remember { mutableStateOf<Double?>(null) }
    val celsius = kelvin?.let { conversor.kelvinToC(it) }
    val fahrenheit = kelvin?.let { conversor.kelvinToF(it) }
    AppScaffold(navController = navController) {
        MainBox {
            TemperatureInputs(
                celsius = celsius,
                setCelsius = { setKelvin(conversor.celsiusToK(it)) },
                kelvin = kelvin,
                setKelvin = setKelvin,
                fahrenheit = fahrenheit,
                setFahrenheit = { setKelvin(conversor.fahToK(it)) },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth(0.8f))
            DecorationImage(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 30.dp))
        }
    }
}

@Composable
fun TemperatureInput(@StringRes hint: Int, @StringRes unit: Int, value: Double?, setValue: (Double) -> Unit, modifier: Modifier) {
    val (text, setText) = remember(value) { mutableStateOf(value?.toString() ?: "" )}
    val textStyle = TextStyle(
        color = Color.Black
    )
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier) {
        TextField(
            value = text,
            onValueChange = setText,
            singleLine = true,
            textStyle = textStyle,
            trailingIcon = {
                Text(
                    text = stringResource(id = unit),
                    fontSize = 15.sp)
            },
            placeholder = {
                Text(
                    text = stringResource(hint),
                    style = textStyle,
                    fontSize = 11.sp)
            },
            modifier = Modifier
                .weight(1f)
                .testTag(stringResource(unit))//Acá para identificar en base a la unidad de temp
        )
        Button(
            onClick = { text.toDoubleOrNull()?.let(setValue) },
            modifier = Modifier
                .padding(start = 10.dp)
                .testTag(stringResource(hint))
        ) {
            Image(
                painter = painterResource(id = android.R.drawable.stat_notify_sync),
                contentDescription = stringResource(hint))
        }
    }
}

@Preview
@Composable
fun DefaultPreview() {
    HomeScreen()
}