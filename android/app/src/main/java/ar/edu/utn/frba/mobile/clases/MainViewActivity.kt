package ar.edu.utn.frba.mobile.clases

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ar.edu.utn.frba.mobile.clases.databinding.MainActivityBinding
import ar.edu.utn.frba.mobile.clases.ui.main.MainFragment

class MainViewActivity : AppCompatActivity() {

    lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }
    }
}