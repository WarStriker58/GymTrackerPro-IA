package com.geanpierre.laboratorio7

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.geanpierre.laboratorio7.data.local.db.AppDatabase
import com.geanpierre.laboratorio7.ui.theme.Laboratorio7Theme
import com.geanpierre.laboratorio7.ui.theme.navigation.AppNavHost

class MainActivity : ComponentActivity() {

    lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "gym_db"
        ).build()

        setContent {
            Laboratorio7Theme {
                App(db)
            }
        }
    }
}

@Composable
fun App(db: AppDatabase) {
    val navController = rememberNavController()

    AppNavHost(navController = navController, db = db)
}