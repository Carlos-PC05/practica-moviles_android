package com.example.aplicacion.ui.resumen

import androidx.lifecycle.ViewModel
import com.example.aplicacion.data.MovimientosRepository
import com.example.aplicacion.domain.calcularResumen

class ResumenViewModel(repository: MovimientosRepository) : ViewModel() {
    val resumen = calcularResumen(repository.obtenerMovimientos().value)
}
