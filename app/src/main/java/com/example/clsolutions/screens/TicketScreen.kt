package com.example.clsolutions.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import com.example.clsolutions.model.Equipo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TicketScreen(
    equipo: Equipo
) {

    Scaffold(

        topBar = {

            TopAppBar(
                title = {
                    Text("Ticket de reparación")
                }
            )

        }

    ) { padding ->

        Column(

            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),

            verticalArrangement = Arrangement.spacedBy(12.dp)

        ) {

            Text(
                text = "🧾 CL SOLUTIONS",
                style = MaterialTheme.typography.headlineSmall
            )

            HorizontalDivider()

            Text("Folio: ${equipo.id}")
            Text("Cliente: ${equipo.cliente}")
            Text("Teléfono: ${equipo.telefono}")
            Text("Marca: ${equipo.marca}")
            Text("Modelo: ${equipo.modelo}")
            Text("Falla: ${equipo.falla}")
            Text("Estado: ${equipo.estado}")
            Text("Fecha: ${equipo.fecha}")

            HorizontalDivider()

            Text(
                text = "Gracias por confiar en nosotros 🙌"
            )

        }

    }

}