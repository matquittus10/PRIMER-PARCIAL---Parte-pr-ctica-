package com.example.primerparcial_partepractica.Models

class Farmacia(
    var nombre: String,
    var direccion: String
) {
    fun registrarVenta(venta: Venta) {
        for (detalle in venta.detalles) {
            detalle.medicamento.stock -= detalle.cantidad
        }
    }

    fun consultarStock(medicamento: Medicamento): Int {
        return medicamento.stock
    }
}
