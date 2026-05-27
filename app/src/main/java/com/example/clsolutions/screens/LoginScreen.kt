package com.example.clsolutions.screens

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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(

    irInicio: () -> Unit

) {

    var usuario by remember {
        mutableStateOf("")
    }

    var contraseña by remember {
        mutableStateOf("")
    }

    var mensajeError by remember {
        mutableStateOf("")
    }

    Scaffold(

        topBar = {

            TopAppBar(

                title = {

                    Text("Login CL SOLUTIONS")

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

            OutlinedTextField(

                value = usuario,

                onValueChange = {
                    usuario = it
                },

                label = {
                    Text("Usuario")
                },

                modifier = Modifier.fillMaxWidth()

            )

            OutlinedTextField(

                value = contraseña,

                onValueChange = {
                    contraseña = it
                },

                label = {
                    Text("Contraseña")
                },

                visualTransformation = PasswordVisualTransformation(),

                modifier = Modifier.fillMaxWidth()

            )

            Button(

                onClick = {

                    if (
                        usuario == "admin" &&
                        contraseña == "1234"
                    ) {

                        mensajeError = ""

                        irInicio()

                    } else {

                        mensajeError =
                            "Usuario o contraseña incorrectos"

                    }

                },

                modifier = Modifier.fillMaxWidth()

            ) {

                Text("Ingresar")

            }

            if (mensajeError.isNotEmpty()) {

                Text(
                    text = mensajeError
                )

            }

        }

    }

}