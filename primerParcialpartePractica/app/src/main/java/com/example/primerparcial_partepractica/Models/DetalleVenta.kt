package com.example.primerparcial_partepractica.Models

class DetalleVenta(
    var cantidad: Int,
    var subtotal: Double,
    val medicamento: Medicamento
) {
    fun calcularSubtotal(): Double {
        subtotal = cantidad * medicamento.precio
        return subtotal
    }
}
