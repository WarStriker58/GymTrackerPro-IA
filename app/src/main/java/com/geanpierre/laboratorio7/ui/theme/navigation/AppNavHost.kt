package com.geanpierre.laboratorio7.ui.theme.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.geanpierre.laboratorio7.data.local.db.AppDatabase
import com.geanpierre.laboratorio7.ui.theme.perfil.PerfilUsuarioScreen
import com.geanpierre.laboratorio7.ui.theme.screens.home.MenuPrincipalScreen
import com.geanpierre.laboratorio7.ui.theme.screens.login.LoginScreen
import com.geanpierre.laboratorio7.ui.theme.screens.register.RegistroScreen
import com.geanpierre.laboratorio7.ui.theme.screens.rutina.AgregarRutinaScreen
import com.geanpierre.laboratorio7.ui.theme.screens.rutina.DetalleRutinaScreen
import com.geanpierre.laboratorio7.ui.theme.screens.rutina.ListaRutinasScreen

@Composable
fun AppNavHost(navController: NavHostController, db: AppDatabase) {

    NavHost(navController = navController, startDestination = "login") {

        composable("login") {
            LoginScreen(navController, db)
        }

        composable("registro") {
            RegistroScreen(navController, db)
        }

        composable("menu/{userId}") { backStack ->
            val userId = backStack.arguments?.getString("userId")!!.toInt()
            MenuPrincipalScreen(navController, userId)
        }

        composable("rutinas/{userId}") { backStack ->
            val userId = backStack.arguments?.getString("userId")!!.toInt()
            ListaRutinasScreen(navController, db, userId)
        }

        composable("agregar/{userId}") { backStack ->
            val userId = backStack.arguments?.getString("userId")!!.toInt()
            AgregarRutinaScreen(navController, db, userId)
        }

        composable("detalle/{rutinaId}") { backStack ->
            val id = backStack.arguments?.getString("rutinaId")!!.toInt()
            DetalleRutinaScreen(navController, db, id)
        }

        composable("perfil/{userId}") { backStack ->
            val userId = backStack.arguments?.getString("userId")!!.toInt()
            PerfilUsuarioScreen(navController, db, userId)
        }
    }
}