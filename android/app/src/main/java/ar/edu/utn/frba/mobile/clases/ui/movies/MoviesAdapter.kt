package ar.edu.utn.frba.mobile.clases.ui.movies

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ar.edu.utn.frba.mobile.clases.core.Movie
import ar.edu.utn.frba.mobile.clases.core.MovieCategory
import ar.edu.utn.frba.mobile.clases.databinding.ViewListitemCategoryBinding
import ar.edu.utn.frba.mobile.clases.databinding.ViewListitemMovieBinding

class MoviesAdapter :
    RecyclerView.Adapter<MoviesAdapter.MyViewHolder>() {

    object ViewType {
        const val category: Int = 1
        const val movie: Int = 2
    }

    private var dataset: List<Row> = listOf()

    sealed class Row
    class CategoryRow(val category: MovieCategory) : Row()
    class MovieRow(val movie: Movie) : Row()

    fun setMovies(categories: List<MovieCategory>) {
        dataset = categories.flatMap { category ->
            listOf(CategoryRow(category)) + category.movies.map { MovieRow(it) }
        }
        notifyDataSetChanged()
    }

    override fun getItemCount() = dataset.size

    override fun getItemViewType(position: Int): Int {
        return when (dataset[position]) {
        is CategoryRow -> ViewType.category
        is MovieRow -> ViewType.movie
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            ViewType.category -> CategoryViewHolder(ViewListitemCategoryBinding.inflate(inflater, parent, false))
            ViewType.movie -> MovieViewHolder(ViewListitemMovieBinding.inflate(inflater, parent, false))
            else -> null!! // nunca pasa
        }
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(dataset[position])
    }

    abstract class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        abstract fun bind(row: Row)
    }
    class CategoryViewHolder(private val binding: ViewListitemCategoryBinding) : MyViewHolder(binding.root) {
        override fun bind(row: Row) {
            if (row is CategoryRow) {
                binding.categoryName.text = row.category.name
            }
        }
    }
    class MovieViewHolder(private val binding: ViewListitemMovieBinding) : MyViewHolder(binding.root) {
        override fun bind(row: Row) {
            if (row is MovieRow) {
                binding.movieName.text = row.movie.name
                binding.moviePoster.setImageResource(row.movie.moviePoster)
            }
        }
    }
}