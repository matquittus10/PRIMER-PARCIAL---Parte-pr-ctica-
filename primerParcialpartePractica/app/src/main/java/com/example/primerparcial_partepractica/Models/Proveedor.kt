package com.example.primerparcial_partepractica.Models

class Proveedor(
    val id: Int,
    var nombre: String,
    var contacto: String
) {
    fun suministrarMedicamento(medicamento: Medicamento, cantidad: Int) {
        medicamento.stock += cantidad
    }
}
