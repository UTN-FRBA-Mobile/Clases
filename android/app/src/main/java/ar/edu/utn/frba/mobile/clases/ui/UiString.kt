package ar.edu.utn.frba.mobile.clases.ui

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

sealed class UiString {
    data class Literal(val value: String): UiString()
    class Resource(@StringRes val resId: Int, vararg val args: Any): UiString()

    @Composable
    fun asString(): String =
        when(this) {
            is Literal -> value
            is Resource -> stringResource(resId, *args)
        }

    fun asString(context: Context) =
        when (this) {
            is Literal -> value
            is Resource -> context.getString(resId, *args)
        }
}
