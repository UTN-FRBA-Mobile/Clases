# Clases
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

## Apéndice Test de UI 

- composeTestRule = createComposeRule(), esta llamada me permite trabajar con el contenido de Compose.

- Dentro de los buscadores más utilizados están: onNode, onAllNodes,onNodeWithText,onNodewithContentDescription. 

- Ej:composeTestRule.onNode(hasText("Button"))

- Para ver el árbol semántico: composeTestRule.onRoot().printToLog("TAG")

- Para ver el árbol separado: composeTestRule.onRoot(useUnmergedTree = true).printToLog("TAG")

- Ejemplo de acción: composeTestRule.onNode(...).performClick()

- No se pueden encadenar acciones dentro de una función de ejecución. Se deben hacer varias llamadas a "perform()"


**Para consultar el resumen de test pueden ir al siguiente link:[https://developer.android.com/jetpack/compose/testing-cheatsheet?hl=es-419](https://developer.android.com/jetpack/compose/testing-cheatsheet?hl=es-419)**