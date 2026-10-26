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
import com.example.aplicacion.ui.resumen.MovimientoFormScreen
import com.example.aplicacion.ui.resumen.ResumenScreen
import com.example.aplicacion.ui.resumen.ResumenViewModel
import com.example.aplicacion.ui.theme.AplicacionTheme

/** Actividad principal: crea el ViewModel y presenta el formulario o el resumen. */
class MainActivity : ComponentActivity() {
    private val resumenViewModel: ResumenViewModel by viewModels {
        viewModelFactory {
            initializer { ResumenViewModel(MovimientosRepository()) }
        }
    }

    /**
     * Configura Compose, observa el resumen y gestiona el cambio entre pantallas.
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
                    var mostrandoResumen by rememberSaveable { mutableStateOf(false) }
                    BackHandler(enabled = mostrandoResumen) { mostrandoResumen = false }

                    if (mostrandoResumen) {
                        ResumenScreen(
                            resumen = resumen,
                            onAgregarMovimiento = { mostrandoResumen = false },
                            modifier = Modifier.padding(innerPadding)
                        )
                    } else {
                        MovimientoFormScreen(
                            onGuardar = resumenViewModel::agregarMovimiento,
                            onVerResumen = { mostrandoResumen = true },
                            modifier = Modifier.padding(innerPadding)
                        )
                    }
                }
            }
        }
    }
}
