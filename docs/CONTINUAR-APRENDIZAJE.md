# Retomar la sesión de aprendizaje

Sesión pausada el 15 de octubre de 2026 por decisión del usuario.
Rama de trabajo: `experiment/pruebas-estudio-kotlin`.

## Cómo acompañar al usuario

Continuar en **modo mentor**, en español, con pasos pequeños y explicaciones
para alguien que empieza con Kotlin y Android. El usuario escribe las reglas
que está aprendiendo; el agente prepara pruebas, conexiones y configuración.
Explicar las ideas nuevas antes de pedir código. Dar sintaxis y llamadas de
biblioteca directamente, sin resolver el ejercicio salvo que el usuario lo pida.

El usuario implementó `calcularResumen`. Durante el primer ejercicio dejó
el marcador `TODO` delante de su solución: se explicó que lanza una excepción,
se retiró y las pruebas pasaron. Recordar que debe **sustituir** el marcador
al completar el siguiente ejercicio.

## Fuentes de verdad

- Alcance y criterios de aceptación: [SRS](SRS.md).
- Exigencias académicas: [enunciado](enunciado.md).
- Implementación y pruebas: consultar los archivos y commits siguientes;
  conservar los cambios del usuario al releerlos.
- No hay un `PLAN.md` vigente. Esta guía recoge el punto de reanudación.

## Trabajo guardado

| Commit | Unidad de trabajo |
| --- | --- |
| `b6d273e` | Modelo y cálculo del resumen, con pruebas. |
| `224c00e` | Repository de ejemplos y ViewModel, con prueba de integración local. |
| `b6fc308` | Pantalla Compose conectada al ViewModel. |
| `94da9c7` | Esqueleto y pruebas del ejercicio pendiente de importes. |

El usuario modificó los datos de ejemplo del Repository y retiró la vista
previa de la pantalla. Esos cambios se han conservado. El resumen actual usa
esos ejemplos; la prueba del ViewModel se ajustó a ellos.

## Punto exacto para continuar

La siguiente meta acordada es añadir un movimiento local mediante un formulario
y ver actualizado el resumen. **Primero**, el usuario debe completar
`importeEnCentimos` en
`app/src/main/java/com/example/aplicacion/domain/Importe.kt`.

El contrato y los ejemplos verificables están en `ImporteTest.kt`, en el paquete
de pruebas `com.example.aplicacion.domain`. Ya se explicó que `Long?` devuelve
céntimos o `null`, y se mostraron `replace(',', '.')` y la conversión exacta
con `BigDecimal.movePointRight(2).longValueExact()`.

Todavía no ha presentado un intento de este ejercicio. Releer su código antes
de orientar. La ayuda pendiente consiste en comprobar el formato, normalizar
el separador, convertir sin pérdida y rechazar importes no positivos.
El manejo de errores de conversión está preparado.

## Comprobaciones al retomar

Ejecutar `git status`, `git log -5 --oneline` y:

```bash
./gradlew :app:testDebugUnitTest
```

Al cerrar la sesión: **9 tests, 6 aprobados y 3 fallidos**. Los tres fallos son
de `ImporteTest`, por `NotImplementedError`. Se conservaron intencionadamente
como ejercicio pendiente; no desactivar las pruebas ni implementar la solución
en nombre del usuario para obtener verde.

Para trabajar solo el ejercicio:

```bash
./gradlew :app:testDebugUnitTest --tests 'com.example.aplicacion.domain.ImporteTest'
```

La compilación debug y lint terminaron correctamente. El comando conjunto con
`--continue` devolvió error por esas tres pruebas. No hubo verificación visual
del agente en dispositivo: no había ninguno conectado. El usuario dio su visto
bueno a continuar después de conectar la pantalla.

Una vez aprobado el ejercicio, el agente prepara el formulario y el estado
observable del ViewModel. Un guardado local debe actualizar el resumen; una
entrada inválida debe conservarse y mostrar un error. La persistencia remota
queda para una etapa posterior. El MVP completo sigue definido en el SRS.

## Límites y precauciones

Los datos son ejemplos locales. Aún no hay formulario, filtrado mensual,
autenticación funcional ni persistencia de movimientos implementados.
La configuración Firebase local está ignorada; seguir [README](../README.md).
La sesión anterior comprobó que la clave local difería de la publicada, pero
no verificó revocación, restricciones ni reglas en la consola. Mantener las
credenciales fuera de commits y de esta guía.

El archivo vacío `app/google-services.com` estaba añadido al índice y ausente
en disco; se retiró del índice. No formaba parte del commit base.
Los commits se crearon localmente; el cierre no incluye push.

## Suggested skills

- `socratic-mentor`: retomar la enseñanza y mantener el ejercicio a cargo del usuario.
- `ponytail:ponytail`: conservar la solución mínima y verificar cada cambio.
- `impeccable`: cuando se prepare el formulario Compose, respetar Material 3 y accesibilidad.
- `handoff`: actualizar esta guía si se vuelve a pausar; el usuario pidió guardarla en `docs/`.
