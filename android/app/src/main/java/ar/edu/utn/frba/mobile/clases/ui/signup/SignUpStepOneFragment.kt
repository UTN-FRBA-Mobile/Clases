package ar.edu.utn.frba.mobile.clases.ui.signup

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import ar.edu.utn.frba.mobile.clases.databinding.FragmentSignUpStepOneBinding

class SignUpStepOneFragment : Fragment() {
    private var _binding: FragmentSignUpStepOneBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private var listener: OnFragmentInteractionListener? = null

    private lateinit var viewModel: SignUpViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        _binding = FragmentSignUpStepOneBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.nextButton.setOnClickListener {
            listener!!.onSignUpNextStep()
        }

        viewModel = ViewModelProvider(requireActivity()).get()

        binding.firstName.setText(viewModel.firstName)
        binding.lastName.setText(viewModel.lastName)
        binding.dni.setText(viewModel.dni)
        binding.phoneNumer.setText(viewModel.phoneNumer)
    }

    override fun onPause() {
        viewModel.firstName = binding.firstName.text.toString()
        viewModel.lastName = binding.lastName.text.toString()
        viewModel.dni = binding.dni.text.toString()
        viewModel.phoneNumer = binding.phoneNumer.text.toString()

        super.onPause()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnFragmentInteractionListener {
        fun onSignUpNextStep()
    }
}