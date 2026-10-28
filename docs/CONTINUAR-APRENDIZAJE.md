# Continuar el aprendizaje

## Estado actual

El formulario añade movimientos al repositorio en memoria. El resumen y la pantalla de movimientos observan el mismo `StateFlow`.
Desde el listado se puede eliminar cada movimiento; el resumen se recalcula al cambiar la lista.

## Siguiente paso: mostrar el estado vacío

Aprenderás a presentar una interfaz distinta cuando la lista no contiene movimientos.

1. En `MovimientosScreen`, muestra «No hay movimientos» cuando la lista esté vacía.
2. Conserva las filas y sus botones de eliminación cuando haya movimientos.
3. Mantén disponible el botón para volver al resumen.

**Comprobación:** elimina los cuatro movimientos iniciales. El listado debe mostrar «No hay movimientos» y ningún botón para eliminar.
Después, vuelve al formulario, añade 25 € en Ocio y comprueba que el listado muestra una fila y el resumen se actualiza.
