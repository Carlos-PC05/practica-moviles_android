package com.example.aplicacion.ui.resumen

import com.example.aplicacion.data.MovimientosRepository
import com.example.aplicacion.domain.Resumen
import org.junit.Assert.assertEquals
import org.junit.Test

class ResumenViewModelTest {
    @Test
    fun muestraElResumenDeLosDatosDeEjemplo() {
        val viewModel = ResumenViewModel(MovimientosRepository())

        assertEquals(Resumen(500_000L, 150_000L, 350_000L), viewModel.resumen)
    }
}
