package com.example.aplicacion.domain

import java.math.BigDecimal
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class ImporteTest {
    @Test
    fun aceptaEurosConComaOPunto() {
        assertEquals(5_000L, importeEnCentimos("50"))
        assertEquals(5_025L, importeEnCentimos("50,25"))
        assertEquals(5_025L, importeEnCentimos("50.25"))
    }

    @Test
    fun coincideConLaConversionDecimalExacta() {
        for (texto in listOf("0.01", "7.5", "1234.56", "92233720368547758.07")) {
            val esperado = BigDecimal(texto).movePointRight(2).longValueExact()
            assertEquals(texto, esperado, importeEnCentimos(texto))
        }
    }

    @Test
    fun rechazaImportesInvalidosSinLanzarExcepciones() {
        for (texto in listOf("", "abc", "0", "-5", "1,234", "1.000,25", "1e2", "92233720368547758.08")) {
            assertNull(texto, importeEnCentimos(texto))
        }
    }
}
