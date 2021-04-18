package ar.edu.utn.frba.mobile.clases

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import ar.edu.utn.frba.mobile.clases.databinding.MainActivityBinding
import ar.edu.utn.frba.mobile.clases.ui.movies.MoviesFragment
import ar.edu.utn.frba.mobile.clases.ui.login.LoginFragment
import ar.edu.utn.frba.mobile.clases.ui.signup.SignUpStepOneFragment
import ar.edu.utn.frba.mobile.clases.ui.signup.SignUpStepTwoFragment

class MainViewActivity : AppCompatActivity(), LoginFragment.OnFragmentInteractionListener, SignUpStepOneFragment.OnFragmentInteractionListener, SignUpStepTwoFragment.OnFragmentInteractionListener {
    private lateinit var loginFragment: Fragment
    private lateinit var signUpStepOneFragment: Fragment
    private lateinit var signUpStepTwoFragment: Fragment
    private lateinit var dashboardFragment: Fragment

    lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        if (savedInstanceState == null) {
            loginFragment = LoginFragment()

            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, loginFragment)
                    .commitNow()
        }
    }

    override fun onLogin(username: String, password: String) {
        dashboardFragment = MoviesFragment()

        supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)

        supportFragmentManager.beginTransaction().remove(loginFragment).add(R.id.container, dashboardFragment).commitNow()
    }

    override fun onSignUp() {
        signUpStepOneFragment = SignUpStepOneFragment()

        supportFragmentManager.beginTransaction().remove(loginFragment).add(R.id.container, signUpStepOneFragment).addToBackStack(null).commit()
    }

    override fun onSignUpNextStep() {
        signUpStepTwoFragment = SignUpStepTwoFragment()

        supportFragmentManager.beginTransaction().remove(signUpStepOneFragment).add(R.id.container, signUpStepTwoFragment).addToBackStack(null).commit()
    }

    override fun onFinishSignUp() {
        supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)

        supportFragmentManager.beginTransaction().show(loginFragment).commitNow()
    }
}