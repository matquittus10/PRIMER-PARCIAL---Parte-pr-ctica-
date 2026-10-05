package com.example.primerparcial_partepractica.Models

import java.util.Date

class Venta(
    val id: Int,
    val fecha: Date,
    var total: Double = 0.0,
    val cliente: Cliente,
    val empleado: Empleado,
    val detalles: List<DetalleVenta> = emptyList()
) {
    fun calcularTotal(): Double {
        total = detalles.sumOf { it.subtotal }
        return total
    }
}
