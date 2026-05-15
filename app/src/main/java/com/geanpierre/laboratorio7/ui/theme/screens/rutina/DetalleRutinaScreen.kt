package com.geanpierre.laboratorio7.ui.theme.screens.rutina

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.geanpierre.laboratorio7.data.local.db.AppDatabase
import com.geanpierre.laboratorio7.data.local.entity.Rutina
import kotlinx.coroutines.launch
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalleRutinaScreen(
    navController: NavController,
    db: AppDatabase,
    rutinaId: Int
) {

    val scope = rememberCoroutineScope()

    var rutina by remember { mutableStateOf<Rutina?>(null) }

    var ejercicio by remember { mutableStateOf("") }
    var grupo by remember { mutableStateOf("") }
    var series by remember { mutableStateOf("") }
    var reps by remember { mutableStateOf("") }
    var peso by remember { mutableStateOf("") }
    var fecha by remember { mutableStateOf("") }

    // 🔥 cargar datos
    LaunchedEffect(Unit) {
        rutina = db.rutinaDao().buscarPorId(rutinaId)

        rutina?.let {
            ejercicio = it.ejercicio
            grupo = it.grupoMuscular
            series = it.series.toString()
            reps = it.repeticiones.toString()
            peso = it.pesoKg.toString()
            fecha = it.fecha
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Editar rutina #$rutinaId") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
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
                .padding(16.dp)
        ) {

            Text(
                text = "Modificando registro existente",
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextField(value = ejercicio, onValueChange = { ejercicio = it }, label = { Text("Ejercicio") })
            TextField(value = grupo, onValueChange = { grupo = it }, label = { Text("Grupo muscular") })
            TextField(value = series, onValueChange = { series = it }, label = { Text("Series") })
            TextField(value = reps, onValueChange = { reps = it }, label = { Text("Repeticiones") })
            TextField(value = peso, onValueChange = { peso = it }, label = { Text("Peso (kg)") })
            TextField(value = fecha, onValueChange = { fecha = it }, label = { Text("Fecha") })

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    scope.launch {
                        rutina?.let {
                            db.rutinaDao().actualizar(
                                it.copy(
                                    ejercicio = ejercicio,
                                    grupoMuscular = grupo,
                                    series = series.toInt(),
                                    repeticiones = reps.toInt(),
                                    pesoKg = peso.toDouble(),
                                    fecha = fecha
                                )
                            )
                        }
                        navController.popBackStack()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Actualizar cambios")
            }
        }
    }
}