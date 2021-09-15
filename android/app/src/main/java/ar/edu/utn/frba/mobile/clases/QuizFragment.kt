package ar.edu.utn.frba.mobile.clases

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import ar.edu.utn.frba.mobile.clases.databinding.FragmentQuizBinding



class QuizFragment : Fragment() {

    private var _binding: FragmentQuizBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentQuizBinding.inflate(inflater, container, false)


        //Levanto los argumentos del fragment anterior
        val nombre = arguments?.getString("Nombre")
        binding.rtaNombreAlumno.text = nombre
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.botonSubmit.setOnClickListener{

            if (binding.radioSi.isChecked){
               findNavController().navigate(QuizFragmentDirections.actionQuizFragmentToWinFragment())

            }else if(binding.radioNo.isChecked){
                findNavController().navigate(QuizFragmentDirections.actionQuizFragmentToLoseFragment())
            }
        }

    }
}