package com.example.aplicacion.domain

import org.junit.Assert.assertEquals
import org.junit.Test

class ResumenTest {
    @Test
    fun ingresoYGastoDelEjemplo() {
        val movimientos = listOf(
            Movimiento(50_000L, Categoria.INGRESO),
            Movimiento(20_000L, Categoria.COMIDA)
        )

        assertEquals(Resumen(50_000L, 20_000L, 30_000L), calcularResumen(movimientos))
    }

    @Test
    fun sinMovimientosLosTotalesSonCero() {
        assertEquals(Resumen(0L, 0L, 0L), calcularResumen(emptyList()))
    }

    @Test
    fun coincideConAgrupacionPorCategoria() {
        val movimientos = Categoria.entries.flatMapIndexed { indice, categoria ->
            listOf(Movimiento((indice + 1) * 101L, categoria), Movimiento(37L, categoria))
        }
        val porCategoria = movimientos.groupBy { it.categoria }
            .mapValues { (_, registros) -> registros.sumOf { it.importeCentimos } }
        val ingresos = porCategoria.getValue(Categoria.INGRESO)
        val gastos = porCategoria.filterKeys { it != Categoria.INGRESO }.values.sum()

        assertEquals(Resumen(ingresos, gastos, ingresos - gastos), calcularResumen(movimientos))
    }

    @Test(expected = IllegalArgumentException::class)
    fun rechazaImporteCero() {
        Movimiento(0L, Categoria.COMIDA)
    }
}
