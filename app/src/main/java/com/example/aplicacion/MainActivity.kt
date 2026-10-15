package com.example.aplicacion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.aplicacion.data.MovimientosRepository
import com.example.aplicacion.ui.resumen.ResumenScreen
import com.example.aplicacion.ui.resumen.ResumenViewModel
import com.example.aplicacion.ui.theme.AplicacionTheme

class MainActivity : ComponentActivity() {
    private val resumenViewModel: ResumenViewModel by viewModels {
        viewModelFactory {
            initializer { ResumenViewModel(MovimientosRepository()) }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AplicacionTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ResumenScreen(
                        resumen = resumenViewModel.resumen,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}
