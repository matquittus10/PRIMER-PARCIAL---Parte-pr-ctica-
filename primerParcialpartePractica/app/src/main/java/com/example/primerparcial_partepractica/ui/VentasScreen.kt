package com.example.primerparcial_partepractica.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.automirrored.filled.ReceiptLong
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.PointOfSale
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.primerparcial_partepractica.Models.Cliente
import com.example.primerparcial_partepractica.Models.Empleado
import com.example.primerparcial_partepractica.Models.Venta
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VentasScreen(
    modifier: Modifier = Modifier,
    ventasList: MutableList<Venta> = remember { mutableStateListOf(*SampleData.ventasList.toTypedArray()) },
) {
    var showAddDialog by remember { mutableStateOf(value = false) }
    var selectedVentaDetail by remember { mutableStateOf<Venta?>(null) }

    // Cálculos para la Card de resumen
    val totalVentasHoy = ventasList.size
    val sumaTotalHoy = ventasList.sumOf { it.total }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Ventas",
                        fontWeight = FontWeight.Bold
                    )
                },
                actions = {
                    IconButton(onClick = { /* Filtrar por fecha */ }) {
                        Icon(
                            imageVector = Icons.Default.DateRange,
                            contentDescription = "Filtrar por fecha"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddDialog = true },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Nueva Venta"
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            // Card de resumen
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.PointOfSale,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSecondaryContainer,
                        modifier = Modifier.padding(end = 12.dp)
                    )
                    Text(
                        text = "Hoy · $totalVentasHoy ventas · Bs ${String.format(Locale.getDefault(), "%.2f", sumaTotalHoy)}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }
            }

            // LazyColumn para Lista de Ventas
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(bottom = 80.dp)
            ) {
                items(ventasList, key = { it.id }) { venta ->
                    VentaCardItem(
                        venta = venta,
                        onClick = { selectedVentaDetail = venta }
                    )
                }
            }
        }
    }

    if (showAddDialog) {
        AddVentaDialog(
            onDismiss = { showAddDialog = false },
            onConfirm = { nuevaVenta ->
                ventasList.add(0, nuevaVenta)
                showAddDialog = false
            }
        )
    }

    selectedVentaDetail?.let { venta ->
        VentaDetailDialog(
            venta = venta,
            onDismiss = { selectedVentaDetail = null }
        )
    }
}

@Composable
fun VentaCardItem(
    venta: Venta,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val dateFormat = remember { SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()) }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Header Row: Venta #ID y Fecha
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Venta #${venta.id}",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = dateFormat.format(venta.fecha),
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            // Cliente
            Text(
                text = "Cliente: ${venta.cliente.nombre}",
                fontSize = 15.sp,
                color = MaterialTheme.colorScheme.onSurface
            )

            // Atendió / Empleado
            Text(
                text = "Atendió: ${venta.empleado.nombre}",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )

            Spacer(modifier = Modifier.height(8.dp))
            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
            Spacer(modifier = Modifier.height(8.dp))

            // Total y Chevron
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Total: Bs ${String.format(Locale.getDefault(), "%.2f", venta.total)}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = "Ver detalle",
                    tint = MaterialTheme.colorScheme.outline
                )
            }
        }
    }
}

@Composable
fun AddVentaDialog(
    onDismiss: () -> Unit,
    onConfirm: (Venta) -> Unit
) {
    var clienteNombre by remember { mutableStateOf("") }
    var totalText by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Registrar Venta") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = clienteNombre,
                    onValueChange = { clienteNombre = it },
                    label = { Text("Nombre del Cliente") },
                    singleLine = true
                )
                OutlinedTextField(
                    value = totalText,
                    onValueChange = { totalText = it },
                    label = { Text("Monto Total (Bs)") },
                    singleLine = true
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val total = totalText.toDoubleOrNull() ?: 0.0
                    val nuevaVenta = Venta(
                        id = (106..999).random(),
                        fecha = Date(),
                        total = total,
                        cliente = Cliente(id = (10..99).random(), nombre = clienteNombre.ifBlank { "Cliente Ocasional" }, telefono = "70000000"),
                        empleado = Empleado(id = 1, nombre = "Juan Pérez", cargo = "Farmacéutico"),
                        detalles = emptyList()
                    )
                    onConfirm(nuevaVenta)
                }
            ) {
                Text("Guardar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}

@Composable
fun VentaDetailDialog(
    venta: Venta,
    onDismiss: () -> Unit
) {
    val dateFormat = remember { SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(imageVector = Icons.AutoMirrored.Filled.ReceiptLong, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Detalle Venta #${venta.id}")
            }
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text("Fecha: ${dateFormat.format(venta.fecha)}")
                Text("Cliente: ${venta.cliente.nombre}")
                Text("Atendió: ${venta.empleado.nombre}")
                HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))
                Text("Detalles de Productos:", fontWeight = FontWeight.Bold)
                if (venta.detalles.isEmpty()) {
                    Text("Venta rápida sin desglose de productos")
                } else {
                    venta.detalles.forEach { det ->
                        Text("• ${det.medicamento.nombre} x${det.cantidad} - Bs ${det.subtotal}")
                    }
                }
                HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))
                Text(
                    text = "Total Final: Bs ${String.format(Locale.getDefault(), "%.2f", venta.total)}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Cerrar")
            }
        }
    )
}
