package com.example.clsolutions

import android.os.Bundle

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable

import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument

import com.example.clsolutions.screens.*
import com.example.clsolutions.ui.theme.CLSolutionsTheme

import com.example.clsolutions.screens.EditarEquipoScreen

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            CLSolutionsTheme {
                Navegacion()
            }
        }
    }
}

@Composable
fun Navegacion() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {

        // 🔐 LOGIN
        composable("login") {
            LoginScreen(
                irInicio = {
                    navController.navigate("inicio") {
                        popUpTo("login") { inclusive = true }
                    }
                }
            )
        }

        // 🏠 INICIO
        composable("inicio") {
            PantallaPrincipal(
                irRegistrarEquipo = {
                    navController.navigate("registrar")
                },
                irListaEquipos = {
                    navController.navigate("listaEquipos")
                },
                irInventario = {
                    navController.navigate("inventario")
                },
                irPrecios = {
                    navController.navigate("precios")
                },
                irDashboard = {
                    navController.navigate("dashboard")
                }
            )
        }

        // 🧾 REGISTRAR
        composable("registrar") {
            RegistrarEquipoScreen()
        }

        // 📋 LISTA EQUIPOS
        composable("listaEquipos") {
            ListaEquiposScreen(navController)
        }

        // 📦 INVENTARIO
        composable("inventario") {
            InventarioScreen()
        }

        // 💰 PRECIOS
        composable("precios") {
            ListaPreciosScreen()
        }

        // 📊 DASHBOARD
        composable("dashboard") {
            DashboardScreen()
        }

        // 🧾 TICKET PRO
        composable(
            route = "ticket/{id}",
            arguments = listOf(
                navArgument("id") { type = NavType.StringType }
            )
        ) { backStackEntry ->

            val id = backStackEntry.arguments?.getString("id") ?: ""

            TicketProScreen(id = id)
        }

        // ✏️ EDITAR EQUIPO (NUEVO)
        composable(
            route = "editar/{id}",
            arguments = listOf(
                navArgument("id") { type = NavType.StringType }
            )
        ) { backStackEntry ->

            val id = backStackEntry.arguments?.getString("id") ?: ""

            EditarEquipoScreen(
                id = id,
                onBack = { navController.popBackStack() }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaPrincipal(
    irRegistrarEquipo: () -> Unit,
    irListaEquipos: () -> Unit,
    irInventario: () -> Unit,
    irPrecios: () -> Unit,
    irDashboard: () -> Unit
) {

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("CL SOLUTIONS") })
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Button(onClick = irRegistrarEquipo, modifier = Modifier.fillMaxWidth()) {
                Text("Registrar equipo")
            }

            Button(onClick = irListaEquipos, modifier = Modifier.fillMaxWidth()) {
                Text("Ver equipos")
            }

            Button(onClick = irInventario, modifier = Modifier.fillMaxWidth()) {
                Text("Inventario")
            }

            Button(onClick = irPrecios, modifier = Modifier.fillMaxWidth()) {
                Text("Lista de precios")
            }

            Button(onClick = irDashboard, modifier = Modifier.fillMaxWidth()) {
                Text("Dashboard")
            }
        }
    }
}