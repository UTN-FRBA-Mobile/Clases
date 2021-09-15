package ar.edu.utn.frba.mobile.clases

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ar.edu.utn.frba.mobile.clases.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }
}