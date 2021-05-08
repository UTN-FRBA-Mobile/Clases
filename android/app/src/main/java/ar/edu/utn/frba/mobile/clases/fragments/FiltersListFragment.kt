package ar.edu.utn.frba.mobile.clases.fragments

import android.graphics.Bitmap
import android.graphics.Rect
import android.net.Uri
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ar.edu.utn.frba.mobile.clases.adapters.ThumbnailsAdapter
import ar.edu.utn.frba.mobile.clases.databinding.FragmentFiltersListBinding
import ar.edu.utn.frba.mobile.clases.utils.filters.FilterPack
import ar.edu.utn.frba.mobile.clases.utils.filters.ThumbnailItem
import ar.edu.utn.frba.mobile.clases.utils.filters.ThumbnailsManager
import ar.edu.utn.frba.mobile.clases.utils.storage.fileSystem.ExternalContent
import com.zomato.photofilters.imageprocessors.Filter
import java.util.*

class FiltersListFragment : Fragment(), ThumbnailsAdapter.ThumbnailsAdapterListener {

    private var _binding: FragmentFiltersListBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var mAdapter: ThumbnailsAdapter
    private lateinit var thumbnailItemList: MutableList<ThumbnailItem>
    private lateinit var imageUri: Uri

    private var listener: FiltersListFragmentListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            imageUri = Uri.parse(it.getString(IMAGE_PATH))
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentFiltersListBinding.inflate(inflater, container, false)

        thumbnailItemList = ArrayList()
        mAdapter = ThumbnailsAdapter(requireActivity(), thumbnailItemList, this)

        val mLayoutManager = GridLayoutManager(activity, 5, GridLayoutManager.VERTICAL, false)
        val space = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8f, resources.displayMetrics).toInt()

        binding.recyclerView.layoutManager = mLayoutManager
        binding.recyclerView.itemAnimator = DefaultItemAnimator()
        binding.recyclerView.addItemDecoration(SpacesItemDecoration(space))
        binding.recyclerView.adapter = mAdapter

        prepareThumbnail(null)

        return binding.root
    }

    override fun onFilterSelected(filter: Filter) {
        if (listener != null)
            listener!!.onFilterSelected(filter)
    }

    fun setListener(listener: FiltersListFragmentListener) {
        this.listener = listener
    }

    private fun prepareThumbnail(bitmap: Bitmap?) {
        val r = Runnable {
            val thumbImage: Bitmap = (if (bitmap == null) {
                ExternalContent.getBitmapFromGallery(requireActivity(), imageUri, 100, 100)
            } else {
                Bitmap.createScaledBitmap(bitmap, 100, 100, false)
            }) ?: return@Runnable

            ThumbnailsManager.clearThumbs()
            thumbnailItemList.clear()

            val thumbnailItem = ThumbnailItem()
            thumbnailItem.image = thumbImage
            thumbnailItem.filterName = "Normal"
            ThumbnailsManager.addThumb(thumbnailItem)

            val filters = FilterPack.getFilterPack(activity)

            for (filter in filters) {
                val tI = ThumbnailItem()
                tI.image = thumbImage
                tI.filter = filter
                ThumbnailsManager.addThumb(tI)
            }

            thumbnailItemList.addAll(ThumbnailsManager.processThumbs(activity))

            requireActivity().runOnUiThread { mAdapter.notifyItemRangeInserted(0, thumbnailItemList.size - 1) }
        }

        Thread(r).start()
    }

    interface FiltersListFragmentListener {
        fun onFilterSelected(filter: Filter)
    }

    companion object {
        @JvmStatic
        fun newInstance(imageUri: Uri) =
            FiltersListFragment().apply {
                arguments = Bundle().apply {
                    putString(IMAGE_PATH, imageUri.toString())
                }
            }
    }
}

class SpacesItemDecoration(private val space: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        if (parent.getChildAdapterPosition(view) == state.itemCount - 1) {
            outRect.left = space
            outRect.right = 0
        } else {
            outRect.right = space
            outRect.left = 0
        }
    }
}
