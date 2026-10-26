package com.example.aplicacion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.aplicacion.data.MovimientosRepository
import com.example.aplicacion.ui.movimientos.MovimientosScreen
import com.example.aplicacion.ui.movimientos.MovimientosViewModel
import com.example.aplicacion.ui.resumen.MovimientoFormScreen
import com.example.aplicacion.ui.resumen.ResumenScreen
import com.example.aplicacion.ui.resumen.ResumenViewModel
import com.example.aplicacion.ui.theme.AplicacionTheme

/** Identifica la pantalla que está visible dentro de la actividad. */
private enum class Pantalla { FORMULARIO, RESUMEN, MOVIMIENTOS }

/** Actividad principal: crea los ViewModel y muestra el formulario, el resumen o los movimientos. */
class MainActivity : ComponentActivity() {
    /** Repositorio compartido para que las dos pantallas observen los mismos movimientos. */
    private val movimientosRepository = MovimientosRepository()

    private val resumenViewModel: ResumenViewModel by viewModels {
        viewModelFactory {
            initializer { ResumenViewModel(movimientosRepository) }
        }
    }

    private val movimientosViewModel: MovimientosViewModel by viewModels {
        viewModelFactory {
            initializer { MovimientosViewModel(movimientosRepository) }
        }
    }

    /**
     * Configura Compose, observa el resumen y navega entre formulario, resumen y listado.
     *
     * El botón Atrás vuelve del listado al resumen y del resumen al formulario.
     *
     * @param savedInstanceState estado anterior de la actividad, si Android lo conserva.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AplicacionTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val resumen = resumenViewModel.resumen.collectAsState(
                        initial = resumenViewModel.resumenInicial
                    ).value
                    var pantalla by rememberSaveable { mutableStateOf(Pantalla.FORMULARIO) }
                    BackHandler(enabled = pantalla != Pantalla.FORMULARIO) {
                        pantalla = when (pantalla) {
                            Pantalla.MOVIMIENTOS -> Pantalla.RESUMEN
                            Pantalla.RESUMEN -> Pantalla.FORMULARIO
                            Pantalla.FORMULARIO -> Pantalla.FORMULARIO
                        }
                    }

                    when (pantalla) {
                        Pantalla.FORMULARIO -> MovimientoFormScreen(
                            onGuardar = resumenViewModel::agregarMovimiento,
                            onVerResumen = { pantalla = Pantalla.RESUMEN },
                            modifier = Modifier.padding(innerPadding)
                        )
                        Pantalla.RESUMEN -> ResumenScreen(
                            resumen = resumen,
                            movimientos = resumenViewModel.mostrarMovimientos(),
                            onAgregarMovimiento = { pantalla = Pantalla.FORMULARIO },
                            onVerMovimientos = { pantalla = Pantalla.MOVIMIENTOS },
                            modifier = Modifier.padding(innerPadding)
                        )
                        Pantalla.MOVIMIENTOS -> MovimientosScreen(
                            movimientos = movimientosViewModel.movimientos,
                            onVolverAlResumen = { pantalla = Pantalla.RESUMEN },
                            onEliminarMovimiento = movimientosViewModel::deleteMovimiento,
                            modifier = Modifier.padding(innerPadding)
                        )
                    }
                }
            }
        }
    }
}
