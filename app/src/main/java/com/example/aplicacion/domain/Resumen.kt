package com.example.aplicacion.domain

/** Categorías para clasificar movimientos.
 * Solo `INGRESO` se contabiliza como ingreso; las demás categorías son gastos.
 */
enum class Categoria {
    INGRESO, OCIO, VIVIENDA, COMIDA, TRANSPORTE, SALUD, OTROS
}

/** Movimiento con importe positivo, expresado en céntimos, y su categoría. */
data class Movimiento(val importeCentimos: Long, val categoria: Categoria) {
    init {
        require(importeCentimos > 0) { "El importe debe ser positivo" }
    }
}

/** Totales calculados para un conjunto de movimientos, todos expresados en céntimos. */
data class Resumen(
    /** Suma de los movimientos de ingreso. */
    val ingresosCentimos: Long,
    /** Suma positiva de los movimientos de gasto. */
    val gastosCentimos: Long,
    /** Ingresos menos gastos; puede ser negativo. */
    val flujoCentimos: Long
)

/**
 * Suma los ingresos, los gastos y calcula el flujo como ingresos menos gastos.
 *
 * @param movimientos lista cuyos importes están expresados en céntimos.
 * @return ingresos y gastos como totales positivos, y flujo con su signo resultante.
 */
fun calcularResumen(movimientos: List<Movimiento>): Resumen {
    var total = 0L
    var ingresos = 0L
    var gastos = 0L

    for (movimiento in movimientos) {
        if (movimiento.categoria == Categoria.INGRESO) {
            total += movimiento.importeCentimos
            ingresos += movimiento.importeCentimos
        } else {
            total -= movimiento.importeCentimos
            gastos += movimiento.importeCentimos
        }
    }

    return Resumen(ingresos, gastos, total)
}
