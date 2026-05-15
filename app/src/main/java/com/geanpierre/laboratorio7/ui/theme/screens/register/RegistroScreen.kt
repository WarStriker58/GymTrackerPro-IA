package com.geanpierre.laboratorio7.ui.theme.screens.register

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.geanpierre.laboratorio7.data.local.db.AppDatabase
import com.geanpierre.laboratorio7.data.local.entity.Usuario
import kotlinx.coroutines.launch
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistroScreen(navController: NavController, db: AppDatabase) {

    var nombre by remember { mutableStateOf("") }
    var usuario by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var edad by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Crear cuenta") },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Text("<")
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

            TextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre completo") },
                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
            )

            TextField(
                value = usuario,
                onValueChange = { usuario = it },
                label = { Text("Usuario") },
                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
            )

            TextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
            )

            TextField(
                value = edad,
                onValueChange = { edad = it },
                label = { Text("Edad") },
                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
            )

            TextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Contraseña") },
                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
            )

            Button(
                onClick = {
                    scope.launch {
                        val existe = db.usuarioDao().buscarPorEmail(email)

                        if (existe == null && email.contains("@")) {

                            db.usuarioDao().insertar(
                                Usuario(
                                    nombreUsuario = usuario,
                                    password = password,
                                    nombreCompleto = nombre,
                                    email = email,
                                    edad = edad.toIntOrNull() ?: 0,
                                    fechaRegistro = "2025"
                                )
                            )

                            navController.popBackStack()
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp)
            ) {
                Text("Registrarme")
            }
        }
    }
}