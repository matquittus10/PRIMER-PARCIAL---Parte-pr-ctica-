package com.example.primerparcial_partepractica.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Medication
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.FloatingActionButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.primerparcial_partepractica.Models.Medicamento
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

enum class FilterOption {
    TODOS,
    CON_STOCK,
    POCO_STOCK_O_VENCE
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MedicamentosScreen(
    modifier: Modifier = Modifier,
    medicamentosList: MutableList<Medicamento> = remember { mutableStateListOf(*SampleData.medicamentosList.toTypedArray()) },
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedFilter by remember { mutableStateOf(FilterOption.TODOS) }
    var isSearchVisible by remember { mutableStateOf(value = true) }
    var showAddDialog by remember { mutableStateOf(value = false) }

    val filteredMedicamentos = remember(searchQuery, selectedFilter, medicamentosList.size) {
        medicamentosList.filter { med ->
            val matchesSearch = searchQuery.isEmpty() ||
                    med.nombre.contains(searchQuery, ignoreCase = true) ||
                    med.laboratorio.contains(searchQuery, ignoreCase = true)

            val matchesFilter = when (selectedFilter) {
                FilterOption.TODOS -> true
                FilterOption.CON_STOCK -> med.stock > 0
                FilterOption.POCO_STOCK_O_VENCE -> (med.stock in 1..5) || med.stock == 0
            }

            matchesSearch && matchesFilter
        }
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Medicamentos",
                        fontWeight = FontWeight.Bold
                    )
                },
                actions = {
                    IconButton(onClick = { isSearchVisible = !isSearchVisible }) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Buscar"
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
                    contentDescription = "Agregar Medicamento"
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
            // Buscador OutlinedTextField
            AnimatedVisibility(visible = isSearchVisible) {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    placeholder = { Text("Buscar medicamento...") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = null
                        )
                    },
                    trailingIcon = {
                        if (searchQuery.isNotEmpty()) {
                            IconButton(onClick = { searchQuery = "" }) {
                                Icon(
                                    imageVector = Icons.Default.Clear,
                                    contentDescription = "Limpiar"
                                )
                            }
                        }
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp)
                )
            }

            // Filter Chips Row
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item {
                    FilterChip(
                        selected = selectedFilter == FilterOption.TODOS,
                        onClick = { selectedFilter = FilterOption.TODOS },
                        label = { Text("Todos") }
                    )
                }
                item {
                    FilterChip(
                        selected = selectedFilter == FilterOption.CON_STOCK,
                        onClick = { selectedFilter = FilterOption.CON_STOCK },
                        label = { Text("Con stock") }
                    )
                }
                item {
                    FilterChip(
                        selected = selectedFilter == FilterOption.POCO_STOCK_O_VENCE,
                        onClick = { selectedFilter = FilterOption.POCO_STOCK_O_VENCE },
                        label = { Text("Poco stock / Vence") },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MaterialTheme.colorScheme.errorContainer,
                            selectedLabelColor = MaterialTheme.colorScheme.onErrorContainer
                        )
                    )
                }
            }

            // LazyColumn para Lista de Medicamentos
            if (filteredMedicamentos.isEmpty()) {
                Spacer(modifier = Modifier.height(32.dp))
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.Medication,
                        contentDescription = null,
                        modifier = Modifier.size(64.dp),
                        tint = MaterialTheme.colorScheme.outline
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "No se encontraron medicamentos",
                        color = MaterialTheme.colorScheme.outline,
                        fontSize = 16.sp
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    contentPadding = PaddingValues(bottom = 80.dp)
                ) {
                    items(filteredMedicamentos, key = { it.id }) { med ->
                        MedicamentoCard(medicamento = med)
                    }
                }
            }
        }
    }

    if (showAddDialog) {
        AddMedicamentoDialog(
            onDismiss = { showAddDialog = false },
            onConfirm = { nuevoMed ->
                medicamentosList.add(nuevoMed)
                showAddDialog = false
            }
        )
    }
}

@Composable
fun MedicamentoCard(
    medicamento: Medicamento,
    modifier: Modifier = Modifier
) {
    val dateFormat = remember { SimpleDateFormat("MM/yyyy", Locale.getDefault()) }
    val isLowStock = medicamento.stock in 1..5
    val isOutOfStock = medicamento.stock == 0

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Nombre del medicamento
            Text(
                text = medicamento.nombre,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            // Laboratorio
            Text(
                text = medicamento.laboratorio,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Precio y Stock
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = String.format(Locale.getDefault(), "Bs %.2f", medicamento.precio),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.primary
                )

                if (isLowStock || isOutOfStock) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Warning,
                            contentDescription = "Poco Stock",
                            tint = if (isOutOfStock) Color.Red else Color(0xFFFF8C00),
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = if (isOutOfStock) "Sin stock" else "⚠ Stock: ${medicamento.stock}",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (isOutOfStock) Color.Red else Color(0xFFFF8C00)
                        )
                    }
                } else {
                    Text(
                        text = "Stock: ${medicamento.stock}",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            // Fecha de Vencimiento
            Text(
                text = "Vence: ${dateFormat.format(medicamento.fechaVencimiento)}",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
            )
        }
    }
}

@Composable
fun AddMedicamentoDialog(
    onDismiss: () -> Unit,
    onConfirm: (Medicamento) -> Unit
) {
    var nombre by remember { mutableStateOf("") }
    var laboratorio by remember { mutableStateOf("") }
    var precioText by remember { mutableStateOf("") }
    var stockText by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Nuevo Medicamento") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = nombre,
                    onValueChange = { nombre = it },
                    label = { Text("Nombre") },
                    singleLine = true
                )
                OutlinedTextField(
                    value = laboratorio,
                    onValueChange = { laboratorio = it },
                    label = { Text("Laboratorio") },
                    singleLine = true
                )
                OutlinedTextField(
                    value = precioText,
                    onValueChange = { precioText = it },
                    label = { Text("Precio (Bs)") },
                    singleLine = true
                )
                OutlinedTextField(
                    value = stockText,
                    onValueChange = { stockText = it },
                    label = { Text("Stock") },
                    singleLine = true
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val precio = precioText.toDoubleOrNull() ?: 0.0
                    val stock = stockText.toIntOrNull() ?: 0
                    val cal = Calendar.getInstance()
                    cal.add(Calendar.YEAR, 1)
                    val med = Medicamento(
                        id = (100..999).random(),
                        nombre = nombre.ifBlank { "Nuevo Medicamento" },
                        laboratorio = laboratorio.ifBlank { "Laboratorio Generico" },
                        precio = precio,
                        stock = stock,
                        fechaVencimiento = cal.time
                    )
                    onConfirm(med)
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
