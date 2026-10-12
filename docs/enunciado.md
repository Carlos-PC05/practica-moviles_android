# Práctica final: Android

**Asignatura:** Aplicaciones para dispositivos móviles  
**Profesor:** Daniel Valero Carreras  
**Titulación:** Grado en Ingeniería Informática

## 1. Objetivos

Desarrollar los conceptos básicos que deben haber adquirido los alumnos. Reconocer y
utilizar las buenas prácticas y convenios de programación en Android. Conocer la
estructura básica de la programación en Android. Mostrar interés, iniciativa, creatividad y
destreza en la programación móvil sobre la plataforma Android.

Para ello, se deberá realizar una aplicación de Android que contenga los elementos
que se proponen en los siguientes puntos.

## 2. Requisitos

Debéis implementar una aplicación móvil que aglutine la mayoría de los conocimientos
vistos durante la asignatura. La temática es libre, pero debe cumplir con las siguientes
características:

### Requisitos visuales

- Pantalla de login.
- Pantalla de registro si no estás logueado.
- Pantalla de inicio con elementos de la temática correspondiente.
- Pantalla de detalle.
- Pantalla de perfil/ajustes.
- Pantalla de funcionalidades avanzadas (mapa, gráficos, vídeos, cámara, sensores,
  etc.).

### Requisitos técnicos obligatorios

La aplicación deberá utilizar los siguientes elementos vistos durante la asignatura:

- Jetpack Compose para el desarrollo de la interfaz.
- Material 3.
- Navigation Compose, con al menos cinco destinos.
- Paso de parámetros entre pantallas.
- Gestión correcta del estado de la interfaz.
- Una lista mediante `LazyColumn`, `LazyRow` o Grid.
- Arquitectura MVVM.
- Uso de ViewModel.
- Patrón Repository para acceso a datos.
- Persistencia mediante Room y/o Firebase según la naturaleza de la aplicación.
- Inyección de dependencias.
- Gestión apropiada de permisos Android cuando sean necesarios.
- Interfaz razonablemente adaptativa a diferentes tamaños de pantalla.

### Documentación

Además, deberéis completar la práctica con una documentación acorde a la misma, que
incluya como mínimo los siguientes apartados:

- Introducción.
- Diseño de la aplicación.
- Base de datos empleada.
- División del trabajo por personas en caso de que la aplicación se haya
  desarrollado en grupo.
- Desarrollo de la aplicación.
- Problemas encontrados y solución a los mismos.
- Puntos fuertes de mi aplicación / puntos débiles de mi aplicación.
- Conclusiones (tanto de la práctica como de la asignatura) y vías futuras.
- Uso de la IA (cómo se ha utilizado la IA para la creación de la aplicación).

### Presentación

Para finalizar, se debe preparar una presentación de **no más de 10 minutos** en la que
se debe:

- Mostrar la aplicación móvil realizada (ya sea en el emulador o en un smartphone
  real).
- Indicar la estructura del proyecto (clases más relevantes, vistas implementadas,
  código a destacar, etc.).
- Responder a las preguntas que haga el profesor.

## 3. Entrega

Habrá una entrega:

- Práctica en formato PDF con:
  - Enlace al proyecto subido en GitHub con los diferentes commits que
    demuestren la evolución del proyecto.

## 4. Evaluación (parte práctica: 20 %)

La práctica se valorará de la siguiente forma:

1. **Funcionamiento y requisitos funcionales: 3 puntos.**

   Se evalúa la implementación correcta del login, registro, inicio, detalle, perfil/ajustes
   y las pantallas necesarias para su temática. Navegación coherente, operaciones
   principales completas y sin errores importantes.

2. **Jetpack Compose, navegación y estado: 1 punto.**

   Se evalúa el correcto uso de Jetpack Compose, la navegación y el estado. Los componentes deben
   estar bien separados, y la navegación y los estados deben estar correctamente
   gestionados.

3. **Arquitectura y organización: 1 punto.**

   Utilización de la arquitectura planteada en clase: UI → ViewModel → Repository →
   fuente de datos. Inyección de dependencias, organización de paquetes, etc.

4. **Diseño e implementación de la base de datos: 1 punto.**

   Utilización de Firebase o Room para la implementación de la base de datos.

5. **Funcionalidades avanzadas: 1 punto.**

   Utilización de **al menos dos funcionalidades avanzadas**: cámara, sensores, QR,
   mapas, notificaciones, audio, vídeo, gráficos…

6. **Diseño adaptativo: 1 punto.**

   Utilización de diseño adaptativo para diferentes dispositivos móviles.

7. **Documentación: 1 punto.**

   Calidad de la documentación aportada.

8. **GitHub: 1 punto.**

   Repositorio bien estructurado y evolución razonable mediante commits.

## 5. Evaluación (parte teórica: 30 %)

La práctica se evaluará presencialmente en enero para demostrar que se han adquirido los
conocimientos necesarios. Para ello, se hará una entrevista personal al alumno/grupo en la
que se mostrará la aplicación y se le harán preguntas sobre el desarrollo de la misma.
