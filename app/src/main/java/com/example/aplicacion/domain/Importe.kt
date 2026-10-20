package com.example.aplicacion.domain

import java.math.BigDecimal

fun importeEnCentimos(texto: String): Long? {
    /*
    * Convierte un importe decimal positivo a céntimos, o devuelve 'null' si el formato, el valor o el rango no son válidos
    * */
    return try {
        // limpiamos el texto quitando espacios experiores
        val importe = texto.trim()
        // matches() -> valida todo el texto
        if (importe.isEmpty() || !importe.matches(Regex("[0-9]+([.,][0-9]{1,2})?"))) return null

        // replace() -> normaliza la coma decimal
        // BigDecimal() -> conserva la precisión
        val numero = BigDecimal(importe.replace(',', '.'))
        // signum() -> rechaza cero y negativos
        if (numero.signum() <= 0) return null

        // movePointRight(2) -> convierte euros a céntimos
        // longValueExact() -> comprueba que el resultado cabe en 'Long' sin perder decimales
        numero.movePointRight(2).longValueExact()

    } catch (_: NumberFormatException) {
        null
    } catch (_: ArithmeticException) {
        null
    }
}
