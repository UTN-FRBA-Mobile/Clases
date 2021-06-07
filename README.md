# Clases
Prácticas de la materia

Este repositorio incluye código inicial para realizar tareas durante las clases. Cada branch representa una práctica distinta.

## HTML

Para mostrar contenido HTML se utiliza la vista WebView.

Esta vista provee la funcionalidad para mostrar una página web dentro de la jerarquía de vistas nativas.
No es un navegador completo ya que no provee barra de direcciones, barra de herramientas, etc, por lo tanto
si necesitamos que el usuario pueda realizar acciones que realizaría en un navegador, tenemos que
implementarlas nosotros mismos.

Provee también facilidades para interactuar con la aplicación desde la página web a través de interfaces
Javascript.
Una interfaz Javascript es un objeto común de Java (o Kotlin) cuyos métodos anotados con @JavascriptInterface
son accesibles desde Javascript. De esta manera la página puede solicitar datos o ejecutar acciones dentro
de la aplicación host.
El caso inverso (la aplicación solicitando datos o ejecutando acciones en la página) puede realizarse
invocando desde la app al método `evaluateJavascript` con el código javascript que queremos que se ejecute.

La vista WebView por omisión no permite navegar entre páginas. Si queremos cambiar este comportamiento
y los eventos de navegación (para, por ejemplo, mostrar un indicador de progreso mientras carga una página)
debemos cambiar las propiedades webViewClient y webChromeClient.

Si queremos utilizar las dev tools de Chrome en nuestra WebView debemos activar el modo debug con
`WebView.setWebContentsDebuggingEnabled(true)`, entrar en `chrome://inspect` en Chrome y conectarse.
