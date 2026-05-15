package com.geanpierre.laboratorio7.ui.theme.screens.rutina

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import com.geanpierre.laboratorio7.data.local.db.AppDatabase
import com.geanpierre.laboratorio7.data.local.entity.Rutina
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListaRutinasScreen(
    navController: NavController,
    db: AppDatabase,
    userId: Int
) {

    val scope = rememberCoroutineScope()
    var lista by remember { mutableStateOf(listOf<Rutina>()) }

    LaunchedEffect(Unit) {
        lista = db.rutinaDao().listarPorUsuario(userId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mis rutinas") },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate("agregar/$userId")
                }
            ) {
                Text("+")
            }
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {

            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn {

                items(lista) { r ->

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp),
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {

                        Column(modifier = Modifier.padding(12.dp)) {

                            Text(
                                text = r.ejercicio,
                                style = MaterialTheme.typography.titleMedium
                            )

                            Text(
                                text = r.grupoMuscular,
                                style = MaterialTheme.typography.bodyMedium
                            )

                            Text(
                                text = "${r.series} series x ${r.repeticiones} reps · ${r.pesoKg} kg · ${r.fecha}",
                                style = MaterialTheme.typography.bodySmall
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Row {
                                Button(onClick = {
                                    navController.navigate("detalle/${r.id}")
                                }) {
                                    Text("Editar")
                                }

                                Spacer(modifier = Modifier.width(8.dp))

                                Button(onClick = {
                                    scope.launch {
                                        db.rutinaDao().eliminar(r)
                                        lista = db.rutinaDao().listarPorUsuario(userId)
                                    }
                                }) {
                                    Text("Eliminar")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
