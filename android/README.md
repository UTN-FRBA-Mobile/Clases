# Clases
## Práctica Conectividad

Conectar la aplicación con un servicio REST simple.

* Agregar al `build.gradle` de la aplicación las dependencias necesarias para usar RetroFit y Picasso:
```
    implementation 'com.squareup.retrofit2:retrofit:2.5.0'
    implementation 'com.squareup.retrofit2:converter-gson:2.5.0'
    implementation 'com.squareup.okhttp3:logging-interceptor:3.9.0'
    implementation "io.coil-kt:coil-compose:2.3.0" // image loader para compose
    implementation 'com.squareup.picasso:picasso:2.71828' // image loader para views
```
(ya fueron agregadas para que levanten más rápido el entorno)

* Agregar en `AndroidManifest.xml` el permiso necesario para acceder a Internet (en `<manifest>`, fuera de `<application>`):
```
    <uses-permission android:name="android.permission.INTERNET" />
```

* Diseñar el modelo para leer el resultado del servicio con este formato:

```json
{
  "tweets": [
      {
          "profilePic": "url de imagen",
          "name": "nombre",
          "certified": true,
          "username": "@username",
          "content": "el tweet",
          "image": "url de imagen. opcional (puede no venir)",
          "commentCount": número,
          "retweetCount": número,
          "likeCount": número
      }
  ]
}
```

Pueden usar `data class` de Kotlin.
Para poder preparar la vista la vista, la data class que representa a un tweet ya está diseñado y se llama `Tweet`
pero falta agregar la data class que modela la respuesta entera.

* Armar la interfaz para hacer la llamada con RetroFit.

En este caso tenemos un método GET con dos parámetros: oldest y latest,
que sirven para paginar los resultados.

Hay una breve explicación de RetroFit en su página: https://square.github.io/retrofit/
(con leer la introducción alcanza) pero está hecho para el modelo de callbacks.
Para usar corrutinas solo hay que agregar el `suspend` al comienzo de la `fun` y
cambiar el tipo de retorno `Call` por `Result`.

El método `/list` quedaría entonces algo así:
```
    @GET("/list")
    suspend fun list(@Query("oldest") oldest: Long?, @Query("latest") latest: Long?): Response<TweetsResponse>
```

* Crear una instancia de RetroFit que implemente la interfaz diseñada con:

```kotlin
val service = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create()) // Para parsear automágicamente el json
    .baseUrl("https://us-central1-clases-854bb.cloudfunctions.net/") // la URL
    .build()
    .create(TweetsService::class.java) // la interfaz que diseñaron antes
```

Esto pueden meterlo en alguna clase, por ejemplo `RetrofitClient`, sea como singleton
o en el companion object, para accederlo fácilmente desde donde lo necesiten.

* Reemplazar la llamada mockeada al servicio en `TweetsViewModel`.

```kotlin
// ...
val response = service.list(
  oldest = oldest,
  latest = latest
)
if (response.isSuccessful) {
    val newTweets = response.body()?.tweets ?: emptyList()
    // ...
```
Acá `service` sería el servicio que implementaron antes. Pueden usarlo directamente o,
mejor, agregarlo como parámetro opcional al constructor del ViewModel, poniendo el
valor por omisión a la instancia que crearon antes.

* Actualizar TweetsAdapter para contener una lista de tweets y mostrar la información de cada tweet.

* Problemas comunes y sus soluciones:
  - Si después de hacer todo no les trae nada, fíjense en el logcat qué pasó:
    - Si es un problema al definir la interfaz del servicio, ahí les va a decir qué pasó.
    - Si al hacer el GET a la API encuentran un error del tipo ```java.net.SocketException: socket failed: EPERM (Operation not permitted)```, revisen no haberse olvidado de agregar el permiso y si sigue pasando desinstalen la app del emulador o del dispositivo donde están probando y vuelvan a instalarla/correrla. De esta forma el posible problema de permisos que puedan estar teniendo se va a solucionar.
