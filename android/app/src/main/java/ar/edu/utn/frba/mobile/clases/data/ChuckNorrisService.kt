package ar.edu.utn.frba.mobile.clases.data

import retrofit2.http.GET

//URL para obtener un chiste de Chuck Norris Random
//https://api.chucknorris.io/jokes/random

data class ChuckNorrisJoke(
    val value: String
)

interface ChuckNorrisService {
    @GET("jokes/random")
    suspend fun randomJoke(): ChuckNorrisJoke
}