package ar.edu.utn.frba.mobile.clases.utils.storage.preferences

import android.content.Context

class Preferences(context: Context) {
    private val sharedPreferences = context.getSharedPreferences("Preferences", Context.MODE_PRIVATE)

    companion object {
        private const val showAsGridKey = "showAsGrid"
    }

    var showAsGrid: Boolean = false
        //get() = true // TODO: sacar de sharedPreferences
        //set(value) {} // TODO: guardar en sharedPreferences
}
