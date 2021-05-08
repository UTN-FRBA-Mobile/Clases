# Clases
Prácticas de la materia

## Práctica Permisos y Persistencia

Configurar aplicación para que solicite permisos de lectura/escritura de almacenamiento externo y para que persista archivos en File System así como en preferencias.
* Partiendo de una aplicación de edición de fotos casi funcionando, se debe agregar el código necesario para que la misma sea funcional.
* La aplicación está preparada para:
  - Listar las imágenes editadas por el usuario previamente en la pantalla inicial de la misma, en formato de grilla o lista, según gusto de usuario (dicho gusto debe ser recordado por la aplicación -> Guardado en preferencias)
  - Lanzar un Picker de imágenes, y recibir la imágen seleccionada
  - Editar la imágen seleccionada según una serie de filtros y calibraciones de brillo, contraste y saturación
* Inicialmente la aplicación usa el almacenamiento interno para guardar y listar las imágenes pero siendo que queremos que estas imágenes se puedan ver desde otras aplicaciones (por ejemplo Files) debemos cambiar la aplicación para usar almacenamiento externo.
* A la aplicación le hace falta:
  - Guardar las preferencias.
  - Cambiar el uso de almacenamiento interno por externo.

### Guardar las preferencias
La clase `Preferences` está preparada para persistir las preferencias pero no lo hace. Se provee una property `sharedPreferences` que facilita el acceso a las mismas y una constante `showAsGridKey` para usar de clave.
* Cambiar `showAsGrid` para que no tenga _backing field_ y sea computada:
  - en el get retornar el valor de `showAsGridKey` de las preferencias.
  - en el set guardar el valor en las preferencias.

Como el objeto para acceder a las preferencias es de solo lectura se debe crear un editor con `.edit()`, hacer los cambios ahí y luego confirmar los cambios con `.apply()`.  

### Cambiar el uso de almacenamiento interno por externo

En los métodos `MainViewModel.reloadImages` y `EditImageFragment.save` se utiliza la clase `InternalStorage` para leer y guardar las imágenes respectivamente. Se debe cambiar para usar `ExternalStorage`. Una vez hecho esto las imágenes generadas se pueden acceder desde otras apps que tengan permiso a leer el almacenamiento externo.

Tanto `InternalStorage` como `ExternalStorage` están definidas en el package `.utils.fileSystem`. Se dejan más métodos que los necesarios para que sirvan de ejemplo.

### Notas:
- Para cargar imagenes al emulador de Android se puede usar el Device File Explorer. Un buen lugar para dejarlas es la carpeta de Pictures (`/sdcard/Pictures`), ya que ahí las ve la galería de imágenes.
