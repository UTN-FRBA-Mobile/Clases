package ar.edu.utn.frba.mobile.clases

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import ar.edu.utn.frba.mobile.clases.databinding.MainActivityBinding


class MainViewActivity : AppCompatActivity() {

    private lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar!!.setTitle("Conversor de Unidades de Temperatura")

        val conversor = ConversorUnidades()
        val botonC = findViewById(R.id.botonCelsius) as ImageButton

        botonC.setOnClickListener {
            // Llamo a celsiustof y celsius to k
            val valorC = binding.temperaturaIngresadaC.text.toString().toDouble()
            binding.temperaturaIngresadaF.setText(conversor.celsiusToF(valorC).toString())
            binding.temperaturaIngresadaK.setText(conversor.celsiusToK(valorC).toString())
            it.hideKeyboard()
        }

        val botonK = findViewById(R.id.botonKelvin) as ImageButton

        botonK.setOnClickListener {
            // Llamo a kelvintoc y kelvintof
            val valorK = binding.temperaturaIngresadaK.text.toString().toDouble()
            binding.temperaturaIngresadaC.setText(conversor.kelvinToC(valorK).toString())
            binding.temperaturaIngresadaF.setText(conversor.kelvinToF(valorK).toString())
            it.hideKeyboard()
        }

        val botonF = findViewById(R.id.botonFahrenheit) as ImageButton

        botonF.setOnClickListener {
            // Llamo a fahtoc y fahtok
            val valorF = binding.temperaturaIngresadaF.text.toString().toDouble()
            binding.temperaturaIngresadaC.setText(conversor.fahToC(valorF).toString())
            binding.temperaturaIngresadaK.setText(conversor.fahToK(valorF).toString())
            it.hideKeyboard()
        }
    }
    //Esto es extensionMethods!
    fun View.hideKeyboard() {
        val inputManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(windowToken, 0)
    }
}