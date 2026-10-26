package com.example.aplicacion.ui.resumen

import androidx.lifecycle.ViewModel
import com.example.aplicacion.data.MovimientosRepository
import com.example.aplicacion.domain.Categoria
import com.example.aplicacion.domain.Movimiento
import com.example.aplicacion.domain.calcularResumen
import com.example.aplicacion.domain.importeEnCentimos
import kotlinx.coroutines.flow.map

/** Conecta los movimientos del repositorio con el resumen que muestra la interfaz. */
class ResumenViewModel(private val repository: MovimientosRepository) : ViewModel() {
    private val movimientos = repository.obtenerMovimientos()

    /** Resumen inicial para mostrar antes de recibir la primera emisión del flujo. */
    val resumenInicial = calcularResumen(movimientos.value)

    /** Recalcula el resumen cada vez que el repositorio publica una lista nueva. */
    val resumen = movimientos.map(::calcularResumen)

    /**
     * Valida un importe en euros y lo añade al repositorio.
     *
     * @param importe texto escrito en euros.
     * @param categoria categoría seleccionada para el movimiento.
     * @return `true` si se añadió; `false` si el importe no es válido.
     */
    fun agregarMovimiento(importe: String, categoria: Categoria): Boolean {
        val importeCentimos = importeEnCentimos(importe)

        if (importeCentimos != null) {
            repository.addMovimiento(Movimiento(importeCentimos, categoria))
            return true
        }

        return false
    }
}
