package com.example.clsolutions.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding

import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar

import androidx.compose.runtime.*

import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import com.example.clsolutions.model.Equipo

import com.google.firebase.firestore.FirebaseFirestore

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen() {

    val db = FirebaseFirestore.getInstance()

    var totalEquipos by remember {
        mutableStateOf(0)
    }

    var enReparacion by remember {
        mutableStateOf(0)
    }

    var terminados by remember {
        mutableStateOf(0)
    }

    var entregados by remember {
        mutableStateOf(0)
    }

    LaunchedEffect(Unit) {

        db.collection("equipos")
            .addSnapshotListener { value, error ->

                if (error != null) {
                    return@addSnapshotListener
                }

                val lista = value?.documents?.mapNotNull {

                    it.toObject(Equipo::class.java)

                } ?: emptyList()

                totalEquipos = lista.size

                enReparacion = lista.count {
                    it.estado == "Reparando"
                }

                terminados = lista.count {
                    it.estado == "Terminado"
                }

                entregados = lista.count {
                    it.estado == "Entregado"
                }

            }

    }

    Scaffold(

        topBar = {

            TopAppBar(
                title = {
                    Text("Dashboard")
                }
            )

        }

    ) { paddingInterno ->

        Column(

            modifier = Modifier
                .fillMaxSize()
                .padding(paddingInterno)
                .padding(16.dp),

            verticalArrangement = Arrangement.spacedBy(16.dp)

        ) {

            CardDashboard(
                titulo = "Total equipos",
                valor = totalEquipos.toString()
            )

            CardDashboard(
                titulo = "En reparación",
                valor = enReparacion.toString()
            )

            CardDashboard(
                titulo = "Terminados",
                valor = terminados.toString()
            )

            CardDashboard(
                titulo = "Entregados",
                valor = entregados.toString()
            )

        }

    }

}

@Composable
fun CardDashboard(
    titulo: String,
    valor: String
) {

    Card(
        modifier = Modifier.fillMaxWidth()
    ) {

        Column(
            modifier = Modifier.padding(20.dp)
        ) {

            Text(
                text = titulo,
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = valor,
                style = MaterialTheme.typography.headlineMedium
            )

        }

    }

}