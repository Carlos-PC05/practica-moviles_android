# Continuar el aprendizaje

## Estado actual

El formulario añade movimientos. El resumen y el listado observan el mismo `StateFlow`; el listado permite eliminarlos y muestra un mensaje cuando queda vacío. El nivel de estado vacío se completó en el commit `5c15db6`.

## Siguiente paso: editar un movimiento

Aprenderás a reemplazar un elemento de una lista sin crear un movimiento adicional. El repositorio publicará una lista nueva y el `StateFlow` actualizará el listado y el resumen.

1. Añade una acción «Editar» en cada fila.
2. Abre el formulario con el importe y la categoría actuales.
3. Al guardar, actualiza ese movimiento en el repositorio y conserva el flujo de alta actual.

**Comprobación:** añade 25 € en Ocio, edítalo a 30 € en Vivienda y guarda. Debe seguir habiendo cinco filas; Ocio baja 25 €, Vivienda sube 30 € y el resumen refleja el cambio neto de 5 €.
