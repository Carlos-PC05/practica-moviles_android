package com.example.aplicacion.data

import com.example.aplicacion.domain.Categoria
import com.example.aplicacion.domain.Movimiento
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/** Mantiene y publica los movimientos de esta ejecución de la aplicación. */
class MovimientosRepository {
    // shortcut: los movimientos viven en memoria y se reinician al cerrar la aplicación.
    // Esta lista inicial contiene cuatro movimientos de ejemplo para probar el resumen.
    private val _movimientos = MutableStateFlow<List<Movimiento>>(
        listOf(
        Movimiento(50_000L, Categoria.INGRESO),
        Movimiento(200000_000L, Categoria.COMIDA),
        Movimiento(100_000L, Categoria.OCIO),
        Movimiento(30_000L, Categoria.VIVIENDA)
        )
    )

    /** Expone una vista observable de solo lectura de los movimientos actuales. */
    fun obtenerMovimientos(): StateFlow<List<Movimiento>> {
        return _movimientos.asStateFlow()
    }

    /** Añade un movimiento y publica una lista nueva a quienes observan el estado. */
    fun addMovimiento(movimiento: Movimiento) {
        _movimientos.update { actual -> actual + movimiento }
    }
}
