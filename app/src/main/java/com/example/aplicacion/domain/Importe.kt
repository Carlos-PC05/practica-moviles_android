package com.example.aplicacion.domain

import java.math.BigDecimal

/** Devuelve céntimos positivos o null si el texto no es un importe válido. */
fun importeEnCentimos(texto: String): Long? {
    return try {
        TODO("Validar el formato, convertir a céntimos y rechazar cero o negativos")
    } catch (_: NumberFormatException) {
        null
    } catch (_: ArithmeticException) {
        null
    }
}
