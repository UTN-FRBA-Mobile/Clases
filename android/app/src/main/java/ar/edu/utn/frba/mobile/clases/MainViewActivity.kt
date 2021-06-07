package ar.edu.utn.frba.mobile.clases

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import ar.edu.utn.frba.mobile.clases.databinding.MainActivityBinding
import ar.edu.utn.frba.mobile.clases.ui.main.MainFragment
import ar.edu.utn.frba.mobile.clases.ui.main.TermsAndConditionsFragment

class MainViewActivity : AppCompatActivity(), MainFragment.Listener, TermsAndConditionsFragment.Listener {

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

    override fun goto(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun goBack() {
        supportFragmentManager.popBackStack()
    }
}