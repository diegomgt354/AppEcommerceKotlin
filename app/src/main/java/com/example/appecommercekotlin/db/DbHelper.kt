package com.example.appecommercekotlin.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


open class DbHelper(context: Context?) :
    SQLiteOpenHelper(context, DATABASE_NOMBRE, null, 1) {
    private var baseDeDatosExiste = false

    init {
        // validamos si existe la db
        baseDeDatosExiste = context!!.getDatabasePath(DATABASE_NOMBRE).exists()
    }

    override fun onCreate(sqLite: SQLiteDatabase) {
        // si no existe crea las tablas
        if (!baseDeDatosExiste) {
            val sql_usuario = "create table usuario (id integer primary key autoincrement," +
                    "correo text not null, usuario text not null," +
                    "password text not null, nombre text not null," +
                    "edad integer not null, genero text, direccion text not null)"
            sqLite.execSQL(sql_usuario)
            val sql_compra = "create table compra (id integer primary key autoincrement, " +
                    "idusuario integer not null, subtotal text not null, " +
                    "entrega text not null, impuesto text not null, " +
                    "total text not null, productos text not null, " +
                    "fechacompra text not null)"
            sqLite.execSQL(sql_compra)
        }
    }

    override fun onUpgrade(sqLite: SQLiteDatabase, i: Int, i1: Int) {
        // no se realiza nada ya que no manejamos versiones
    }

    companion object {
        private const val DATABASE_NOMBRE = "ecommerce.db"
    }
}
