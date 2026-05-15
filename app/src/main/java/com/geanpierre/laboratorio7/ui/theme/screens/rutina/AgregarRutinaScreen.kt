package com.geanpierre.laboratorio7.ui.theme.screens.rutina

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.geanpierre.laboratorio7.data.local.db.AppDatabase
import com.geanpierre.laboratorio7.data.local.entity.Rutina
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgregarRutinaScreen(navController: NavController, db: AppDatabase, userId: Int) {

    var ejercicio by remember { mutableStateOf("") }
    var grupo by remember { mutableStateOf("") }
    var series by remember { mutableStateOf("") }
    var repeticiones by remember { mutableStateOf("") }
    var peso by remember { mutableStateOf("") }
    var fecha by remember { mutableStateOf("") }

    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Nueva rutina") },
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
                value = ejercicio,
                onValueChange = { ejercicio = it },
                label = { Text("Ejercicio") },
                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
            )

            TextField(
                value = grupo,
                onValueChange = { grupo = it },
                label = { Text("Grupo muscular") },
                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
            )

            Row {
                TextField(
                    value = series,
                    onValueChange = { series = it },
                    label = { Text("Series") },
                    modifier = Modifier.weight(1f).padding(end = 4.dp)
                )

                TextField(
                    value = repeticiones,
                    onValueChange = { repeticiones = it },
                    label = { Text("Repeticiones") },
                    modifier = Modifier.weight(1f).padding(start = 4.dp)
                )
            }

            TextField(
                value = peso,
                onValueChange = { peso = it },
                label = { Text("Peso (kg)") },
                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
            )

            TextField(
                value = fecha,
                onValueChange = { fecha = it },
                label = { Text("Fecha") },
                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
            )

            Button(
                onClick = {
                    scope.launch {

                        db.rutinaDao().insertar(
                            Rutina(
                                usuarioId = userId,
                                ejercicio = ejercicio,
                                grupoMuscular = grupo,
                                series = series.toIntOrNull() ?: 0,
                                repeticiones = repeticiones.toIntOrNull() ?: 0,
                                pesoKg = peso.toDoubleOrNull() ?: 0.0,
                                fecha = fecha
                            )
                        )

                        navController.popBackStack()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp)
            ) {
                Text("Guardar rutina")
            }
        }
    }
}