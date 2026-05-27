package com.example.clsolutions.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete

import androidx.compose.material3.*

import androidx.compose.runtime.*

import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import com.example.clsolutions.model.Pieza

import com.google.firebase.firestore.FirebaseFirestore

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InventarioScreen() {

    val db = FirebaseFirestore.getInstance()

    var nombre by remember {
        mutableStateOf("")
    }

    var cantidad by remember {
        mutableStateOf("")
    }

    var precio by remember {
        mutableStateOf("")
    }

    val listaPiezas = remember {
        mutableStateListOf<Pieza>()
    }

    LaunchedEffect(Unit) {

        db.collection("inventario")
            .addSnapshotListener { value, error ->

                if (error != null) {
                    return@addSnapshotListener
                }

                listaPiezas.clear()

                value?.documents?.forEach { documento ->

                    val pieza = documento
                        .toObject(Pieza::class.java)
                        ?.copy(
                            id = documento.id
                        )

                    if (pieza != null) {

                        listaPiezas.add(pieza)

                    }

                }

            }

    }

    Scaffold(

        topBar = {

            TopAppBar(
                title = {
                    Text("Inventario")
                }
            )

        }

    ) { paddingInterno ->

        Column(

            modifier = Modifier
                .fillMaxSize()
                .padding(paddingInterno)
                .padding(16.dp),

            verticalArrangement = Arrangement.spacedBy(12.dp)

        ) {

            OutlinedTextField(

                value = nombre,

                onValueChange = {
                    nombre = it
                },

                label = {
                    Text("Nombre de pieza")
                },

                modifier = Modifier.fillMaxWidth()

            )

            OutlinedTextField(

                value = cantidad,

                onValueChange = {
                    cantidad = it
                },

                label = {
                    Text("Cantidad")
                },

                modifier = Modifier.fillMaxWidth()

            )

            OutlinedTextField(

                value = precio,

                onValueChange = {
                    precio = it
                },

                label = {
                    Text("Precio")
                },

                modifier = Modifier.fillMaxWidth()

            )

            Button(

                onClick = {

                    val pieza = Pieza(
                        nombre = nombre,
                        cantidad = cantidad,
                        precio = precio
                    )

                    db.collection("inventario")
                        .add(pieza)

                    nombre = ""
                    cantidad = ""
                    precio = ""

                },

                modifier = Modifier.fillMaxWidth()

            ) {

                Text("Agregar pieza")

            }

            HorizontalDivider()

            LazyColumn(

                verticalArrangement = Arrangement.spacedBy(12.dp)

            ) {

                items(listaPiezas) { pieza ->

                    Card(
                        modifier = Modifier.fillMaxWidth()
                    ) {

                        Row(

                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),

                            horizontalArrangement = Arrangement.SpaceBetween

                        ) {

                            Column {

                                Text(
                                    text = pieza.nombre,
                                    style = MaterialTheme.typography.titleMedium
                                )

                                Text("Cantidad: ${pieza.cantidad}")

                                Text("Precio: $${pieza.precio}")

                            }

                            IconButton(

                                onClick = {

                                    db.collection("inventario")
                                        .document(pieza.id)
                                        .delete()

                                }

                            ) {

                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Eliminar"
                                )

                            }

                        }

                    }

                }

            }

        }

    }

}