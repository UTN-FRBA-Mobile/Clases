package ar.edu.utn.frba.mobile.clases.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import ar.edu.utn.frba.mobile.clases.R
import ar.edu.utn.frba.mobile.clases.ui.main.StoredImage
import com.squareup.picasso.Picasso
import kotlin.properties.Delegates

class ImagesAdapter() : RecyclerView.Adapter<ImagesAdapter.ViewHolder>() {

    var images by Delegates.observable(emptyList<StoredImage>()) { _, _, _ ->
        notifyDataSetChanged()
    }

    private val mOnClickListener: View.OnClickListener = View.OnClickListener {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_image_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = images.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val imageUri = images[position].uri

        Picasso.get().load(imageUri).into(holder.image)

        with(holder.mView) {
            tag = imageUri.toString()
            setOnClickListener(mOnClickListener)
        }
    }

    class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val image: ImageView = mView.findViewById(R.id.edited_image)
    }
}
