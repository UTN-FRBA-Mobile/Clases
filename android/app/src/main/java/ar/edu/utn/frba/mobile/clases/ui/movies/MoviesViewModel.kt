package ar.edu.utn.frba.mobile.clases.ui.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import ar.edu.utn.frba.mobile.clases.core.MovieApi
import ar.edu.utn.frba.mobile.clases.core.RealMovieApi
import kotlinx.coroutines.Dispatchers

class MoviesViewModel(private val movieApi: MovieApi = RealMovieApi()): ViewModel() {

    var categories = liveData(Dispatchers.IO) {
        emit(null)
        emit(movieApi.getMovies())
    }
}