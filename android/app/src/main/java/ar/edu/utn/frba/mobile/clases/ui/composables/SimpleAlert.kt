package ar.edu.utn.frba.mobile.clases.ui.composables

import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import ar.edu.utn.frba.mobile.clases.R
import ar.edu.utn.frba.mobile.clases.ui.UiString

@Composable
fun SimpleAlert(show: Boolean, text: UiString?, onDismiss: () -> Unit) {
    if (show) {
        AlertDialog(
            onDismissRequest = onDismiss,
            confirmButton = {
                TextButton(onClick = onDismiss)
                { Text(text = stringResource(id = R.string.ok)) }
            },
            title = { Text(text = text?.asString() ?: "") })
    }
}

@Preview
@Composable
private fun DefaultPreview() {
    SimpleAlert(
        show = true,
        text = UiString.Literal("Hola"),
        onDismiss = {}
    )
}
