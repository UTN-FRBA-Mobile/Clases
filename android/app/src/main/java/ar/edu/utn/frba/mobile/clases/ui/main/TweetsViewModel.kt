package ar.edu.utn.frba.mobile.clases.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.utn.frba.mobile.clases.ui.model.Tweet
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Response
import java.util.Date

class TweetsViewModel() : ViewModel() {

    // LiveData para la lista de tweets
    private val tweetsMutableLiveData: MutableLiveData<List<Tweet>> = MutableLiveData(listOf())
    val tweetsLiveData: LiveData<List<Tweet>> = tweetsMutableLiveData

    // Indicador de carga
    private val isLoadingMutableLiveData: MutableLiveData<Boolean> = MutableLiveData(false)
    val isLoadingLiveData: LiveData<Boolean> = isLoadingMutableLiveData

    // Indicador de error
    private val isErrorMutableLiveData: MutableLiveData<Boolean> = MutableLiveData(false)
    val isErrorLiveData: LiveData<Boolean> = isErrorMutableLiveData

    // el timestamp del tweet más reciente
    private val latestTs: Long? get() = tweetsLiveData.value?.firstOrNull()?.ts
    // el timestamp del tweet más antiguo
    private val oldestTs: Long? get() = tweetsLiveData.value?.lastOrNull()?.ts
    private var hasMore = true

    fun loadNewTweets() {
        // pegarle al servicio con el oldest=this.latestTs
        loadTweets(oldest = latestTs, onTop = true)
    }

    fun loadOlderTweets() {
        // pegarle al servicio con el latest=this.oldestTs
        loadTweets(latest = oldestTs, onTop = false)
    }

    private fun loadTweets(oldest: Long? = null, latest: Long? = null, onTop: Boolean) {
        if (isLoadingMutableLiveData.value == true || (!onTop && !hasMore)) {
            return
        }
        // Indicamos que estamos cargando
        isLoadingMutableLiveData.value = true
        viewModelScope.launch(Dispatchers.Main) {
            try {
                val response = mockedServiceGetTweets(
                    oldest = oldest,
                    latest = latest
                )
                if (response.isSuccessful) {
                    // Obtener la lista de tweets desde la respuesta
                    val newTweets = response.body() ?: emptyList()
                    val oldTweets = tweetsLiveData.value ?: emptyList()
                    val tweets = if (onTop) newTweets.plus(oldTweets) else oldTweets.plus(newTweets)
                    if (!onTop && newTweets.size == 0) {
                        // Llegamos al final de la lista
                        hasMore = false
                    }

                    // Agregar los tweets a la lista existente
                    tweetsMutableLiveData.postValue(tweets)
                } else {
                    // Mostrar indicador de error
                    isErrorMutableLiveData.postValue(true)
                }
            } catch (e: Exception) {
                // Mostrar indicador de error
                isErrorMutableLiveData.postValue(true)
                // la siguiente línea les va a ayudar a ver si hubo un problema definiendo la interfaz del servicio. Si nunca trae nada van a ver en el logcat cuál fue el problema.
                Log.e("Tweets", e.message, e)
            }
            // Ocultar indicador de carga
            isLoadingMutableLiveData.postValue(false)
        }
    }
}

private suspend fun mockedServiceGetTweets(oldest: Long?, latest: Long?): Response<List<Tweet>> {
    delay(2000)
    val firstTs = oldest?.minus(10) ?: latest?.plus(1) ?: Date().time
    val lastTs = firstTs + 9
    return Response.success(firstTs.rangeTo(lastTs).map {
        Tweet(ts = it, profilePic = "https://upload.wikimedia.org/wikipedia/commons/8/89/Portrait_Placeholder.png", name = "Username", certified = it % 2 == 0L, username = "@username", content = "This is a tweet", image = null, commentCount = 123, retweetCount = 234, likeCount = it % 1000)
    })
}