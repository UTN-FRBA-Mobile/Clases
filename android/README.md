# Clases-2021c1
## Práctica de Test

Este proyecto contiene una aplicación que permite realizar conversiones entre distintas unidades de temperatura. El usuario ingresa un valor y al presionar el botón, se calcula la temperatura en las restantes escalas.

El desarrollador no realizó ningún test.

El usuario descargó la app. Apenas la abrió presionó los botones sin haber ingresado valores y la aplicación dejó de funcionar.

Se armó un test de interfaz ("botonesDesactivadosSinTexto ()", dentro de la clase MainACtivityTest) para dicho caso y actualmente la aplicación no lo está pasando.

1.  Realizar las correcciones necesarias para que dicho test dé verde.

2.  Realizar un test de interfaz dónde se chequee que al ingresar texto se habilitan los botones. Siempre chequear que no fallen los demás test.

3.  Agregar un test unitario verificando alguna de las funciones de conversión.


# Apéndice Test Unitario

## Algunas Annotations de JUnit4:

- import org.junit.*: Hay que agregar este import para poder utilizar las annotations.

- @Test: Indica que el método o función es testeable

- @Before: Indica que el código en dicho bloque se ejecutará antes de cada test.

- @After: Indica que el código en dicho bloque se ejecutará luego de cada test. Se usa si es necesario volver el escenario a cómo estaba originalmente.

## Algunos asserts:

- fail([message]): Let the method fail. Might be used to check that a certain part of the code is not reached or to have a failing test before the test code is implemented. The message parameter is optional.

- assertTrue([message,] boolean condition): Verifica que la condición es verdadera

- assertFalse([message,] boolean condition): Verifica que la condición es falsa

- assertEquals([message,] expected, actual): Compara que dos valores sean iguales.

- assertEquals([message,] expected, actual, tolerance): Compara que dos valores sean iguales con un valor de tolerancia para decimales.

- assertNull([message,] object): Chequea que el objeto es null.

- assertNotNull([message,] object): Chequea que el objeto no es null.

**Para más información de test Unitarios pueden ir al siguiente link:  [https://developer.android.com/training/testing/unit-testing/local-unit-tests](https://developer.android.com/training/testing/unit-testing/local-unit-tests)**

## Apéndice Test de UI (Espresso)

-   Para poder hacer referencia a una view en particular, utilizamos el siguiente código onView(withId(R.id.ID_DE_LA_VIEW))

-   Luego podemos utilizar distintos métodos de chequeos mediante el método "check" onView(withId(R.id.ID_DE_LA_VIEW)).check(ViewAssertion)

-   O podemos ejecutar acciones sobre la interfaz, utilizando el método "perform" onView(withId(R.id.ID_DE_LA_VIEW)).perform(ViewAction)


### Algunos métodos destacados:

- onView: Devuelve una interacción a partir de un matcher

- withId / withText: Devuelve un matcher a partir de un id o texto de la pantalla

- check: Evalua una expresión para una interacción determinada utilizando matches.

- matches: Determina si una expresión es válida para determinada interacción

- not: negación

- perform: ejecuta una acción determinada sobre una interacción

- click: Simula un click

- typeText: simula la escritura de un texto

**Ej: onView(withId(resId)).check(matches(not(isDisplayed())));**

**Ej: onView(withId(resId)).perform(click());**

**Para más información de Test de Interfaz pueden ir al siguiente link:[https://developer.android.com/training/testing/espresso/basics](https://developer.android.com/training/testing/espresso/basics)**