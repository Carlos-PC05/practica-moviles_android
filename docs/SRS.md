# SRS: MVP de finanzas personales e inversiones

- **Estado:** alcance acordado con el equipo mediante entrevista.
- **Fecha:** 12 de octubre de 2026.
- **Referencia académica:** [Enunciado de la práctica](enunciado.md).
- **Equipo:** cinco personas sin experiencia previa en Kotlin ni desarrollo móvil.
- **Objetivo de entrega del equipo:** principios de diciembre de 2026.

Este documento especifica el comportamiento del MVP. Los identificadores permiten
referenciar requisitos en los futuros tickets y pruebas. Todos los requisitos del MVP
son obligatorios; las mejoras futuras no forman parte del compromiso de entrega.
No fija clases, estructura de Firestore ni librerías concretas de gráficos o inyección:
esas decisiones se concretarán al desglosar la implementación.

## 1. Objetivo y alcance

Permitir a una persona saber en qué gasta su dinero, consultar si su flujo de caja
mensual es positivo y centralizar una visión sencilla de sus inversiones y patrimonio
neto actual. No pretende ofrecer contabilidad profesional ni asesoramiento financiero.

Cada usuario gestiona sus propios datos. La entrada es manual y todos los importes
se expresan en euros. La aplicación usa Firebase Authentication y Cloud Firestore;
no incluye Room ni un servidor propio. Requiere conexión para acceder y guardar cambios.

El MVP incluye movimientos, saldo líquido agregado, inversiones independientes,
valoraciones históricas, deuda total, gráficos y recordatorios locales.

## 2. Conceptos y datos

| Concepto | Datos y significado |
| --- | --- |
| Usuario | Identidad de Firebase, nombre completo obligatorio y correo. La contraseña se gestiona mediante Authentication. |
| Movimiento | Importe positivo, fecha, categoría y nota opcional. La categoría determina si es ingreso o gasto. |
| Categoría | Lista fija: Ingreso, Ocio, Vivienda, Comida, Transporte, Salud y Otros. Solo Ingreso suma. |
| Saldo inicial | Importe líquido, que puede ser negativo, al comienzo de una fecha elegida. Es único por usuario. |
| Ajuste de efectivo | Corrección del saldo agregado para igualarlo al saldo real indicado por el usuario. No es un movimiento de ingresos o gastos. |
| Inversión | Registro independiente: nombre, tipo, fecha de compra, importe total de compra, opción de descontar del efectivo y ticker opcional para acciones. |
| Tipo de activo | Lista fija: Acciones, Fondos, Oro, Inmuebles y Otros. |
| Valoración | Valor total en euros de una inversión en una fecha. No es el precio por unidad. |
| Deuda | Un único total pendiente, actualizado manualmente, sin préstamos individuales. |
| Recordatorio | Preferencia local del dispositivo: activado o desactivado y hora diaria. |

No se agrupan compras del mismo activo. Nombre y ticker no identifican de forma única
una inversión: pueden existir varios registros del mismo activo.

## 3. Requisitos funcionales

### 3.1. Cuenta y acceso

| ID | Requisito | Criterio de aceptación |
| --- | --- | --- |
| RF-01 | Registrar una cuenta con nombre completo, correo y contraseña. | Rechaza nombre vacío, correo inválido y contraseña no admitida por Authentication; comunica errores como correo ya registrado. |
| RF-02 | Iniciar sesión y recuperar contraseña mediante correo. | Una cuenta válida accede a sus datos; credenciales incorrectas y fallos de conexión tienen un mensaje comprensible. La recuperación permite solicitar el correo correspondiente. |
| RF-03 | Cerrar sesión. | Deja de mostrar los datos privados y cancela el recordatorio; otro usuario no ve datos de la sesión anterior. |
| RF-04 | Mostrar perfil y ajustes. | Muestra nombre completo y correo y permite configurar el recordatorio y cerrar sesión. |
| RF-05 | Mantener datos privados y recuperables desde otro dispositivo. | El mismo usuario recupera sus datos financieros al iniciar sesión; otro usuario no puede leerlos ni modificarlos, tampoco mediante acceso directo a Firestore. |

### 3.2. Movimientos y efectivo

| ID | Requisito | Criterio de aceptación |
| --- | --- | --- |
| RF-06 | Establecer un saldo inicial fechado antes de operar con el efectivo. | Acepta cero, positivos y negativos; la fecha no puede ser futura. Representa el saldo al comienzo del día elegido. |
| RF-07 | Crear movimientos manuales. | Exige importe mayor que cero, hasta dos decimales, categoría válida y fecha entre la fecha inicial y hoy, ambas incluidas. La nota es opcional. |
| RF-08 | Listar movimientos y filtrar por mes y categoría. | Los resultados corresponden a los filtros seleccionados y muestran fecha, categoría, importe y signo. Un resultado vacío se indica expresamente. |
| RF-09 | Editar y eliminar movimientos. | Aplica las mismas validaciones de creación. Eliminar requiere confirmación. Saldo, resumen y gráficos reflejan el cambio sin duplicar su efecto. |
| RF-10 | Calcular el saldo líquido agregado. | Incluye saldo inicial, movimientos, descuentos de inversiones y ajustes de efectivo según las reglas de la sección 4. Puede ser negativo. |
| RF-11 | Corregir el saldo inicial sin cambiar su fecha. | Cambiar 1.000 € por 1.100 € incrementa el saldo calculado en 100 € sin modificar movimientos ni gráficos de flujo de caja. |
| RF-12 | Ajustar el saldo líquido actual. | Al indicar el saldo real, el saldo mostrado coincide con él. El ajuste no altera ingresos, gastos ni flujo de caja. No se exige un historial visible de ajustes. |
| RF-13 | Consultar el resumen de un mes. | Muestra ingresos, gastos y flujo de caja del mes seleccionado, incluido cero si no existen movimientos. |

La interfaz solicita importes positivos en los movimientos y aplica el signo por
categoría. Ingreso se muestra en verde y se distingue también por signo o texto;
la comprensión nunca depende exclusivamente del color.

### 3.3. Inversiones y valoraciones

| ID | Requisito | Criterio de aceptación |
| --- | --- | --- |
| RF-14 | Crear inversiones independientes. | Exige nombre no vacío, tipo válido, fecha de compra no futura e importe total de compra positivo con hasta dos decimales. Ticker opcional únicamente para acciones. |
| RF-15 | Registrar la valoración inicial de compra y el valor actual. | La compra genera una valoración inicial por su importe y fecha. Si se aporta otra valoración actual, exige importe no negativo y fecha entre compra y hoy. Solo existe una valoración por inversión y fecha. |
| RF-16 | Ofrecer «Descontar del saldo líquido», desactivado por defecto. | Desactivado no afecta al efectivo. Activado descuenta el importe de compra una vez y no crea un gasto cotidiano. |
| RF-17 | Editar inversiones y su descuento. | Corregir importe o activar/desactivar el descuento aplica únicamente la diferencia al efectivo. Corregir fecha o importe de compra actualiza coherentemente la valoración inicial. |
| RF-18 | Eliminar una inversión con confirmación. | Elimina sus valoraciones y revierte el descuento si existía. La eliminación corrige un registro, no representa una venta. |
| RF-19 | Añadir, editar y eliminar valoraciones. | Exige valor no negativo, hasta dos decimales y fecha entre compra y hoy. No permite duplicar fechas ni eliminar la valoración inicial; esta se corrige desde los datos de compra. |
| RF-20 | Mostrar listado y detalle de inversiones. | El listado muestra nombre, tipo y último valor; el detalle muestra datos de compra, descuento, valoraciones y evolución de la inversión. |
| RF-21 | Calcular valor total y distribución por tipo de activo. | Suma la última valoración de cada registro; agrupa esa suma por tipo, sin agrupar los registros individuales. |

Pueden registrarse activos comprados antes de la fecha del saldo inicial, pero no
pueden descontarse de ese saldo: ya forman parte de la situación previa. La interfaz
explica la restricción. Una compra en la fecha inicial sí puede descontarse, puesto
que el saldo inicial corresponde al comienzo de ese día.

Al corregir la fecha de compra no se permite dejar valoraciones anteriores a ella,
ni crear dos valoraciones en la misma fecha. Se solicita resolver el conflicto;
no se eliminan valoraciones silenciosamente. Si compra y valoración actual tienen
la misma fecha, se conserva un único valor para esa fecha, inicialmente el de compra;
el usuario puede editar ese valor desde las valoraciones. Una corrección posterior del
importe de compra vuelve a fijar la valoración inicial al importe corregido. La
interfaz debe advertir de este efecto antes de confirmar la corrección.

### 3.4. Deuda y patrimonio

| ID | Requisito | Criterio de aceptación |
| --- | --- | --- |
| RF-22 | Consultar y actualizar la deuda total pendiente. | Acepta cero o un importe positivo con hasta dos decimales. No genera movimientos ni modifica automáticamente el efectivo. |
| RF-23 | Mostrar patrimonio neto actual. | Calcula efectivo más valor actual de inversiones menos deuda pendiente; admite resultados negativos. |

El usuario mantiene manualmente la deuda. Un pago registrado como gasto reduce el
efectivo, pero no reduce automáticamente la deuda: no hay gestión de préstamos.

### 3.5. Gráficos y estadísticas

| ID | Requisito | Criterio de aceptación |
| --- | --- | --- |
| RF-24 | Mostrar gastos por categoría del mes seleccionado. | Incluye únicamente categorías de gasto y concuerda con el total mensual de gastos. |
| RF-25 | Mostrar ingresos y gastos mensuales de los últimos doce meses. | Incluye el mes actual y los once anteriores, con meses sin movimientos a cero y el mes actual parcial. |
| RF-26 | Mostrar distribución actual de inversiones por tipo de activo. | Los importes y porcentajes corresponden a RF-21. Si el total es cero, no divide por cero y muestra un estado sin distribución. |
| RF-27 | Mostrar evolución mensual del valor de las inversiones. | Aplica la última valoración conocida al cierre de cada mes; antes de la compra el registro aporta cero. El mes actual usa la última valoración hasta hoy. |
| RF-28 | Explicar el significado del histórico. | Identifica las valoraciones como manuales y no llama rentabilidad al crecimiento del valor, que también incluye nuevas compras. |

El detalle aplica las mismas reglas de evolución a una sola inversión. El histórico
se presenta desde el primer mes de compra registrado hasta el mes actual. Si no hay
inversiones, se muestra un estado vacío. No hay histórico del patrimonio neto completo.

### 3.6. Notificaciones locales

| ID | Requisito | Criterio de aceptación |
| --- | --- | --- |
| RF-29 | Configurar un recordatorio diario para registrar movimientos. | Está desactivado inicialmente; permite elegir hora y activar/desactivar. La hora es aproximada, sin promesa de ejecución exacta. |
| RF-30 | Gestionar el permiso de notificaciones cuando corresponda. | Rechazarlo no impide utilizar la aplicación; se informa de que el recordatorio no puede mostrarse. |
| RF-31 | Mantener recordatorios por dispositivo y sesión. | No sincroniza la preferencia entre dispositivos, no programa duplicados y deja de notificar al cerrar sesión. |

## 4. Reglas de cálculo e integridad

### 4.1. Magnitudes distintas

- **Ingresos del periodo:** suma de movimientos de categoría Ingreso.
- **Gastos del periodo:** suma positiva de movimientos de las categorías de gasto.
- **Flujo de caja cotidiano:** ingresos menos gastos del periodo. Excluye compras de
  inversiones, ajustes de saldo y cambios de deuda.
- **Saldo líquido actual:** saldo inicial + ingresos registrados − gastos registrados
  − compras con descuento activo + ajustes de efectivo.
- **Valor de inversiones:** suma de la valoración más reciente de cada inversión.
- **Patrimonio neto actual:** saldo líquido + valor de inversiones − deuda pendiente.

El flujo de caja se etiqueta de forma que no se confunda con la variación total del
efectivo. No equivale al saldo disponible ni incorpora todos los movimientos patrimoniales.

### 4.2. Ajustes y correcciones

Un ajuste de efectivo representa la diferencia entre el saldo real indicado y el saldo
calculado justo antes de guardar el ajuste. Las correcciones posteriores de movimientos,
compras o saldo inicial siguen afectando al saldo: el ajuste no oculta sus diferencias.
No se prescribe cómo almacenar o calcular estas magnitudes internamente.

Guardar una inversión y su efecto en efectivo debe ser atómico: se guardan ambos o
ninguno. La misma garantía se exige al editar o eliminar una inversión y sus datos
asociados. Reintentar una operación o pulsar varias veces no debe duplicar sus efectos.

Los importes se calculan con precisión de céntimos, evitando errores acumulados de
coma flotante. Las fechas financieras son fechas de calendario sin hora; los meses
son meses naturales. Los gráficos y resúmenes usan los mismos criterios de fechas.

### 4.3. Ejemplos verificables

| Caso | Resultado esperado |
| --- | --- |
| Saldo inicial 1.000 €, ingreso 500 € y gasto 200 € en el mismo mes | Efectivo 1.300 €; flujo de caja del mes +300 €. |
| Sobre el caso anterior, compra de 400 € con descuento y valoración de 450 € | Efectivo 900 €; inversiones 450 €; flujo de caja sigue en +300 €. |
| Sobre el caso anterior, deuda pendiente de 100 € | Patrimonio neto 1.250 €. |
| Corregir la compra descontada de 400 € a 500 € | Efectivo baja otros 100 €; no se descuenta de nuevo el importe completo. |
| Desactivar el descuento de una compra de 500 € | Efectivo aumenta 500 €; no se genera ingreso. |
| Eliminar una compra descontada de 500 € | Efectivo aumenta 500 € y desaparecen el registro y sus valoraciones. |
| Ajustar un efectivo calculado de 900 € a 850 € | Ajuste de −50 €; flujo de caja intacto. Un gasto posterior de 20 € deja 830 €. |
| Compra por 1.000 € en enero y valoración de 1.100 € en marzo | Histórico: enero 1.000 €, febrero 1.000 €, marzo 1.100 €. |
| Sin movimientos ni inversiones y deuda cero | Totales cero salvo el saldo inicial; sin porcentajes inválidos ni gráficos engañosos. |

## 5. Pantallas y navegación

| Pantalla | Contenido o acciones principales |
| --- | --- |
| Login | Acceso, enlace a registro y recuperación de contraseña. |
| Registro | Nombre completo, correo y contraseña. |
| Inicio | Mes seleccionado, ingresos, gastos, flujo de caja, efectivo, deuda y patrimonio neto; acceso a estadísticas y ajustes de saldos. |
| Movimientos | Lista, filtros, creación, edición y eliminación. |
| Formulario de movimiento | Campos y validaciones de RF-07; reutilizado para editar. |
| Inversiones | Lista, total, distribución y creación de registros. |
| Detalle de inversión | Datos, valoraciones, evolución y acciones de edición/eliminación. |
| Formulario de inversión | Campos de compra, valoración y opción de descuento; reutilizado para editar. |
| Estadísticas | Gráficos de gastos, ingresos/gastos y evolución de inversiones. |
| Perfil/ajustes | Nombre, correo, configuración de recordatorio y cierre de sesión. |

La navegación principal contiene Inicio, Movimientos, Inversiones y Ajustes.
Estadísticas se abre desde Inicio. Los formularios de saldos, deuda y valoraciones
pueden ser diálogos o pantallas: no requieren destinos adicionales propios.
La configuración inicial del saldo se solicita antes de operar con el efectivo.

## 6. Requisitos técnicos y de calidad

| ID | Requisito | Comprobación de aceptación |
| --- | --- | --- |
| RT-01 | Kotlin, Jetpack Compose y Material 3 para la interfaz Android. | Las pantallas del MVP usan Compose y componentes/tema Material 3. |
| RT-02 | Navigation Compose con al menos cinco destinos y paso de parámetros. | Se navega entre las pantallas previstas y se pasa el identificador de inversión o movimiento para consultar/editar el registro correcto. |
| RT-03 | Arquitectura UI → ViewModel → Repository → fuente de datos. | La UI no accede directamente a Firestore; responsabilidades y paquetes son identificables y explicables por el equipo. |
| RT-04 | Uso de ViewModel e inyección de dependencias. | Las dependencias se suministran mediante el mecanismo de inyección elegido por el equipo; no se incorpora una arquitectura adicional innecesaria. |
| RT-05 | Persistencia con Firebase y aislamiento por usuario. | Reglas de Firestore validan autenticación, propiedad y datos esenciales; no basta con filtrar datos en la UI. Las contraseñas no se guardan en Firestore. |
| RT-06 | Al menos una lista LazyColumn, LazyRow o Grid. | Movimientos o inversiones utilizan una de estas listas. |
| RT-07 | Gestión del estado, carga, errores y vacíos. | Se distingue carga de ausencia de datos. Un fallo de guardado conserva el formulario y permite reintentar; no se anuncia éxito antes de confirmar la escritura remota. |
| RT-08 | Interfaz adaptativa y accesible. | En teléfono y pantalla grande no se cortan campos ni acciones; se adapta la distribución. Controles etiquetados, contenido desplazable y signos/texto además de color. |
| RT-09 | Permisos Android apropiados. | Se solicita el permiso de notificaciones cuando sea necesario; su rechazo tiene el comportamiento de RF-30. No se solicitan permisos ajenos al MVP. |
| RT-10 | Conexión requerida sin garantía de modo sin conexión. | Un fallo de red no se presenta como guardado confirmado ni provoca pérdida del formulario o exposición de datos de otra cuenta. |
| RT-11 | Verificación de reglas críticas. | Hay pruebas de cálculos, correcciones de compras, ajustes e históricos, y comprobaciones de reglas de acceso con dos usuarios. |

## 7. Trazabilidad con el enunciado

| Exigencia académica | Cobertura del MVP |
| --- | --- |
| Login y registro | RF-01 a RF-03 y pantallas de acceso. |
| Inicio, detalle y perfil/ajustes | Inicio, detalle de inversión y perfil/ajustes. |
| Pantalla de funcionalidades avanzadas | Estadísticas con gráficos; configuración y uso de notificaciones locales. |
| Al menos dos funcionalidades avanzadas | Gráficos y notificaciones locales. Confirmar esta combinación con el profesor antes de la implementación. |
| Compose, Material 3, navegación y estado | RT-01, RT-02 y RT-07. |
| Listas, MVVM, ViewModel, Repository e inyección | RT-03, RT-04 y RT-06. |
| Persistencia, permisos y adaptación | RT-05, RT-08 y RT-09. |
| Evolución razonable mediante commits | Repositorio organizado con commits que reflejen el trabajo del equipo; no una única subida final. |

Además del software, la entrega académica debe incluir:

- Documentación en PDF con introducción, diseño, base de datos, reparto entre los cinco
  miembros, desarrollo, problemas y soluciones, puntos fuertes y débiles, conclusiones,
  vías futuras y explicación del uso de IA.
- Enlace al repositorio GitHub con la evolución del proyecto.
- Presentación de hasta diez minutos: demostración de la aplicación, estructura y
  código relevante, y respuesta a preguntas del profesor.
- Preparación para la entrevista presencial de enero; cada miembro debe entender y
  poder explicar lo desarrollado, incluida la ayuda recibida de IA.

## 8. Fuera del MVP y posibles mejoras futuras

| Mejora | Alcance futuro |
| --- | --- |
| Cotizaciones externas | Consultar precios mediante API, inicialmente para activos compatibles, bajo demanda y admitiendo retraso; mostrar fuente, moneda y fecha. Conservar entrada manual como alternativa. |
| Agrupación por activo | Agrupar compras del mismo activo, cantidades y precio medio ponderado. |
| Operaciones de inversión | Ventas totales/parciales, dividendos, comisiones y rentabilidad que distinga aportaciones de variaciones de precio. |
| Automatización de movimientos | Movimientos recurrentes, importaciones y posibles conexiones bancarias. |
| Estadísticas adicionales | Semanas, trimestres y rangos personalizados. |
| Patrimonio histórico completo | Historial de efectivo y deuda junto con las valoraciones de inversiones. |
| Finanzas más detalladas | Cuentas separadas, transferencias, deudas individuales y amortizaciones. |
| Personalización | Categorías propias y múltiples monedas con conversión. |
| Cuenta y disponibilidad | Acceso social, foto de perfil, eliminación de cuenta desde la app y modo sin conexión garantizado. |

### 8.1. Resultado preliminar de la investigación de cotizaciones

Consulta documental realizada el 12 de octubre de 2026:

- [Alpha Vantage](https://www.alphavantage.co/support/api-key/) anuncia un acceso
  gratuito general de 25 peticiones diarias y condiciones especiales para proyectos
  educativos o de código abierto verificados. No se ha obtenido esa verificación.
- [Twelve Data](https://twelvedata.com/pricing) ofrece planes con distintas coberturas
  y límites; no se ha confirmado que un plan gratuito cubra los activos deseados.

No se ha probado ninguna integración con credenciales. Antes de incluirla hay que
verificar activos y mercados concretos, precios en euros o conversión, actualización,
límites, custodia de claves y condiciones de uso. No se promete tiempo real ni cobertura
universal. Esta integración no condiciona la aceptación del MVP.

## 9. Criterio de finalización y siguiente fase

El MVP está terminado cuando los requisitos RF y RT tienen sus criterios satisfechos,
los ejemplos de cálculo se verifican y se puede demostrar el recorrido completo:
registro → saldo inicial → movimientos → inversión y valoraciones → estadísticas →
deuda y patrimonio → recordatorio → cierre de sesión y recuperación de datos.

Antes de repartir implementación, el equipo debe convertir estos identificadores en
tickets pequeños con dependencias y responsables, concretar las decisiones técnicas
pendientes y confirmar con el profesor las funcionalidades avanzadas. Este documento
no asigna tareas ni crea issues. Cualquier ampliación del MVP requiere un acuerdo
explícito y una actualización de este SRS.
