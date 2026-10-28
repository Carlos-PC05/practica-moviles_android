package com.example.aplicacion.ui.movimientos

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.aplicacion.domain.Movimiento
import kotlinx.coroutines.flow.StateFlow
import java.math.BigDecimal
import java.text.NumberFormat
import java.util.Currency
import java.util.Locale

/**
 * Muestra la lista observable de movimientos con importes en euros y permite volver al resumen.
 *
 * @param movimientos flujo que publica la lista actual y sus cambios.
 * @param onVolverAlResumen acción que solicita regresar a la pantalla de resumen.
 * @param onEliminarMovimiento acción que recibe y elimina el movimiento de una fila.
 * @param modifier ajustes visuales que recibe el contenedor principal.
 */
@Composable
fun MovimientosScreen(
    movimientos: StateFlow<List<Movimiento>>,
    onVolverAlResumen: () -> Unit,
    onEliminarMovimiento: (Movimiento) -> Unit,
    modifier: Modifier = Modifier
) {
    val listaMovimientos = movimientos.collectAsState().value
    val formato = NumberFormat.getCurrencyInstance(Locale.forLanguageTag("es-ES")).apply {
        currency = Currency.getInstance("EUR")
    }

    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        // Si no hay movimientos, mostramos "No hay movimientos" y un botón para volver a la página del resumen
        if(listaMovimientos.isEmpty()) {
            // <<No hay movimientos>> con estilo de cabecera
            Text("No hay movimientos", style = MaterialTheme.typography.headlineLarge)
            // Botón para volver al resumen
            Button(onClick = onVolverAlResumen, modifier = Modifier.fillMaxWidth()) {
                Text("Volver al resumen")
            }
            return
        }

        Text("Movimientos", style = MaterialTheme.typography.headlineLarge)
        for (movimiento in listaMovimientos) {
            Text(
                "${movimiento.categoria}: " +
                    formato.format(BigDecimal.valueOf(movimiento.importeCentimos, 2))
            )
            Button(
                onClick = { onEliminarMovimiento(movimiento) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Eliminar movimiento")
            }
        }
        Button(onClick = onVolverAlResumen, modifier = Modifier.fillMaxWidth()) {
            Text("Volver al resumen")
        }
    }
}
