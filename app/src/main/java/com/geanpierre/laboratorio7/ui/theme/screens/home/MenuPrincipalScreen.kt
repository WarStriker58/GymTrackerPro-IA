package com.geanpierre.laboratorio7.ui.theme.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuPrincipalScreen(navController: NavController, userId: Int) {

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            Column(modifier = Modifier.padding(16.dp)) {

                Text("Menú", modifier = Modifier.padding(8.dp))

                Button(onClick = {
                    navController.navigate("rutinas/$userId")
                }) { Text("Mis rutinas") }

                Button(onClick = {
                    navController.navigate("agregar/$userId")
                }) { Text("Agregar rutina") }

                Button(onClick = {
                    navController.navigate("perfil/$userId")
                }) { Text("Perfil") }

                Button(onClick = {
                    navController.navigate("login") {
                        popUpTo("login") { inclusive = true }
                    }
                }) { Text("Cerrar sesión") }
            }
        }
    ) {

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("GymTracker Pro") },
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch { drawerState.open() }
                        }) {
                            Icon(Icons.Default.Menu, contentDescription = null)
                        }
                    }
                )
            }
        ) { padding ->

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center
            ) {

                Button(onClick = {
                    navController.navigate("agregar/$userId")
                }) {
                    Text("Agregar rutina")
                }

                Button(onClick = {
                    navController.navigate("rutinas/$userId")
                }) {
                    Text("Mis rutinas")
                }
            }
        }
    }
}