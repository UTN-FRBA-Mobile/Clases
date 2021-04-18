package ar.edu.utn.frba.mobile.clases.ui.signup

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ar.edu.utn.frba.mobile.clases.R
import ar.edu.utn.frba.mobile.clases.databinding.FragmentSignUpStepOneBinding
import ar.edu.utn.frba.mobile.clases.databinding.FragmentSignUpStepTwoBinding

class SignUpStepTwoFragment : Fragment() {
    private var _binding: FragmentSignUpStepTwoBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private var listener: OnFragmentInteractionListener? = null

    private lateinit var viewModel: SignUpViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = FragmentSignUpStepTwoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.signUpButton.setOnClickListener {
            listener!!.onFinishSignUp()
        }

        viewModel = ViewModelProvider(activity!!).get(SignUpViewModel::class.java)

        binding.userName.setText(viewModel.userName)
        binding.password.setText(viewModel.password)
    }

    override fun onPause() {
        viewModel.userName = binding.userName.text.toString()
        viewModel.password = binding.password.text.toString()

        super.onPause()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnFragmentInteractionListener {
        fun onFinishSignUp()
    }
}