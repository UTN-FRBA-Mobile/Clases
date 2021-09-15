package ar.edu.utn.frba.mobile.clases

import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import ar.edu.utn.frba.mobile.clases.databinding.FragmentMainBinding

class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.botonPlay.setOnClickListener{

            val nombre = binding.nombreAlumno.text.toString()

            when{
                nombre.isEmpty() ->{
                    val toast = Toast.makeText(activity,"Ingrese un nombre", Toast.LENGTH_SHORT)
                    toast.setGravity(Gravity.BOTTOM,0,0)
                    toast.show()
                }
                else ->{
                    val bundle = bundleOf("Nombre" to nombre)
                    val action = R.id.action_mainFragment_to_quizFragment
                    findNavController().navigate(action,bundle)}
            }

        }

    }
}