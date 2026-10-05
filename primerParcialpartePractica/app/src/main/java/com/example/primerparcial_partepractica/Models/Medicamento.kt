package com.example.primerparcial_partepractica.Models

import java.util.Date

class Medicamento(
    val id: Int,
    var nombre: String,
    var laboratorio: String,
    var precio: Double,
    var stock: Int,
    var fechaVencimiento: Date
) {
    fun actualizarStock(nuevaCantidad: Int) {
        this.stock = nuevaCantidad
    }
}
