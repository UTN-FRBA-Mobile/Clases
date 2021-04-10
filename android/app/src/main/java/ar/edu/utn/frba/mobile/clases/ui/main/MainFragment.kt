package ar.edu.utn.frba.mobile.clases.ui.main

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ar.edu.utn.frba.mobile.clases.R
import ar.edu.utn.frba.mobile.clases.databinding.MainFragmentBinding

private const val MAIN_FRAGMENT_ARG_LABEL = "MAIN_FRAGMENT_ARG_LABEL"

class MainFragment : Fragment() {
    private var listener: OnFragmentInteractionListener? = null
    private lateinit var viewModel: MainViewModel

    private var _binding: MainFragmentBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    companion object {
        fun newInstance(title: String) = MainFragment().apply {
            arguments = Bundle().apply {
                putString(MAIN_FRAGMENT_ARG_LABEL, title)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        //viewModel.resources = MainViewModelResources(requireContext())
        arguments?.let {
            viewModel.label = it.getString(MAIN_FRAGMENT_ARG_LABEL)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Forma clásica de encontrar una vista por id
        view.findViewById<TextView>(R.id.textView).text = viewModel.label
        // Forma nueva con View Bindings
        binding.button.setOnClickListener {
            onButtonPressed()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnFragmentInteractionListener")
        }
    }

    override fun onDestroyView() {
        _binding = null

        super.onDestroyView()
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    private fun onButtonPressed() {
        listener?.onButtonTapped(viewModel.salute.asString(requireContext()))
    }

    interface OnFragmentInteractionListener {
        fun onButtonTapped(salute: String)
    }
}
