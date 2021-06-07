package ar.edu.utn.frba.mobile.clases.ui.main

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Handler
import android.os.Looper
import android.view.ViewGroup
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import ar.edu.utn.frba.mobile.clases.R


@Composable
fun TermsAndConditionsScreen(navController: NavController? = null) {
    val (displayMenu, setDisplayMenu) = remember { mutableStateOf(false) }
    val context = LocalContext.current
    val onAccept: () -> Unit = {
        Toast.makeText(context, R.string.accept, Toast.LENGTH_LONG).show()
        navController?.popBackStack()
    }
    val onDoNotAccept = {
        Toast.makeText(context, R.string.dontAccept, Toast.LENGTH_LONG).show()
    }
    val jsInterface = remember() {
        object {

            @JavascriptInterface
            fun getAppName() = context.getString(R.string.app_name)

            @JavascriptInterface
            fun acceptTermsAndConditions() {
                Handler(Looper.getMainLooper()).post {
                    onAccept()
                }
            }

            @JavascriptInterface
            fun doNotAcceptTermsAndConditions() {
                Handler(Looper.getMainLooper()).post {
                    onDoNotAccept()
                }
            }
        }
    }
    AppScaffold(
        navController = navController,
        actions = {
            IconButton(onClick = { setDisplayMenu(!displayMenu) }) {
                Icon(Icons.Default.MoreVert, "")
            }

            // Creating a dropdown menu
            DropdownMenu(
                expanded = displayMenu,
                onDismissRequest = { setDisplayMenu(false) }
            ) {

                // Creating dropdown menu item, on click
                // would create a Toast message
                DropdownMenuItem(onClick = onAccept) {
                    Text(text = stringResource(id = R.string.acceptMenu))
                }

                // Creating dropdown menu item, on click
                // would create a Toast message
                DropdownMenuItem(onClick = onDoNotAccept) {
                    Text(text = stringResource(id = R.string.dontAcceptMenu))
                }
            }
        }) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            WebView(
                url = "file:///android_asset/termsAndConditions.html",
            jsInterface = jsInterface)
        }
    }
}

@SuppressLint("SetJavaScriptEnabled", "JavascriptInterface")
@Composable
fun WebView(url: String, jsInterface: Any) {
    AndroidView(factory = {
        WebView(it).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            webViewClient = WebViewClient()
            setBackgroundColor(Color.TRANSPARENT)
            settings.javaScriptEnabled = true
            addJavascriptInterface(jsInterface, "Clases")
            loadUrl(url)
        }
    }, update = {
        it.loadUrl(url)
    }, modifier = Modifier
        .windowInsetsPadding(WindowInsets.navigationBars))
}

@Preview
@Composable
private fun DefaultTCPreview() {
    TermsAndConditionsScreen()
}
