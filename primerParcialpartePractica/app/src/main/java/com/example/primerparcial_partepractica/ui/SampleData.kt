package com.example.primerparcial_partepractica.ui

import com.example.primerparcial_partepractica.Models.Cliente
import com.example.primerparcial_partepractica.Models.DetalleVenta
import com.example.primerparcial_partepractica.Models.Empleado
import com.example.primerparcial_partepractica.Models.Medicamento
import com.example.primerparcial_partepractica.Models.Venta
import java.util.Calendar
import java.util.Date

object SampleData {

    private fun createDate(year: Int, month: Int, day: Int): Date {
        val calendar = Calendar.getInstance()
        calendar.set(year, month - 1, day, 0, 0, 0)
        return calendar.time
    }

    val medicamentosList = mutableListOf(
        Medicamento(
            id = 1,
            nombre = "Paracetamol 500mg",
            laboratorio = "Lab. Bagó",
            precio = 12.50,
            stock = 40,
            fechaVencimiento = createDate(2027, 12, 1)
        ),
        Medicamento(
            id = 2,
            nombre = "Ibuprofeno 400mg",
            laboratorio = "Lab. Inti",
            precio = 18.00,
            stock = 3,
            fechaVencimiento = createDate(2026, 5, 15)
        ),
        Medicamento(
            id = 3,
            nombre = "Amoxicilina 500mg",
            laboratorio = "Lab. Vita",
            precio = 25.00,
            stock = 15,
            fechaVencimiento = createDate(2025, 8, 20)
        ),
        Medicamento(
            id = 4,
            nombre = "Omeprazol 20mg",
            laboratorio = "Lab. Alcos",
            precio = 10.00,
            stock = 0,
            fechaVencimiento = createDate(2026, 10, 10)
        ),
        Medicamento(
            id = 5,
            nombre = "Aspirina 100mg",
            laboratorio = "Lab. Bayer",
            precio = 8.50,
            stock = 50,
            fechaVencimiento = createDate(2028, 11, 5)
        ),
        Medicamento(
            id = 6,
            nombre = "Losartán 50mg",
            laboratorio = "Lab. Bagó",
            precio = 22.00,
            stock = 4,
            fechaVencimiento = createDate(2026, 4, 30)
        )
    )

    val clientesList = listOf(
        Cliente(1, "María Quispe", "71234567"),
        Cliente(2, "Carlos Mamani", "72345678"),
        Cliente(3, "Lucía Arteaga", "73456789"),
        Cliente(4, "Pedro Gómez", "74567890"),
        Cliente(5, "Sofía Morales", "75678901")
    )

    val empleadosList = listOf(
        Empleado(1, "Juan Pérez", "Farmacéutico"),
        Empleado(2, "Ana Flores", "Cajera")
    )

    val ventasList = mutableListOf(
        Venta(
            id = 101,
            fecha = createDate(2026, 10, 5),
            total = 45.50,
            cliente = clientesList[0],
            empleado = empleadosList[0],
            detalles = listOf(
                DetalleVenta(2, 25.00, medicamentosList[0]),
                DetalleVenta(1, 20.50, medicamentosList[1])
            )
        ),
        Venta(
            id = 102,
            fecha = createDate(2026, 10, 5),
            total = 18.00,
            cliente = clientesList[1],
            empleado = empleadosList[1],
            detalles = listOf(
                DetalleVenta(1, 18.00, medicamentosList[1])
            )
        ),
        Venta(
            id = 103,
            fecha = createDate(2026, 10, 5),
            total = 75.00,
            cliente = clientesList[2],
            empleado = empleadosList[0],
            detalles = listOf(
                DetalleVenta(3, 75.00, medicamentosList[2])
            )
        ),
        Venta(
            id = 104,
            fecha = createDate(2026, 10, 5),
            total = 32.50,
            cliente = clientesList[3],
            empleado = empleadosList[1],
            detalles = listOf(
                DetalleVenta(1, 22.00, medicamentosList[5]),
                DetalleVenta(1, 10.50, medicamentosList[3])
            )
        ),
        Venta(
            id = 105,
            fecha = createDate(2026, 10, 5),
            total = 59.00,
            cliente = clientesList[4],
            empleado = empleadosList[0],
            detalles = listOf(
                DetalleVenta(2, 50.00, medicamentosList[2]),
                DetalleVenta(1, 9.00, medicamentosList[4])
            )
        )
    )
}
