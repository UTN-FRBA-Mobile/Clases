package ar.edu.utn.frba.mobile.clases

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import ar.edu.utn.frba.mobile.clases.databinding.MainActivityBinding
import ar.edu.utn.frba.mobile.clases.ui.main.MainFragment
import ar.edu.utn.frba.mobile.clases.ui.status_update.StatusUpdateFragment

class MainViewActivity : AppCompatActivity(), MainFragment.OnFragmentInteractionListener, StatusUpdateFragment.OnFragmentInteractionListener {
    lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        if (savedInstanceState == null) {
            // Solo la primera vez que corre el activity
            // Las demás el propio manager restaura todo como estaba
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, MainFragment.newInstance(getString(R.string.whatAreYouThinking)))
                .commit()
        }
    }

    override fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .addToBackStack(null)
            .setCustomAnimations(R.anim.fragment_push_enter, R.anim.fragment_push_exit, R.anim.fragment_pop_enter, R.anim.fragment_pop_exit)
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }
}