package com.example.aplicacion.data

import com.example.aplicacion.domain.Categoria
import com.example.aplicacion.domain.Movimiento
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MovimientosRepository {
    // shortcut: los movimientos viven en memoria y se reinician al cerrar la aplicación.
    private val _movimientos = MutableStateFlow<List<Movimiento>>(
        listOf(
        Movimiento(50_000L, Categoria.INGRESO),
        Movimiento(200000_000L, Categoria.COMIDA),
        Movimiento(100_000L, Categoria.OCIO),
        Movimiento(30_000L, Categoria.VIVIENDA)
        )
    )

    fun obtenerMovimientos(): StateFlow<List<Movimiento>> {
        // Devolvemos los movimientos
        return _movimientos.asStateFlow()
    }

    fun addMovimiento(movimiento: Movimiento) {
        //añadir movimiento nuevo, actualización atómica y thread-safe
        _movimientos.update { actual -> actual + movimiento }
    }
}
