@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.clsolutions.screens

import android.content.Intent
import android.net.Uri

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

import com.google.firebase.firestore.FirebaseFirestore

import com.example.clsolutions.utils.generarPdfTicket

@Composable
fun TicketProScreen(id: String) {

    val db = FirebaseFirestore.getInstance()
    val context = LocalContext.current

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
            TopAppBar(
                title = { Text("TICKET PRO") }
            )
        }

    ) { padding ->

        Column(

            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .background(Color.White),

            verticalArrangement = Arrangement.spacedBy(10.dp),

            horizontalAlignment = Alignment.Start

        ) {

            Text(
                text = "🧾 CL SOLUTIONS",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )

            Text("Folio: $id")

            HorizontalDivider()

            Text("👤 Cliente: $cliente")
            Text("📱 Teléfono: $telefono")
            Text("📱 Marca: $marca")
            Text("📱 Modelo: $modelo")
            Text("⚠️ Falla: $falla")

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                "📦 Estado: $estado",
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1976D2)
            )

            Text("📅 Fecha: $fecha")

            HorizontalDivider()

            Text(
                text = "Gracias por confiar en CL SOLUTIONS 🙌",
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(20.dp))

            // 📲 BOTÓN WHATSAPP
            Button(
                onClick = {

                    val mensaje =
                        "🧾 CL SOLUTIONS\n" +
                                "Cliente: $cliente\n" +
                                "Equipo: $marca $modelo\n" +
                                "Estado: $estado\n" +
                                "Folio: $id"

                    val url =
                        "https://wa.me/52$telefono?text=${Uri.encode(mensaje)}"

                    context.startActivity(
                        Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    )

                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Compartir por WhatsApp")
            }
            Button(
                onClick = {

                    val file = generarPdfTicket(
                        context = context,
                        id = id,
                        equipo = com.example.clsolutions.model.Equipo(
                            cliente = cliente,
                            telefono = telefono,
                            marca = marca,
                            modelo = modelo,
                            falla = falla,
                            estado = estado,
                            fecha = fecha
                        )
                    )

                    val uri = androidx.core.content.FileProvider.getUriForFile(
                        context,
                        context.packageName + ".provider",
                        file
                    )

                    val intent = Intent(Intent.ACTION_SEND).apply {
                        type = "application/pdf"
                        putExtra(Intent.EXTRA_STREAM, uri)
                        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                    }

                    context.startActivity(Intent.createChooser(intent, "Compartir PDF"))

                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Generar PDF")
            }

        }

    }

}