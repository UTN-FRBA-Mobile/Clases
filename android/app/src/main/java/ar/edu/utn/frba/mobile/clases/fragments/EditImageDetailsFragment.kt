package ar.edu.utn.frba.mobile.clases.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar

import ar.edu.utn.frba.mobile.clases.R
import ar.edu.utn.frba.mobile.clases.databinding.FragmentEditImageDetailsBinding

class EditImageDetailsFragment : Fragment(), SeekBar.OnSeekBarChangeListener {

    private var _binding: FragmentEditImageDetailsBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private var listener: EditImageDetailsFragmentListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditImageDetailsBinding.inflate(inflater, container, false)

        // keeping brightness value b/w -100 / +100
        binding.seekbarBrightness.max = 200
        binding.seekbarBrightness.progress = 100

        // keeping contrast value b/w 1.0 - 3.0
        binding.seekbarContrast.max = 20
        binding.seekbarContrast.progress = 0

        // keeping saturation value b/w 0.0 - 3.0
        binding.seekbarSaturation.max = 30
        binding.seekbarSaturation.progress = 10

        binding.seekbarBrightness.setOnSeekBarChangeListener(this)
        binding.seekbarContrast.setOnSeekBarChangeListener(this)
        binding.seekbarSaturation.setOnSeekBarChangeListener(this)

        return binding.root
    }

    override fun onProgressChanged(seekBar: SeekBar, initialProgress: Int, b: Boolean) {
        var progress = initialProgress
        if (listener != null) {

            if (seekBar.id == R.id.seekbar_brightness) {
                // brightness values are b/w -100 to +100
                listener!!.onBrightnessChanged(progress - 100)
            }

            if (seekBar.id == R.id.seekbar_contrast) {
                // converting int value to float
                // contrast values are b/w 1.0f - 3.0f
                // progress = progress > 10 ? progress : 10;
                progress += 10
                val floatVal = .10f * progress
                listener!!.onContrastChanged(floatVal)
            }

            if (seekBar.id == R.id.seekbar_saturation) {
                // converting int value to float
                // saturation values are b/w 0.0f - 3.0f
                val floatVal = .10f * progress
                listener!!.onSaturationChanged(floatVal)
            }
        }
    }

    override fun onStartTrackingTouch(seekBar: SeekBar) {
        if (listener != null)
            listener!!.onEditStarted()
    }

    override fun onStopTrackingTouch(seekBar: SeekBar) {
        if (listener != null)
            listener!!.onEditCompleted()
    }

    fun setListener(listenerDetails: EditImageDetailsFragmentListener) {
        this.listener = listenerDetails
    }

    fun resetControls() {
        _binding?.seekbarBrightness?.progress = 100
        _binding?.seekbarContrast?.progress = 0
        _binding?.seekbarSaturation?.progress = 10
    }

    interface EditImageDetailsFragmentListener {
        fun onBrightnessChanged(brightness: Int)

        fun onSaturationChanged(saturation: Float)

        fun onContrastChanged(contrast: Float)

        fun onEditStarted()

        fun onEditCompleted()
    }
}