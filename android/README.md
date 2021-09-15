# NAVIGATION COMPONENT

## UN POCO DE TEORÍA

### Definiciones

En Google I/O del 2018 se anunció el Navigation Component. Es un conjunto de librerías, plugins y herramientas, que simplifican y unifican la navegación en Android.
Con navegación nos referimos a como es el flujo dentro de la aplicación, la interacción con el usuario y cómo va navegando los distintos contenidos de la misma.
La idea es lograr una Experiencia de Usuario consistente e intuitiva.

El uso de este componente está basado en crear un grafo en el cuál están conectados todos los Activities y Fragments, y sus relaciones.

Tiene 3 partes principales:

**Navigation Graph**
* Es un xml que tiene toda la información sobre la navegación.
* Está conformado por Acciones (que conectan destinos) y Destinos (cualquier lugar al que puedo ir desde mi app. Generalmente son Fragments que representan una pantalla en particular.)

**NavHostFragment**
* Es un contenedor (layout) vacío en el que se muestran los Destinos del Navigation Graph.
* Swapea entre los distintos fragments del Navigation Graph.

**NavController**
* Es un Fragment.
* Gestiona la navegación dentro del NavHost.
* Va cambiando los fragments a medida que el usuario se mueve por la app.
* * Por ejemplo:  boton.setOnClickListener {v -> Navigation.findNavController(v).navigate(R.id.action_mainFragment_to_detailFragment) }


## PASO A PASO

**1) CONFIGURACIONES DE LOS ARCHIVOS BUILD GRADLE.**

Lo primero que tenemos que hacer es agregar las dependencias y plugins necesarios para poder utilizar este componente.

Agregamos en el bloque de dependencias del build.gradle de la app:

dependencies {

      implementation("androidx.navigation:navigation-fragment-ktx:2.3.5")
      implementation("androidx.navigation:navigation-ui-ktx:2.3.5")
}

Y realizamos el Sync del gradle (aparece un mensaje que pide que lo hagamos).


**2) AGREGAMOS EL NAVIGATION GRAPH**
El navigation graph es un recurso, por lo tanto vamos a la carpeta “res”, hacemos clic derecho -> New -> Android Resource File.

En el menú desplegable “Resource Type” elegimos “Navigation”, le ponemos un nombre (nav_graph) y le damos OK.

Se generará nav_graph.xml.

**3) AGREGAMOS DESTINATION**

Desde la vista de diseño del nav_graph.xml, vamos a agregar fragments.

Para esto, hacemos clic en “Click to add destination” y luego en “Create new destination”. Completamos el nombre del fragment y Finish.

Para este ejemplo, vamos a crear los siguientes fragments:

* MainFragment
* QuizFragment
* AboutFragment
* WinFragment
* LoseFragment

El ícono de la casa, indica cuál es el fragment principal.

En caso de tener creados los Fragments de antemano, sólo basta con hacer clic en “Click to add destination” y seleccionarlos para que aparezcan en el gráfico.

**4) CREAMOS ACTIONS - CONECTAMOS FRAGMENTS**

Se pueden conectar los fragments desde la vista diseño del nav_graph.xml, haciendo clic sobre el punto conector (del lado derecho del fragment) y, manteniendo presionado, arrastrando hasta el fragment destino.

Esto genera el siguiente código, creando una action:

<action android:id="@+id/action_mainFragment_to_quizFragment"
app:destination="@id/quizFragment"/>

Se deberán conectar los fragments de la siguiente manera:

* Main to Quiz
* Quiz to Win
* Quiz to Lose
* Main to About

**5)  ARMAMOS LOS LAYOUTS, CON LO QUE SE VA A MOSTRAR EN CADA PANTALLA.**

**6)  AGREGAMOS EL NAVHOST AL ACTIVITY

Esto se puede realizar desde el xml del Activity principal, por código o desde la vista diseño.

Para ambos casos, abrir el archivo xml correspondiente al Activity principal.
Desde la vista diseño, seleccionar la categoría Containers y arrastrar la vista de NavHostFragment al activity.
Luego,seleccionar el gráfico de navegación correspondiente para asociarlo con este NavHostFragment.

Desde el código, se agrega el fragment desde el que va a ocurrir la navegación.

<fragment
android:id="@+id/fragment"
android:name="androidx.navigation.fragment.NavHostFragment"
android:layout_width="match_parent"
android:layout_height="match_parent"
app:defaultNavHost="true"
app:navGraph="@navigation/nav_graph"
/>

*android:name Usa NavHostFragment como clase de creación
*app:navgraph Hace referencia a nuestro recurso nav_graph.xml
*app:defaultNavHost Al colocarlo en "true" le habilita el control back del sistema a este NavHostFragment

**7) Y AHORA VAMOS CON EL CÓDIGO!**

En cada fragment que creamos, ya se encuentra generado el código para inflar la vista correspondiente.

Vamos a agregar, para los casos de fragments en los que la navegación es con botones, el método onViewCreated(), y vamos a poner un Listener para el botón.
La idea es que al presionar dicho botón, se dispare el fragment correspondiente.
Esto se hace con el siguiente código:

botonPlay.setOnClickListener{

val action = MainFragmentDirections.actionMainFragmentToQuizFragment()  
findNavController().navigate(action)

}
La action que estamos llamando, es una de las generadas dentro del nav_graph.xml.

**8) Y QUÉ ESTÁ FALTANDO...?**
En este ejemplo, están creados todos los componentes y agregadas todas las dependencias. Pueden chequearlo!

Está faltando agregar:
* La comunicación entre el MainFragment y el AboutFragment.
* Desde el botón "Reintentar", del LoseFragment, volver al QuizFragment.


