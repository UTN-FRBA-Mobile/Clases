package ar.edu.utn.frba.mobile.clases.ui.main

import androidx.lifecycle.ViewModel
import ar.edu.utn.frba.mobile.clases.R
import ar.edu.utn.frba.mobile.clases.ui.UiString

class MainViewModel() : ViewModel() {
    var label: String? = null

    val salute get() = UiString.Resource(R.string.salute)
}
