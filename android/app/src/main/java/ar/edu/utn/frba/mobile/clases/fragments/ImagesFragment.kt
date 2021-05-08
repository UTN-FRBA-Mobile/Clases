package ar.edu.utn.frba.mobile.clases.fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.GridLayoutManager
import ar.edu.utn.frba.mobile.clases.R
import ar.edu.utn.frba.mobile.clases.adapters.ImagesAdapter
import ar.edu.utn.frba.mobile.clases.databinding.FragmentImagesBinding
import ar.edu.utn.frba.mobile.clases.ui.main.MainViewModel

class ImagesFragment : Fragment() {

    private var _binding: FragmentImagesBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var viewModel: MainViewModel
    private var listener: ImagesFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        viewModel = ViewModelProvider(this).get()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentImagesBinding.inflate(inflater, container, false)
        with(binding.recyclerView) {
            layoutManager = GridLayoutManager(context, viewModel.numberOfColumns.value ?: 1)
            adapter = ImagesAdapter()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.addButton.setOnClickListener {
            launchImagePicker()
        }
        viewModel.numberOfColumns.observe(viewLifecycleOwner) { numberOfColumns ->
            binding.recyclerView.post {
                androidx.transition.TransitionManager.beginDelayedTransition(binding.recyclerView)
                with (binding.recyclerView.layoutManager as GridLayoutManager) {
                    spanCount = numberOfColumns
                }
            }
        }
        viewModel.images.observe(viewLifecycleOwner) { images ->
            binding.imagesListTitle.visibility = if (images.isEmpty()) View.VISIBLE else View.GONE
            (binding.recyclerView.adapter as ImagesAdapter).images = images
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.reloadImages()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ImagesFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement ImagesFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_main, menu)
        val actionToggleViewGrid = menu.findItem(R.id.action_toggle_view_grid)
        val actionToggleViewList = menu.findItem(R.id.action_toggle_view_list)
        viewModel.showAsGrid.observe(viewLifecycleOwner) { showAsGrid ->
            actionToggleViewGrid.isVisible = !showAsGrid
            actionToggleViewList.isVisible = showAsGrid
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_toggle_view_list, R.id.action_toggle_view_grid -> {
                viewModel.toggleShowAsGrid()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private val addImageLaucher = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) {
        if (it != null) {
            launchImageEdition(it)
        }
    }
    private fun launchImagePicker() {
        addImageLaucher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private fun launchImageEdition(imageUri: Uri) {
        listener!!.showFragment(EditImageFragment.newInstance(imageUri))
    }

    interface ImagesFragmentInteractionListener {
        fun showFragment(fragment: Fragment)
    }

    companion object {
        @JvmStatic
        fun newInstance() = ImagesFragment()
    }
}
