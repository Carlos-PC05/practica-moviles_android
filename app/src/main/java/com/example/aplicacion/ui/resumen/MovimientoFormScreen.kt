package com.example.aplicacion.ui.resumen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.aplicacion.domain.Categoria

/**
 * Captura un importe y una categoría, informa si el guardado falla y permite abrir el resumen.
 *
 * Al guardar con éxito limpia el campo; si el importe no es válido, conserva el texto y muestra un error.
 *
 * @param onGuardar recibe el texto y la categoría; devuelve `true` si el ViewModel lo acepta.
 * @param onVerResumen cambia a la pantalla con los totales actualizados.
 * @param modifier ajustes visuales que recibe el contenedor principal.
 */
@Preview
@Composable
fun MovimientoFormScreen(
    onGuardar: (String, Categoria) -> Boolean,
    onVerResumen: () -> Unit,
    updateMovimiento: Boolean,
    modifier: Modifier = Modifier
) {
    var importe by rememberSaveable { mutableStateOf("") }
    var categoria by rememberSaveable { mutableStateOf(Categoria.OTROS) }
    var categoriasAbiertas by rememberSaveable { mutableStateOf(false) }
    var mostrarError by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = modifier
            .widthIn(max = 600.dp)
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .imePadding()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Añadir movimiento", style = MaterialTheme.typography.headlineLarge)
        OutlinedTextField(
            value = importe,
            onValueChange = {
                importe = it
                mostrarError = false
            },
            label = { Text("Importe en euros") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            singleLine = true,
            isError = mostrarError,
            supportingText = if (mostrarError) {
                { Text("Introduce un importe positivo con hasta dos decimales.") }
            } else {
                null
            },
            modifier = Modifier.fillMaxWidth()
        )

        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text("Categoría", style = MaterialTheme.typography.labelLarge)
            Box {
                OutlinedButton(
                    onClick = { categoriasAbiertas = true },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(categoria.name.lowercase().replaceFirstChar { it.titlecase() })
                }
                DropdownMenu(
                    expanded = categoriasAbiertas,
                    onDismissRequest = { categoriasAbiertas = false }
                ) {
                    Categoria.entries.forEach { opcion ->
                        DropdownMenuItem(
                            text = { Text(opcion.name.lowercase().replaceFirstChar { it.titlecase() }) },
                            onClick = {
                                categoria = opcion
                                categoriasAbiertas = false
                            }
                        )
                    }
                }
            }
        }

        if(updateMovimiento){
            Button(
                onClick = {
                    mostrarError = !onGuardar(importe, categoria)
                    if (!mostrarError) importe = ""
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Actualizar movimiento")
            }
            return
        }

        Button(
            onClick = {
                mostrarError = !onGuardar(importe, categoria)
                if (!mostrarError) importe = ""
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Guardar movimiento")
        }
        TextButton(onClick = onVerResumen, modifier = Modifier.fillMaxWidth()) {
            Text("Ver resumen")
        }
    }
}
