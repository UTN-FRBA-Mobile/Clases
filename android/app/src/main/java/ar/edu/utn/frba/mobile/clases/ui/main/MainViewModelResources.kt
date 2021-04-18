package ar.edu.utn.frba.mobile.clases.ui.main

import android.content.Context
import ar.edu.utn.frba.mobile.clases.R

class MainViewModelResources(val context: Context): MainViewModel.ResourceProvider {

    override val salute: String
        get() = context.getString(R.string.salute)
}