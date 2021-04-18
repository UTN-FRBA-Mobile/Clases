package ar.edu.utn.frba.mobile.clases.ui.main

import androidx.lifecycle.ViewModel

class MainViewModel() : ViewModel() {
    lateinit var resources: MainViewModel.ResourceProvider

    val salute get() = resources.salute

    interface ResourceProvider {
        val salute: String
    }
}