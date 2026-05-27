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

import com.example.clsolutions.model.Precio

import com.google.firebase.firestore.FirebaseFirestore

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListaPreciosScreen() {

    val db = FirebaseFirestore.getInstance()

    var servicio by remember {
        mutableStateOf("")
    }

    var precio by remember {
        mutableStateOf("")
    }

    var textoBusqueda by remember {
        mutableStateOf("")
    }

    val listaPrecios = remember {
        mutableStateListOf<Precio>()
    }

    val listaFiltrada = listaPrecios.filter {

        it.servicio.contains(textoBusqueda, true)

    }

    LaunchedEffect(Unit) {

        db.collection("precios")
            .addSnapshotListener { value, error ->

                if (error != null) {
                    return@addSnapshotListener
                }

                listaPrecios.clear()

                value?.documents?.forEach { documento ->

                    val precioObj = documento
                        .toObject(Precio::class.java)
                        ?.copy(
                            id = documento.id
                        )

                    if (precioObj != null) {

                        listaPrecios.add(precioObj)

                    }

                }

            }

    }

    Scaffold(

        topBar = {

            TopAppBar(
                title = {
                    Text("Lista de Precios")
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

                value = servicio,

                onValueChange = {
                    servicio = it
                },

                label = {
                    Text("Servicio")
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

                    val nuevoPrecio = Precio(
                        servicio = servicio,
                        precio = precio
                    )

                    db.collection("precios")
                        .add(nuevoPrecio)

                    servicio = ""
                    precio = ""

                },

                modifier = Modifier.fillMaxWidth()

            ) {

                Text("Agregar precio")

            }

            HorizontalDivider()

            OutlinedTextField(

                value = textoBusqueda,

                onValueChange = {
                    textoBusqueda = it
                },

                label = {
                    Text("Buscar servicio")
                },

                modifier = Modifier.fillMaxWidth()

            )

            LazyColumn(

                verticalArrangement = Arrangement.spacedBy(12.dp)

            ) {

                items(listaFiltrada) { precioItem ->

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
                                    text = precioItem.servicio,
                                    style = MaterialTheme.typography.titleMedium
                                )

                                Text(
                                    text = "$${precioItem.precio}"
                                )

                            }

                            IconButton(

                                onClick = {

                                    db.collection("precios")
                                        .document(precioItem.id)
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