package ar.edu.utn.frba.mobile.clases.ui.main

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ar.edu.utn.frba.mobile.clases.R
import ar.edu.utn.frba.mobile.clases.databinding.MainFragmentBinding

class MainFragment : Fragment() {
    private lateinit var viewModel: MainViewModel
    private var listener: Listener? = null

    private var _binding: MainFragmentBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = MainFragment()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as Listener
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.resources = MainViewModelResources(context!!)
        binding.button.setOnClickListener {
            listener!!.goto(TermsAndConditionsFragment.newInstance())
        }
    }

    interface Listener {
        fun goto(fragment: Fragment)
    }
}