package com.example.aplicacion.data

import com.example.aplicacion.domain.Categoria
import com.example.aplicacion.domain.Movimiento

class MovimientosRepository {
    // shortcut: datos de ejemplo locales; sustituir por Firestore al implementar persistencia.
    fun obtenerMovimientos(): List<Movimiento> = listOf(
        Movimiento(500_000L, Categoria.INGRESO),
        Movimiento(20_000L, Categoria.COMIDA),
        Movimiento(100_000L, Categoria.OCIO),
        Movimiento(30_000L, Categoria.VIVIENDA)
    )
}
