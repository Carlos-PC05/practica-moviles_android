package com.example.aplicacion.ui.resumen

import androidx.lifecycle.ViewModel
import com.example.aplicacion.data.MovimientosRepository
import com.example.aplicacion.domain.Categoria
import com.example.aplicacion.domain.Movimiento
import com.example.aplicacion.domain.calcularResumen
import com.example.aplicacion.domain.importeEnCentimos
import kotlinx.coroutines.flow.map

class ResumenViewModel(private val repository: MovimientosRepository) : ViewModel() {
    private val movimientos = repository.obtenerMovimientos()
    val resumenInicial = calcularResumen(movimientos.value)
    val resumen = movimientos.map(::calcularResumen)

    fun agregarMovimiento(importe: String, categoria: Categoria): Boolean {
        val importeCentimos = importeEnCentimos(importe)

        if (importeCentimos != null) {
            repository.addMovimiento(Movimiento(importeCentimos, categoria))
            return true
        }

        return false
    }
}
