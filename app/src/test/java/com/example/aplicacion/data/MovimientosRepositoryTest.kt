package com.example.aplicacion.data

import com.example.aplicacion.domain.Categoria
import com.example.aplicacion.domain.Movimiento
import org.junit.Assert.assertEquals
import org.junit.Test

class MovimientosRepositoryTest {
    @Test
    fun agregarMovimientoActualizaLaLista() {
        val repository = MovimientosRepository()
        val iniciales = repository.obtenerMovimientos().value
        val nuevo = Movimiento(2_500L, Categoria.OCIO)

        repository.addMovimiento(nuevo)

        assertEquals(iniciales + nuevo, repository.obtenerMovimientos().value)
    }
}
