package ar.edu.utn.frba.mobile.clases.ui.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ar.edu.utn.frba.mobile.clases.R

@Composable
fun TemperatureInputs(
    celsius: Double?,
    setCelsius: (Double) -> Unit,
    kelvin: Double?,
    setKelvin: (Double) -> Unit,
    fahrenheit: Double?,
    setFahrenheit: (Double) -> Unit,
    modifier: Modifier
) {
    TemperatureInput(
        hint = R.string.hint_celsius,
        unit = R.string.unit_celsius,
        value = celsius,
        setValue = setCelsius,
        modifier = modifier.padding(top = 60.dp))
    TemperatureInput(
        hint = R.string.hint_kelvin,
        unit = R.string.unit_kelvin,
        value = kelvin,
        setValue = setKelvin,
        modifier = modifier)
    TemperatureInput(
        hint = R.string.hint_fahrenheit,
        unit = R.string.unit_fahrenheit,
        value = fahrenheit,
        setValue = setFahrenheit,
        modifier = modifier)
}

@Composable
fun DecorationImage(modifier: Modifier) {
    Image(
        painter = painterResource(id = R.drawable.termo2),
        contentDescription = stringResource(id = R.string.decoration),
        modifier = modifier.fillMaxWidth(0.6f).aspectRatio(1f))
}

@Composable
fun MainBox(content: @Composable ColumnScope.() -> Unit) {
    Column(content = content)
}
