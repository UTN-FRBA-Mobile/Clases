package ar.edu.utn.frba.mobile.clases.ui.coroutines

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.utn.frba.mobile.clases.data.ChuckNorrisService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.math.atan
import kotlin.math.tan
import kotlin.random.Random


class CourotinesViewModel: ViewModel() {

    //region sample1_01
    /**
     * La corremos en el Thread Principal (Es muy pesada y bloquea el Thread Principal, el botón
     * de incrementar no procesa hasta se libera el thread principal)
     */
    fun sample1_01() {
        Log.d("TEST", "Empieza Tarea Pesada (Thread: ${Thread.currentThread().name})")

        for (i in 1..10_000_000) {
            tan(atan(tan(Random.nextDouble())))
        }

        //Es Similar a...
        //Thread.sleep(5_000)

        Log.d("TEST", "Termina Tarea Pesada Tarea Pesada (Thread: ${Thread.currentThread().name})")
    }
    //endregion

    //region sample1_02
    /**
     * Lo corremos en una corrutina en el dispatcher de IO ()
     */
    fun sample1_02() {
        val scope = CoroutineScope(Dispatchers.IO)

        Log.d("TEST", "Antes Corrutina")

        scope.launch {
            Log.d("TEST", "Empieza Tarea Pesada (Thread: ${Thread.currentThread().name})")
            Thread.sleep(5_000)
            Log.d("TEST", "Termina Tarea Pesada (Thread: ${Thread.currentThread().name})")
        }

        Log.d("TEST", "Despues Corrutina")
    }

    //endregion

    //region sample1_03
    /**
     * Corremos en el Dispatchrs.IO llamando a dos funciones suspend.
     */
    fun sample1_03() {
        val scope = CoroutineScope(Dispatchers.IO)
        Log.d("TEST", "Antes Corrutina")

        scope.launch {
            Log.d("TEST", "Empieza Tarea Pesada 1 (Thread: ${Thread.currentThread().name})")
            suspendFor5Seconds()
            Log.d("TEST", "Termina Tarea Pesada 1 (Thread: ${Thread.currentThread().name})")
        }

        scope.launch {
            Log.d("TEST", "Empieza Tarea Pesada 2 (Thread: ${Thread.currentThread().name})")
            suspendFor4Seconds()
            Log.d("TEST", "Termina Tarea Pesada 2 (Thread: ${Thread.currentThread().name})")
        }

        Log.d("TEST", "Despues Corrutina")
    }

    private suspend fun suspendFor5Seconds() {
        delay(5_000)
    }

    private suspend fun suspendFor4Seconds() {
        delay(4_000)
    }
    //endregion

    //region sample1_04
    /**
     * Utilizamos el viewModelScope para evitarnos tener que hacerle .cancel() al scope
     * en el onCleared del viewModel. (Se bloquea el main thread porque el viewModelScope tiene Dispatchers.Main)
     */
    fun sample1_04() {
        Log.d("TEST", "Antes Corrutina")

        viewModelScope.launch(Dispatchers.IO) {
            Log.d("TEST", "Empieza Tarea Pesada 1 (Thread: ${Thread.currentThread().name})")
            for (i in 1..10_000_000) {
                tan(atan(tan(Random.nextDouble())))
            }
            Log.d("TEST", "Termina Tarea Pesada 1 (Thread: ${Thread.currentThread().name})")
        }

        viewModelScope.launch(Dispatchers.IO) {
            Log.d("TEST", "Empieza Tarea Pesada 2 (Thread: ${Thread.currentThread().name})")
            for (i in 1..10_000_000) {
                tan(atan(tan(Random.nextDouble())))
            }
            Log.d("TEST", "Termina Tarea Pesada 2 (Thread: ${Thread.currentThread().name})")
        }

        Log.d("TEST", "Despues Corrutina")
    }
    //endregion

    //region sample1_05
    /**
     * Utilizamos el viewModelScope para evitarnos tener que hacerle .cancel() al scope
     * en el onCleared del viewModel.
     *
     * Utilizamos el Dispatchers.IO de dos formas distintas (en el primer caso lo pasamos
     * como parametro al launch, en el segundo caso usamos withContext para ser main-safe)
     *
     * Hacemos el codigo cooperativo para la cancelación
     */
    fun sample1_05() {
        Log.d("TEST", "Antes Corrutina")

        viewModelScope.launch(Dispatchers.IO) {
            Log.d("TEST", "Empieza Tarea Pesada 1 (Thread: ${Thread.currentThread().name})")

            for (i in 1..10_000_000) {
                if (isActive) {
                    tan(atan(tan(Random.nextDouble())))
                } else {
                    break
                }
            }

            if (!isActive)
                Log.d("TEST", "Tarea 1: Me cancelaron :( (Thread: ${Thread.currentThread().name})")
            else
                Log.d("TEST", "Termina Tarea Pesada 1 (Thread: ${Thread.currentThread().name})")
        }

        viewModelScope.launch {
            Log.d("TEST", "Empieza Tarea Pesada 2 (Thread: ${Thread.currentThread().name})")
            withContext(Dispatchers.IO) {
                Log.d("TEST", "Corriendo Tarea pesada 2 (Thread: ${Thread.currentThread().name})")
                for (i in 1..10_000_000) {
                    if (isActive) {
                        tan(atan(tan(Random.nextDouble())))
                    } else {
                        break
                    }
                }
                if (!isActive)
                    Log.d("TEST", "Tarea 2: Me cancelaron :( (Thread: ${Thread.currentThread().name})")
                else
                    Log.d("TEST", "Termina Tarea Pesada 2 (Thread: ${Thread.currentThread().name})")
            }

        }

        Log.d("TEST", "Despues Corrutina")
    }
    //endregion

    //region sample1_06
    /**
     * Tenemos dos funciones suspend que devuelven 2 valores y los queremos sumar.
     * Corremos ambas funciones suspend en paralelo (con async). Esperamos los resultados con await
     */
    fun sample1_06() {
        viewModelScope.launch {
            Log.d("TEST", "Comienzo.")

            val primerValor = async { delayAndReturn10() }
            val segundoValor = async { delayAndReturn15() }

            val resultado = primerValor.await() + segundoValor.await()

            Log.d("TEST", "El resultado es $resultado")

        }
    }

    private suspend fun delayAndReturn15(): Int {
        delay(4_000)
        return 15
    }

    private suspend fun delayAndReturn10(): Int {
        delay(3_000)
        return 10
    }
    //endregion

    //region sample1_07
    /**
     * Las corrutinas Son livianas y podemos crear muchas!
     */
    fun sample1_07() {
        repeat(50_000) {
            viewModelScope.launch {
                delay(5_000)
                Log.d("TEST", "fin $it")
            }
        }
        //Haciendo lo mismos con Threads nos quedamos sin memoria....
        /*
        repeat(50_000) {
            thread {
                Thread.sleep(5_000)
                Log.d("TEST", "fin $it")
            }
        }
        */
    }
    //endregion


    //region sample2_01
    /**
     * Comunicamos Productores y Consumidores a traves de un Channel
     */
    fun sample2_01() {
        viewModelScope.launch {
            launch {
                Log.d("TEST", "Poniendo A1")
                channel.send("A1")
                Log.d("TEST", "Poniendo A2")
                channel.send("A2")

                Log.d("TEST", "Listo con los A")
            }

            launch {
                repeat(2) {
                    val x = channel.receive()
                    Log.d("TEST", "Saque $x")
                }
            }
        }
    }

    private val channel = Channel<String>() //Channel Default: Rendezvous channel
    //endregion

    //region sample2_02
    /**
     * Supongamos que queremos generar 3 valores asincronicamente y procesarlos a
     * medida que se van generado
     */
    fun sample2_02() {
        viewModelScope.launch {
            Log.d("TEST", "Llamando a Calcular Valores")

            calculateThreeValuesWithFlow().collect {
                Log.d("TEST", "valor $it")
            }
        }
    }

    private suspend fun calculateThreeValues() : List<Int> {
        val retList = mutableListOf<Int>()
        delay(1000)
        retList.add(2)
        delay(1000)
        retList.add(3)
        delay(1000)
        retList.add(4)
        return retList
    }

    private fun calculateThreeValuesWithFlow() : Flow<Int> = flow {
        Log.d("TEST", "Calculando Valores")

        delay(1_000)
        emit(2)

        delay(1_000)
        emit(3)

        delay(1_000)
        emit(4)
    }
    //endregion


    //region sample3_01
    /**
     * Queremos utilizar un flow para actualizar la vista.
     * En una corrutina llamamos a la API par obtener un chiste random 3 veces (separadas
     * con un delay de 3 segundo entre ellas).
     * Por cada chiste, ponemos el valor en el StateFlow
     * Al StateFlow se le hace un `collect` desde la vista.
     */
    fun sample3_01() {
        viewModelScope.launch {
            var joke = service.randomJoke()
            _jokeFlow.update {
                it.copy(joke = joke.value)
            }
            delay(3_000)

            joke = service.randomJoke()
            _jokeFlow.update {
                it.copy(joke = joke.value)
            }
            delay(3_000)

            joke = service.randomJoke()
            _jokeFlow.update {
                it.copy(joke = joke.value)
            }
        }
    }


    private val service = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl("https://api.chucknorris.io/")
        .build()
        .create(ChuckNorrisService::class.java)


    data class UIState(
        val joke: String? = null
    )

    private val _jokeFlow = MutableStateFlow<UIState>(UIState())
    val jokeFlow = _jokeFlow.asStateFlow()
    //endregion

    override fun onCleared() {
        super.onCleared()
        Log.d("TEST ", "onCleared")
    }
}