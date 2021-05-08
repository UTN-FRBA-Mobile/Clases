package ar.edu.utn.frba.mobile.clases

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import ar.edu.utn.frba.mobile.clases.databinding.ActivityMainBinding
import ar.edu.utn.frba.mobile.clases.fragments.EditImageFragment
import ar.edu.utn.frba.mobile.clases.fragments.ImagesFragment

class MainViewActivity : AppCompatActivity(), ImagesFragment.ImagesFragmentInteractionListener, EditImageFragment.OnFragmentInteractionListener {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, getFragment(intent))
                .commit()
        }
    }

    override fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun popFragment() {
        if (!supportFragmentManager.popBackStackImmediate()) {
            finish()
        }
    }
    companion object {
        const val URI_EXTRA = "ImageURI"

        fun getFragment(intent: Intent): Fragment {
            val uriString = intent.getStringExtra(URI_EXTRA)
            return when {
                uriString != null -> EditImageFragment.newInstance(Uri.parse(uriString))
                else -> ImagesFragment.newInstance()
            }
        }
    }
}
