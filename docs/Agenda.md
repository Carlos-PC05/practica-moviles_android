# Agenda de desarrollo manual de práctica de aplicaciones móviles con Kotlin

### 26 Octubre 2026

Nivel actual: el formulario añade movimientos, el ViewModel actualiza el resumen y puedes cambiar de pantalla. Las pruebas pasan.

##### Tarea 

    1. Expón desde el ViewModel el flujo de movimientos que ahora mantiene privado.
    2. En el resumen, muestra por movimiento su categoría e importe en euros.

Comprobación: hay cuatro movimientos iniciales. Añade 25 € en Ocio; el resumen debe mostrar cinco filas y los gastos deben subir 25 €.

##### Notas de la tarea

`MovimientosRepository` devuelve un StateFlow<List<Movimiento>> y lo estoy intentando asociar a una lista, lo cual da un fallo de tipos.
En `ResumenScreen` estoy intentando llamar a `ResumenViewModel` como si fuera una función, sin crear un objeto.

La idea que falta es pasar el estado por las capas:

Repository: StateFlow<List<Movimiento>>
↓
ViewModel: expone los movimientos
↓
MainActivity: observa el flujo y obtiene la lista actual
↓
ResumenScreen: recibe la lista como parámetro

Tu siguiente paso: elimina la declaración inválida de ResumenScreen y haz que MainActivity le pase la lista que observa. La pantalla no debería buscar el ViewModel por su nombre.

// Le he pasado a `ResumenScreen` los movimientos que obtenía desde el repositorio el ViewModel por medio de `MainActivity`
StateFlow<List<Movimiento>> no es una lista y no se puede recorrer directamente.

Tenemos que obtener la lista de movimientos del StateFlow mediante collectAsState().value y ahí iterar la lista

##### Nivel completado: mostrar los movimientos y recoger cambios con collectAsState.

##### Nivel completado: eliminar movimientos desde el listado.

La pantalla envía el movimiento pulsado al ViewModel, que delega la eliminación al repositorio. La lista observable cambia y Compose actualiza el listado y el resumen. Comprobación: añade 25 € en Ocio, elimínalo desde el listado y confirma que quedan cuatro filas y bajan los gastos.

Siguiente paso: mostrar un mensaje cuando se hayan eliminado todos los movimientos. La comprobación y los pasos están en `docs/CONTINUAR-APRENDIZAJE.md`.
