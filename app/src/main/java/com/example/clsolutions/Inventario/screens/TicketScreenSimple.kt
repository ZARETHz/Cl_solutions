@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.clsolutions.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun TicketScreenSimple(id: String) {

    val db = FirebaseFirestore.getInstance()

    var cliente by remember { mutableStateOf("") }
    var marca by remember { mutableStateOf("") }
    var modelo by remember { mutableStateOf("") }
    var estado by remember { mutableStateOf("") }
    var fecha by remember { mutableStateOf("") }

    LaunchedEffect(id) {

        db.collection("equipos")
            .document(id)
            .get()
            .addOnSuccessListener { doc ->

                cliente = doc.getString("cliente") ?: ""
                marca = doc.getString("marca") ?: ""
                modelo = doc.getString("modelo") ?: ""
                estado = doc.getString("estado") ?: ""
                fecha = doc.getString("fecha") ?: ""

            }

    }

    Scaffold(

        topBar = {
            TopAppBar(
                title = { Text("Ticket de reparación") }
            )
        }

    ) { padding ->

        Column(

            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),

            verticalArrangement = Arrangement.spacedBy(10.dp)

        ) {

            Text(
                "🧾 CL SOLUTIONS",
                style = MaterialTheme.typography.headlineSmall
            )

            HorizontalDivider()

            Text("Folio: $id")
            Text("Cliente: $cliente")
            Text("Marca: $marca")
            Text("Modelo: $modelo")
            Text("Estado: $estado")
            Text("Fecha: $fecha")

            HorizontalDivider()

            Text("Gracias por su confianza 🙌")

        }

    }

}