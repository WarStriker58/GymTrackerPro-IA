package com.geanpierre.laboratorio7.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.geanpierre.laboratorio7.data.local.dao.RutinaDao
import com.geanpierre.laboratorio7.data.local.dao.UsuarioDao
import com.geanpierre.laboratorio7.data.local.entity.Rutina
import com.geanpierre.laboratorio7.data.local.entity.Usuario

@Database(entities = [Usuario::class, Rutina::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun usuarioDao(): UsuarioDao
    abstract fun rutinaDao(): RutinaDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "gym_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}