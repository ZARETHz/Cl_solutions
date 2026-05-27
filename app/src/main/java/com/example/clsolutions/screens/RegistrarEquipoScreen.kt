package com.example.clsolutions.screens

import android.widget.Toast

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding

import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

import com.example.clsolutions.model.Equipo

import com.google.firebase.firestore.FirebaseFirestore

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrarEquipoScreen() {

    var cliente by remember {
        mutableStateOf("")
    }

    var telefono by remember {
        mutableStateOf("")
    }

    var marca by remember {
        mutableStateOf("")
    }

    var modelo by remember {
        mutableStateOf("")
    }

    var falla by remember {
        mutableStateOf("")
    }

    val context = LocalContext.current

    val db = FirebaseFirestore.getInstance()

    Scaffold(

        topBar = {

            TopAppBar(
                title = {
                    Text("Registrar Equipo")
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

                value = cliente,

                onValueChange = {
                    cliente = it
                },

                label = {
                    Text("Cliente")
                },

                modifier = Modifier.fillMaxWidth()

            )

            OutlinedTextField(

                value = telefono,

                onValueChange = {
                    telefono = it
                },

                label = {
                    Text("Teléfono")
                },

                modifier = Modifier.fillMaxWidth()

            )

            OutlinedTextField(

                value = marca,

                onValueChange = {
                    marca = it
                },

                label = {
                    Text("Marca")
                },

                modifier = Modifier.fillMaxWidth()

            )

            OutlinedTextField(

                value = modelo,

                onValueChange = {
                    modelo = it
                },

                label = {
                    Text("Modelo")
                },

                modifier = Modifier.fillMaxWidth()

            )

            OutlinedTextField(

                value = falla,

                onValueChange = {
                    falla = it
                },

                label = {
                    Text("Falla")
                },

                modifier = Modifier.fillMaxWidth()

            )

            Button(

                onClick = {

                    val fechaActual = SimpleDateFormat(
                        "dd/MM/yyyy hh:mm a",
                        Locale.getDefault()
                    ).format(Date())

                    val equipo = Equipo(

                        cliente = cliente,

                        telefono = telefono,

                        marca = marca,

                        modelo = modelo,

                        falla = falla,

                        estado = "Recibido",

                        fecha = fechaActual

                    )

                    db.collection("equipos")
                        .add(equipo)

                        .addOnSuccessListener {

                            Toast.makeText(
                                context,
                                "Equipo guardado",
                                Toast.LENGTH_SHORT
                            ).show()

                            cliente = ""
                            telefono = ""
                            marca = ""
                            modelo = ""
                            falla = ""

                        }

                        .addOnFailureListener {

                            Toast.makeText(
                                context,
                                "Error al guardar",
                                Toast.LENGTH_SHORT
                            ).show()

                        }

                },

                modifier = Modifier.fillMaxWidth()

            ) {

                Text("Guardar")

            }

        }

    }

}