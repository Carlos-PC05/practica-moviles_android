package com.example.aplicacion.domain

import java.math.BigDecimal

/**
 * Convierte un importe positivo escrito en euros a céntimos exactos.
 *
 * Quita espacios exteriores, valida hasta dos decimales y normaliza la coma decimal.
 * `BigDecimal` evita redondeos; `signum()` rechaza cero y negativos, y
 * `longValueExact()` rechaza desbordamientos.
 *
 * @param texto importe en euros, con punto o coma decimal y hasta dos decimales.
 * @return los céntimos o `null` si el texto no representa un importe válido.
 */
fun importeEnCentimos(texto: String): Long? {
    return try {
        val importe = texto.trim()
        if (importe.isEmpty() || !importe.matches(Regex("[0-9]+([.,][0-9]{1,2})?"))) return null

        val numero = BigDecimal(importe.replace(',', '.'))
        if (numero.signum() <= 0) return null

        numero.movePointRight(2).longValueExact()

    } catch (_: NumberFormatException) {
        null
    } catch (_: ArithmeticException) {
        null
    }
}
