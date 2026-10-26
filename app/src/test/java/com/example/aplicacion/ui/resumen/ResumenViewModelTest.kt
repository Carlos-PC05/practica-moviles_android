package com.example.aplicacion.ui.resumen

import com.example.aplicacion.data.MovimientosRepository
import com.example.aplicacion.domain.Categoria
import com.example.aplicacion.domain.Resumen
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ResumenViewModelTest {
    @Test
    fun actualizaElResumenCuandoSeAnadeUnMovimiento() = runBlocking {
        val repository = MovimientosRepository()
        val viewModel = ResumenViewModel(repository)

        assertEquals(Resumen(50_000L, 200_130_000L, -200_080_000L), viewModel.resumen.first())

        assertTrue(viewModel.agregarMovimiento("25.00", Categoria.OCIO))

        assertEquals(Resumen(50_000L, 200_132_500L, -200_082_500L), viewModel.resumen.first())
    }

    @Test
    fun rechazaImporteInvalidoSinCambiarElResumen() = runBlocking {
        val viewModel = ResumenViewModel(MovimientosRepository())

        assertFalse(viewModel.agregarMovimiento("abc", Categoria.OCIO))

        assertEquals(Resumen(50_000L, 200_130_000L, -200_080_000L), viewModel.resumen.first())
    }
}
