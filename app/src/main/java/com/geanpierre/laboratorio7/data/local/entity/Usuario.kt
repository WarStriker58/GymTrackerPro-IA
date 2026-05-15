package com.geanpierre.laboratorio7.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "usuarios")
data class Usuario(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "nombre_usuario") val nombreUsuario: String,
    @ColumnInfo(name = "password") val password: String,
    @ColumnInfo(name = "nombre_completo") val nombreCompleto: String,
    @ColumnInfo(name = "email") val email: String,
    @ColumnInfo(name = "edad") val edad: Int,
    @ColumnInfo(name = "fecha_registro") val fechaRegistro: String
)