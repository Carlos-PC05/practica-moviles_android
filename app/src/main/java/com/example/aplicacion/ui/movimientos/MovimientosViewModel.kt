package com.example.aplicacion.ui.movimientos

import androidx.lifecycle.ViewModel
import com.example.aplicacion.data.MovimientosRepository
import com.example.aplicacion.domain.Movimiento
import kotlinx.coroutines.flow.StateFlow

/**
 * Expone a la pantalla el flujo de movimientos mantenido por el repositorio.
 *
 * @param repository origen observable de los movimientos.
 */
class MovimientosViewModel(private val repository: MovimientosRepository) : ViewModel() {
    /** Lista observable que la pantalla recoge para redibujarse tras cada cambio. */
    val movimientos: StateFlow<List<Movimiento>> = repository.obtenerMovimientos()

    /** Envía al repositorio el movimiento que debe quitar de la lista observable. */
    fun deleteMovimiento(movimiento: Movimiento) {
        repository.deleteMovimiento(movimiento)
    }
}
