package com.geanpierre.laboratorio7.ui.theme.perfil

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.geanpierre.laboratorio7.data.local.db.AppDatabase
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PerfilUsuarioScreen(navController: NavController, db: AppDatabase, userId: Int) {

    var nombre by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var edad by remember { mutableStateOf(0) }
    var fecha by remember { mutableStateOf("") }

    var totalRutinas by remember { mutableStateOf(0) }
    var volumen by remember { mutableStateOf(0.0) }

    LaunchedEffect(Unit) {
        val u = db.usuarioDao().buscarPorId(userId)
        nombre = u?.nombreCompleto ?: ""
        email = u?.email ?: ""
        edad = u?.edad ?: 0
        fecha = u?.fechaRegistro ?: ""

        totalRutinas = db.rutinaDao().totalRutinas(userId)
        volumen = db.rutinaDao().volumenTotal(userId) ?: 0.0
    }

    val iniciales = nombre
        .split(" ")
        .mapNotNull { it.firstOrNull()?.toString() }
        .take(2)
        .joinToString("")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mi perfil") },
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
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .background(Color.Gray),
                contentAlignment = Alignment.Center
            ) {
                Text(iniciales, fontSize = 24.sp, color = Color.White)
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(nombre, fontSize = 18.sp)
            Text(email, color = Color.Gray)

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("$totalRutinas", fontSize = 18.sp)
                    Text("Rutinas")
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("${volumen.toInt()} kg", fontSize = 18.sp)
                    Text("Total")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Column(modifier = Modifier.fillMaxWidth()) {
                Text("Email: $email")
                Text("Edad: $edad")
                Text("Miembro desde: $fecha")
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    navController.navigate("login") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Cerrar sesión", color = Color.White)
            }
        }
    }
}