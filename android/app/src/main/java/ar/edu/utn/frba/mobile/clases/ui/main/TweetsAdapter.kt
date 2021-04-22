package ar.edu.utn.frba.mobile.clases.ui.main


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ar.edu.utn.frba.mobile.clases.R

class TweetsAdapter(private val listener: MainFragment.OnFragmentInteractionListener?): RecyclerView.Adapter<TweetsAdapter.ViewHolder>() {

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) R.layout.item_post
        else {
            val hasPicture = position % 2 == 0 // hardcodeado, debería salir del item a mostrar
            if (hasPicture) R.layout.item_image
            else R.layout.item_simple
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(viewType, parent, false)

        when (viewType) {
            R.layout.item_post -> {
                view.findViewById<Button>(R.id.postButton).setOnClickListener {
                    listener?.showFragment(StatusUpdateFragment())
                }
            }
        }

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            R.layout.item_simple, R.layout.item_image -> {
                val itemIndex = position - 1 // el primer item es el encabezado
                // todo hardcodeado, debería salir del item
                holder.itemView.findViewById<TextView>(R.id.nameText).text = "User ${itemIndex}"
                holder.itemView.findViewById<ImageView>(R.id.certifiedIcon).visibility = if (itemIndex % 3 == 0) View.VISIBLE else View.GONE
                holder.itemView.findViewById<TextView>(R.id.usernameText).text = "@username${itemIndex}"
                holder.itemView.findViewById<TextView>(R.id.tweetContent).text =
                    "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed eiusmod tempor incidunt ut labore et dolore magna aliqua."
                holder.itemView.findViewById<TextView>(R.id.commentCount).text = "${itemIndex}"
                holder.itemView.findViewById<TextView>(R.id.retweetCount).text = "${itemIndex * 2}"
                holder.itemView.findViewById<TextView>(R.id.likeCount).text = "${itemIndex * 3}"
                // solo si hay imagen usar holder.itemView.image
            }
            else -> {}
        }
    }

    override fun getItemCount(): Int = 100 + 1 // el primer item es el encabezado

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    }
}