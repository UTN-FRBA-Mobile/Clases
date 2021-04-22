package ar.edu.utn.frba.mobile.clases.ui.main

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ar.edu.utn.frba.mobile.clases.R

class ColorsAdapter: RecyclerView.Adapter<ColorsAdapter.ViewHolder>() {

    override fun getItemCount(): Int {
        return 409
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_color, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.backgroundDrawable.setColor(Color.BLACK + position * 0xa0ff) // para crear un gradiente
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val backgroundDrawable = GradientDrawable().apply {
            val metrics = itemView.context.resources.displayMetrics
            cornerRadius = metrics.density * 8 // 8dp
            setStroke(1, Color.GRAY)
            itemView.background = this
        }
    }
}