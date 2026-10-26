package com.example.aplicacion.ui.resumen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.example.aplicacion.domain.Movimiento
import com.example.aplicacion.domain.Resumen
import com.example.aplicacion.ui.theme.AplicacionTheme
import kotlinx.coroutines.flow.StateFlow
import java.math.BigDecimal
import java.text.NumberFormat
import java.util.Currency
import java.util.Locale

/**
 * Muestra los totales, el listado breve y ofrece volver al formulario o abrir todos los movimientos.
 *
 * @param resumen totales actuales, expresados en céntimos.
 * @param movimientos flujo observable cuya lista se recoge para mostrar cada movimiento.
 * @param onAgregarMovimiento vuelve al formulario para añadir otro movimiento.
 * @param onVerMovimientos abre la pantalla con el listado completo.
 * @param modifier ajustes visuales que recibe el contenedor principal.
 */
@Composable
fun ResumenScreen(
    resumen: Resumen,
    movimientos: StateFlow<List<Movimiento>>,
    onAgregarMovimiento: () -> Unit,
    onVerMovimientos: () -> Unit,
    modifier: Modifier = Modifier
) {
    val formato = NumberFormat.getCurrencyInstance(Locale.forLanguageTag("es-ES")).apply {
        currency = Currency.getInstance("EUR")
    }
    Column(
        modifier = modifier
            .widthIn(max = 600.dp)
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Resumen", style = MaterialTheme.typography.headlineLarge)
        HorizontalDivider()
        Text("Ingresos", style = MaterialTheme.typography.titleMedium)
        Text(formato.format(BigDecimal.valueOf(resumen.ingresosCentimos, 2)))
        Text("Gastos", style = MaterialTheme.typography.titleMedium)
        Text(formato.format(BigDecimal.valueOf(resumen.gastosCentimos, 2)))
        HorizontalDivider()
        Text("Flujo de caja", style = MaterialTheme.typography.titleMedium)
        Text(
            formato.format(BigDecimal.valueOf(resumen.flujoCentimos, 2)),
            style = MaterialTheme.typography.headlineMedium
        )
        HorizontalDivider()
        Text("Movimientos", style = MaterialTheme.typography.titleMedium)
        // StateFlow es el flujo; collectAsState obtiene su lista actual y actualiza Compose al cambiar.
        val listaMovimientos = movimientos.collectAsState().value
        // El bucle visita cada Movimiento de la lista y crea un Text para mostrarlo.
        for (movimiento in listaMovimientos) {
            Text(
                "${movimiento.categoria}: " +
                    formato.format(BigDecimal.valueOf(movimiento.importeCentimos, 2))
            )
        }
        Text("Ingresos menos gastos. No representa el saldo disponible.")
        Button(onClick = onAgregarMovimiento, modifier = Modifier.fillMaxWidth()) {
            Text("Añadir movimiento")
        }
        Button(onClick = onVerMovimientos, modifier = Modifier.fillMaxWidth()) {
            Text("Ver todos los movimientos")
        }
    }
}
