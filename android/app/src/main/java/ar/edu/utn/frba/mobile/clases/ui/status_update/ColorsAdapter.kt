package ar.edu.utn.frba.mobile.clases.ui.status_update

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ar.edu.utn.frba.mobile.clases.R

class ColorsAdapter(val listener: ItemClickListener): RecyclerView.Adapter<ColorsAdapter.ViewHolder>() {

    override fun getItemCount(): Int {
        return 409
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_color, parent, false), listener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.backgroundDrawable.colors = PostTheme.getBackgroundColors(position) // para crear un gradiente
    }

    class ViewHolder(itemView: View, listener: ItemClickListener) : RecyclerView.ViewHolder(itemView) {

        val backgroundDrawable = GradientDrawable().apply {
            val metrics = itemView.context.resources.displayMetrics
            cornerRadius = metrics.density * 8 // 8dp
            setStroke(1, Color.GRAY)
            orientation = GradientDrawable.Orientation.TL_BR
            itemView.background = this
        }
        init {
            itemView.setOnClickListener {
                listener.onItemClick(bindingAdapterPosition)
            }
        }
    }

    interface ItemClickListener {
        fun onItemClick(position: Int)
    }
}