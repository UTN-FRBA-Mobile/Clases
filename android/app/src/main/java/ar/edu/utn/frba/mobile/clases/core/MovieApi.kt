package ar.edu.utn.frba.mobile.clases.core

import ar.edu.utn.frba.mobile.clases.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface MovieApi {
    suspend fun getMovies(): List<MovieCategory>
}
class RealMovieApi: MovieApi {
    override suspend fun getMovies(): List<MovieCategory> {
        withContext(Dispatchers.IO) {
            Thread.sleep(2000)
        }
        return MockMovieApi.sampleMovies()
    }
}

class MockMovieApi: MovieApi {

    override suspend fun getMovies(): List<MovieCategory> {
        return sampleMovies()
    }

    companion object {
        fun sampleMovies(): List<MovieCategory> {
            return (0..10).map { categoryIndex ->
                MovieCategory(
                    name = "Category $categoryIndex",
                    movies = (0..5).map { index ->
                        Movie(
                            name = "Movie $index",
                            summary = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
                            moviePoster = if (index % 2 == 0) R.drawable.movie_icon_1 else R.drawable.movie_icon_2
                        )
                    })
            }
        }
    }
}
