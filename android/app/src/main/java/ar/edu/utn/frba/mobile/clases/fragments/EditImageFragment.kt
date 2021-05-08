package ar.edu.utn.frba.mobile.clases.fragments

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.*
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import ar.edu.utn.frba.mobile.clases.R
import ar.edu.utn.frba.mobile.clases.adapters.ViewPagerAdapter
import ar.edu.utn.frba.mobile.clases.databinding.FragmentEditImageBinding
import ar.edu.utn.frba.mobile.clases.utils.storage.fileSystem.ExternalContent
import ar.edu.utn.frba.mobile.clases.utils.storage.fileSystem.InternalStorage
import com.google.android.material.tabs.TabLayoutMediator
import com.zomato.photofilters.imageprocessors.Filter
import com.zomato.photofilters.imageprocessors.subfilters.BrightnessSubFilter
import com.zomato.photofilters.imageprocessors.subfilters.ContrastSubFilter
import com.zomato.photofilters.imageprocessors.subfilters.SaturationSubFilter
import java.util.*

const val IMAGE_PATH = "IMAGE_PATH"

class EditImageFragment : Fragment(), FiltersListFragment.FiltersListFragmentListener, EditImageDetailsFragment.EditImageDetailsFragmentListener {

    private var _binding: FragmentEditImageBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    init {
        System.loadLibrary("NativeImageProcessor")
    }

    private var listener: OnFragmentInteractionListener? = null
    private var brightnessFinal = 0
    private var saturationFinal = 1.0f
    private var contrastFinal = 1.0f

    private lateinit var imageUri: Uri
    private lateinit var originalImage: Bitmap
    private var originalFilter = Filter()
    private lateinit var prefilteredImage: Bitmap
    private lateinit var filteredImage: Bitmap
    private var editImageDetailsFragment: EditImageDetailsFragment? = null
    private lateinit var processHandler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        arguments?.let {
            imageUri = Uri.parse(it.getString(IMAGE_PATH))
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentEditImageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        originalImage = ExternalContent.getBitmapFromGallery(requireContext(), imageUri, 100, 100)
        filteredImage = originalImage

        binding.imageToEdit.setImageBitmap(originalImage)

        setupViewPager(binding.viewpager)
        val tabTitles = listOf(
            getString(R.string.filters),
            getString(R.string.calibration)
        )
        TabLayoutMediator(binding.tabs, binding.viewpager) { tab, position ->
            tab.text = tabTitles[position]
        }.attach()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnFragmentInteractionListener")
        }
        Thread {
            Looper.prepare()
            processHandler = Looper.myLooper()?.let { Handler(it) }!!
            Looper.loop()
        }.start()
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
        processHandler.post {
            Looper.myLooper()!!.quit()
        }
    }

    override fun onFilterSelected(filter: Filter) {
        resetControls()
        originalFilter = filter
        updateFilter()
    }

    private fun updateFilter() {
        processHandler.removeCallbacksAndMessages(null)
        processHandler.post {
            prefilteredImage = originalFilter.processFilter(originalImage.copy(Bitmap.Config.ARGB_8888, true))
            filteredImage = generatedFilter.processFilter(prefilteredImage.copy(Bitmap.Config.ARGB_8888, true))
            activity?.runOnUiThread { binding.imageToEdit.setImageBitmap(filteredImage) }
        }
    }

    private fun quickFilter() {
        processHandler.removeCallbacksAndMessages(null)
        processHandler.post {
            val newImage = generatedFilter.processFilter(prefilteredImage.copy(Bitmap.Config.ARGB_8888, true))
            activity?.runOnUiThread { binding.imageToEdit.setImageBitmap(newImage) }
        }
    }

    override fun onContrastChanged(contrast: Float) {
        contrastFinal = contrast
        quickFilter()
    }

    override fun onEditStarted() {
    }

    private val generatedFilter
        get() = Filter().apply {
            addSubFilter(BrightnessSubFilter(brightnessFinal))
            addSubFilter(ContrastSubFilter(contrastFinal))
            addSubFilter(SaturationSubFilter(saturationFinal))
        }

    override fun onEditCompleted() {
        updateFilter()
    }

    override fun onSaturationChanged(saturation: Float) {
        saturationFinal = saturation
        quickFilter()
    }

    override fun onBrightnessChanged(brightness: Int) {
        brightnessFinal = brightness
        quickFilter()
    }

    private fun setupViewPager(viewPager: ViewPager2) {
        val prevFragments = childFragmentManager.fragments
        val filtersListFragment = prevFragments.firstOrNull { it is FiltersListFragment } ?: FiltersListFragment.newInstance(imageUri).also {
            it.setListener(this)
        }
        editImageDetailsFragment = prevFragments.firstOrNull { it is EditImageDetailsFragment } as? EditImageDetailsFragment ?: EditImageDetailsFragment().also {
            it.setListener(this)
        }
        val fragments = listOf(filtersListFragment, editImageDetailsFragment!!)
        viewPager.adapter = ViewPagerAdapter(fragments, this)
    }

    private fun resetControls() {
        editImageDetailsFragment?.resetControls()
        brightnessFinal = 0
        saturationFinal = 1.0f
        contrastFinal = 1.0f
    }

    private fun save() {
        InternalStorage.saveFile(requireContext(), filteredImage, Calendar.getInstance().time.toString())
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_edit, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_cancel -> {
                listener!!.popFragment()
                return true
            }
            R.id.action_save -> {
                save()
                listener!!.popFragment()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    interface OnFragmentInteractionListener {
        fun popFragment()
    }

    companion object {
        @JvmStatic
        fun newInstance(imageUri: Uri) =
            EditImageFragment().apply {
                arguments = Bundle().apply {
                    putString(IMAGE_PATH, imageUri.toString())
                }
            }
    }
}
