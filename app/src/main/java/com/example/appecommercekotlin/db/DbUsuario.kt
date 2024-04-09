package com.example.appecommercekotlin.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.example.appecommercekotlin.Entity.Usuario

class DbUsuario(context: Context?) : DbHelper(context) {

    companion object {
        var inicioSesion = false
        var idSesion = 0
    }

    private var context: Context? = null;

    init {
        this.context = context
    }

    // guarda un usuario
    fun guardar(
        correo: String?,
        usuario: String?,
        password: String?,
        nombre: String?,
        edad: Int,
        genero: String?,
        direccion: String?
    ): Long {
        var id: Long = 0
        try {
            val dbHelper = DbHelper(context)
            val db = dbHelper.writableDatabase
            val values = ContentValues()
            values.put("correo", correo)
            values.put("usuario", usuario)
            values.put("password", password)
            values.put("nombre", nombre)
            values.put("edad", edad)
            values.put("genero", genero)
            values.put("direccion", direccion)
            id = db.insert("usuario", null, values)
        } catch (e: Exception) {
            e.toString()
        }
        return id
    }

    // lista a todos los usuarios
    fun listar(): ArrayList<Usuario?> {
        val dbHelper = DbHelper(context)
        val db = dbHelper.writableDatabase
        val listaUsuarios: ArrayList<Usuario?> = ArrayList<Usuario?>()
        var usuario: Usuario? = null
        var cursor: Cursor? = null
        val query = "select * from usuario"
        cursor = db.rawQuery(query, null)
        if (cursor.moveToFirst()) {
            do {
                usuario = Usuario()
                usuario.setId(cursor.getInt(0))
                usuario.setCorreo(cursor.getString(1))
                usuario.setUsuario(cursor.getString(2))
                usuario.setPassword(cursor.getString(3))
                usuario.setNombre(cursor.getString(4))
                usuario.setEdad(cursor.getInt(5))
                usuario.setGenero(cursor.getString(6))
                usuario.setDireccion(cursor.getString(7))
                listaUsuarios.add(usuario)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return listaUsuarios
    }

    // busca el usuario por el id
    fun listarPorId(id: Int): Usuario? {
        val dbHelper = DbHelper(context)
        val db = dbHelper.writableDatabase
        var usuario: Usuario? = null
        var cursor: Cursor? = null
        val query = "select * from usuario where id=$id"
        cursor = db.rawQuery(query, null)
        if (cursor.moveToFirst()) {
            usuario = Usuario()
            usuario.setId(cursor.getInt(0))
            usuario.setCorreo(cursor.getString(1))
            usuario.setUsuario(cursor.getString(2))
            usuario.setPassword(cursor.getString(3))
            usuario.setNombre(cursor.getString(4))
            usuario.setEdad(cursor.getInt(5))
            usuario.setGenero(cursor.getString(6))
            usuario.setDireccion(cursor.getString(7))
        }
        cursor.close()
        return usuario
    }

    // valida si es que el usuario segun su correo y contraseña existen
    fun validarIngreso(user: String, password: String): Usuario? {
        val dbHelper = DbHelper(context)
        val db = dbHelper.writableDatabase
        var usuario: Usuario? = null
        var cursor: Cursor? = null
        val query =
            "select * from usuario where usuario='$user' and password='$password'"
        cursor = db.rawQuery(query, null)
        if (cursor.moveToFirst()) {
            usuario = Usuario()
            usuario.setId(cursor.getInt(0))
            usuario.setCorreo(cursor.getString(1))
            usuario.setUsuario(cursor.getString(2))
            usuario.setPassword(cursor.getString(3))
            usuario.setNombre(cursor.getString(4))
            usuario.setEdad(cursor.getInt(5))
            usuario.setGenero(cursor.getString(6))
            usuario.setDireccion(cursor.getString(7))
            idSesion = usuario.getId()
        }
        cursor.close()
        return usuario
    }


}
