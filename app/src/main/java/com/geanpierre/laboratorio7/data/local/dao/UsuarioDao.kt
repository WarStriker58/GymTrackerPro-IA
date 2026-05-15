package com.geanpierre.laboratorio7.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.geanpierre.laboratorio7.data.local.entity.Usuario

@Dao
interface UsuarioDao {

    @Insert
    suspend fun insertar(usuario: Usuario)

    @Query("SELECT * FROM usuarios WHERE nombre_usuario = :user AND password = :pass LIMIT 1")
    suspend fun login(user: String, pass: String): Usuario?

    @Query("SELECT * FROM usuarios WHERE id = :id")
    suspend fun buscarPorId(id: Int): Usuario?

    @Query("SELECT * FROM usuarios WHERE email = :email LIMIT 1")
    suspend fun buscarPorEmail(email: String): Usuario?
}