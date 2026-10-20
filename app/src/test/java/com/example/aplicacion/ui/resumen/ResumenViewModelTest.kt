package com.example.aplicacion.ui.resumen

import com.example.aplicacion.data.MovimientosRepository
import com.example.aplicacion.domain.Resumen
import org.junit.Assert.assertEquals
import org.junit.Test

class ResumenViewModelTest {
    @Test
    fun muestraElResumenDeLosMovimientosDelRepositorio() {
        val viewModel = ResumenViewModel(MovimientosRepository())

        assertEquals(Resumen(50_000L, 200_130_000L, -200_080_000L), viewModel.resumen)
    }
}
