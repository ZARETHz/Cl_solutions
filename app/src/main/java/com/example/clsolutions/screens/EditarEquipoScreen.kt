package com.example.clsolutions.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.firebase.firestore.FirebaseFirestore

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditarEquipoScreen(
    id: String,
    onBack: () -> Unit
) {

    val db = FirebaseFirestore.getInstance()

    var cliente by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var marca by remember { mutableStateOf("") }
    var modelo by remember { mutableStateOf("") }
    var falla by remember { mutableStateOf("") }
    var estado by remember { mutableStateOf("") }
    var fecha by remember { mutableStateOf("") }

    LaunchedEffect(id) {
        db.collection("equipos")
            .document(id)
            .get()
            .addOnSuccessListener { doc ->
                cliente = doc.getString("cliente") ?: ""
                telefono = doc.getString("telefono") ?: ""
                marca = doc.getString("marca") ?: ""
                modelo = doc.getString("modelo") ?: ""
                falla = doc.getString("falla") ?: ""
                estado = doc.getString("estado") ?: ""
                fecha = doc.getString("fecha") ?: ""
            }
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Editar equipo") })
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            OutlinedTextField(value = cliente, onValueChange = { cliente = it }, label = { Text("Cliente") })
            OutlinedTextField(value = telefono, onValueChange = { telefono = it }, label = { Text("Teléfono") })
            OutlinedTextField(value = marca, onValueChange = { marca = it }, label = { Text("Marca") })
            OutlinedTextField(value = modelo, onValueChange = { modelo = it }, label = { Text("Modelo") })
            OutlinedTextField(value = falla, onValueChange = { falla = it }, label = { Text("Falla") })
            OutlinedTextField(value = fecha, onValueChange = { fecha = it }, label = { Text("Fecha") })

            Button(
                onClick = {
                    db.collection("equipos")
                        .document(id)
                        .update(
                            mapOf(
                                "cliente" to cliente,
                                "telefono" to telefono,
                                "marca" to marca,
                                "modelo" to modelo,
                                "falla" to falla,
                                "fecha" to fecha,
                                "estado" to estado
                            )
                        )
                    onBack()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Guardar cambios")
            }

            Button(
                onClick = onBack,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Cancelar")
            }

        }
    }
}