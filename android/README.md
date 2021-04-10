# Clases
Prácticas de la materia

## ¿Qué vamos a encontrar al desarrollar para Android?

### Gradle Scripts

Siempre hay al menos dos build.gradle: uno para todo el proyecto (la carpeta sobre la que estamos
laburando) y uno por cada module (inicialmente es uno solo, pero podemos tener varios, por ejemplo,
con bibliotecas propias u otras apps).

El build.gradle del proyecto difícilmente lo toquemos, pero ahí van cosas que aplican a todos los
modules, como por ejemplo los repositorios y versión de gradle a usar.

El build.gradle del module define cómo se compila el module. Si bien se pueden definir muchas cosas
(y básicamente cambiar todas las etapas de construcción), lo primero que vamos a tocar acá son las
dependencias que necesitamos y en menor medida los plugins de compilación.

### Manifest

Este archivo contiene la información que el sistema operativo necesita saber sobre la aplicación,
como el nombre, iconito, permisos que necesita, activities y servicios que puede ejecutar, y
otras cosas que expone al OS y otras aplicaciones. De esta forma el OS no necesita ejecutar la
aplicación para saber qué puede hacer.

Entre las activities declaradas una en particular es la que se lanza al tocar el icono de la
aplicación en la home screen, que es la que tiene el intent-filter MAIN/LAUNCHER.

### Contextos

Como su nombre lo indica, define el contexto en el que se ejecuta la aplicación.
No hay un único contexto: la aplicación en sí misma es un contexto y cada activity y/o servicio
en ejecución son otros contextos que envuelven el contexto de la aplicación.

El contexto es el encargado de proveer la configuración de ejecución (tamaño de pantalla,
orientación, definición, idioma, etc).

### Recursos

Todo lo que no sea código puede ser un recurso. La idea es que en vez de hardcodear cosas en el
código, estas cosas se externalicen, por un lado para facilitar el mantenimiento, y por otro para
permitir proveer recursos alternativos dependiendo de la configuración.

Los recursos van en la carpeta `res` y cada tipo de recurso tiene su propia carpeta dentro de
esta. Algunos ejemplos son drawable (para imágenes y otras cosas que pueden dibujarse), values
(para valores como colores, cadenas de texto, estilos, dimensiones, etc), layout ("planos" de
las vistas), etc. mipmap es un recurso especial para el iconito de la aplicación.

Durante la compilación se autogenera una clase `R` con valores estáticos que identifican cada
recurso. Desde el código se recupera el contenido del recurso con funciones que reciben el
identificador de recurso del objeto que devuelve getResources() del contexto. Claro, como se usan
mucho, está lleno de funciones por todos lados que simplifican su uso. Para muchas funciones que
esperan una cadena de texto, color, layout, etc, van a encontrar sobrecargas que reciben un
identificador de recurso. Por ejemplo, si tengo una imagen llamada logo en drawable, su
identificador de recurso sería R.drawable.logo.

Cuando se provee un recurso alternativo para una configuración puntual se coloca en una carpeta
con el sufijo de la configuración que reemplaza. Por ejemplo, si tengo una imagen llamada
"logo" en drawable y quiero proveer una imagen alternativa para cuando el dispositivo está
apaisado, pongo una imagen con el mismo nombre en la carpeta drawable-landscape. El identificador
es el mismo.

### Intents

Son una descripción de una acción que debe tomar el SO. Principalmente los vamos a usar para
arrancar activities propias o de otras aplicaciones, pero también sirven para ejecutar servicios,
emitir eventos a otras apps, etc.

Como es una descripción, todo lo que incluya debe ser serializable. Esto permite poder pasar estos
mensajes entre procesos.

Cuando el usuario toca el iconito de la aplicación en la home screen se intenta ejecutar un intent
especial MAIN/LAUNCHER para la aplicación seleccionada. El SO filtra las activities de la misma y
lanza la que corresponde.

### Activities

Son el punto de interacción de la aplicación con el usuario.
Controla "toda la pantalla" y es responsable de la interacción con el SO.
Como es un contexto, cada vez que una configuración cambia la activity se recrea (sí, se puede
evitar) para que los recursos utilizados sean los correctos.

### Fragments

A partir de API 13 (que soporta pantallas más grandes, de tablets) se incorporaron para facilitar
la composición de "pantallas".

Si bien son parte de la API nativa, la biblioteca de support (ahora androidx) proveyó soporte para
versiones anteriores a la 13. Como la API nativa fue cambiando, los fragments se comportan de
manera distinta en algunas versiones de Android. Por este motivo en la API 28 se deprecó el uso de
fragments "nativos" en pro de utilizar la biblioteca de androidx.
