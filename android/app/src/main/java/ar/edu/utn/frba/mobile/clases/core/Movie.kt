package ar.edu.utn.frba.mobile.clases.core

import androidx.annotation.DrawableRes

data class MovieCategory(val name: String, val movies: List<Movie>)

data class Movie(val name: String, val summary: String, @DrawableRes val moviePoster: Int)
