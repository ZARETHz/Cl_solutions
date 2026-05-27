package com.example.clsolutions.screens

import android.content.Intent
import android.net.Uri

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete

import androidx.compose.material3.*

import androidx.compose.runtime.*

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

import com.example.clsolutions.model.Equipo
import com.google.firebase.firestore.FirebaseFirestore

import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListaEquiposScreen(navController: NavController) {

    val db = FirebaseFirestore.getInstance()
    val context = LocalContext.current

    val listaEquipos = remember { mutableStateListOf<Equipo>() }

    var textoBusqueda by remember { mutableStateOf("") }

    val listaFiltrada = listaEquipos.filter {
        it.cliente.contains(textoBusqueda, true) ||
                it.marca.contains(textoBusqueda, true) ||
                it.modelo.contains(textoBusqueda, true)
    }

    LaunchedEffect(Unit) {
        db.collection("equipos")
            .addSnapshotListener { value, error ->
                if (error != null) return@addSnapshotListener

                listaEquipos.clear()

                value?.documents?.forEach { doc ->
                    val equipo = doc.toObject(Equipo::class.java)?.copy(id = doc.id)
                    if (equipo != null) listaEquipos.add(equipo)
                }
            }
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Equipos Registrados") })
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {

            OutlinedTextField(
                value = textoBusqueda,
                onValueChange = { textoBusqueda = it },
                label = { Text("Buscar equipo") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                items(listaFiltrada) { equipo ->

                    val colorEstado = when (equipo.estado) {
                        "Recibido" -> Color.Gray
                        "En revisión" -> Color.Yellow
                        "Esperando pieza" -> Color(0xFFFF9800)
                        "Reparando" -> Color.Blue
                        "Terminado" -> Color.Green
                        "Entregado" -> Color.Red
                        else -> Color.LightGray
                    }

                    Card(modifier = Modifier.fillMaxWidth()) {

                        Column(modifier = Modifier.padding(16.dp)) {

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {

                                Text(
                                    "Cliente: ${equipo.cliente}",
                                    style = MaterialTheme.typography.titleMedium
                                )

                                IconButton(
                                    onClick = {
                                        db.collection("equipos")
                                            .document(equipo.id)
                                            .delete()
                                    }
                                ) {
                                    Icon(Icons.Default.Delete, "Eliminar")
                                }
                            }

                            Text("Teléfono: ${equipo.telefono}")
                            Text("Marca: ${equipo.marca}")
                            Text("Modelo: ${equipo.modelo}")
                            Text("Falla: ${equipo.falla}")
                            Text("Fecha: ${equipo.fecha}")

                            Spacer(modifier = Modifier.height(8.dp))

                            Text("Estado: ${equipo.estado}", color = colorEstado)

                            Spacer(modifier = Modifier.height(10.dp))

                            Button(
                                onClick = {
                                    val mensaje =
                                        "Hola ${equipo.cliente}, tu equipo ${equipo.marca} ${equipo.modelo} está: ${equipo.estado}"

                                    val url =
                                        "https://wa.me/52${equipo.telefono}?text=${Uri.encode(mensaje)}"

                                    context.startActivity(
                                        Intent(Intent.ACTION_VIEW, Uri.parse(url))
                                    )
                                },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text("WhatsApp")
                            }

                            Button(
                                onClick = {
                                    navController.navigate("ticket/${equipo.id}")
                                },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text("Ver Ticket")
                            }

                            Button(
                                onClick = {
                                    navController.navigate("editar/${equipo.id}")
                                },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text("Actualizar datos")
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            var expanded by remember { mutableStateOf(false) }

                            val estados = listOf(
                                "Recibido",
                                "En revisión",
                                "Esperando pieza",
                                "Reparando",
                                "Terminado",
                                "Entregado"
                            )

                            Button(
                                onClick = { expanded = true },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text("Cambiar estado")
                            }

                            DropdownMenu(
                                expanded = expanded,
                                onDismissRequest = { expanded = false }
                            ) {

                                estados.forEach { estado ->

                                    DropdownMenuItem(
                                        text = { Text(estado) },
                                        onClick = {

                                            db.collection("equipos")
                                                .document(equipo.id)
                                                .update("estado", estado)

                                            expanded = false
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}