package com.example.aplicacion.domain

enum class Categoria {
    INGRESO, OCIO, VIVIENDA, COMIDA, TRANSPORTE, SALUD, OTROS
}

data class Movimiento(val importeCentimos: Long, val categoria: Categoria) {
    init {
        require(importeCentimos > 0) { "El importe debe ser positivo" }
    }
}

data class Resumen(
    val ingresosCentimos: Long,
    val gastosCentimos: Long,
    val flujoCentimos: Long
)

fun calcularResumen(movimientos: List<Movimiento>): Resumen {
    var total = 0L
    var ingresos = 0L
    var gastos = 0L

    for (movimiento in movimientos){
        if (movimiento.categoria == Categoria.INGRESO) {
            total += movimiento.importeCentimos
            ingresos += movimiento.importeCentimos
        }
        else {
            total -= movimiento.importeCentimos
            gastos += movimiento.importeCentimos
        }
    }

    return Resumen(ingresos, gastos, total);
}
