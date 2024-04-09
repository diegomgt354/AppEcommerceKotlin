package com.example.appecommercekotlin.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.example.appecommercekotlin.Entity.Compra

class DbCompra(context: Context) : DbHelper(context) {

    private var context: Context? = null;

    init {
        this.context = context
    }

    // guarda un usuario
    fun insertar(
        idusuario: Int,
        subtotal: String?,
        entrega: String?,
        impuesto: String?,
        total: String?,
        productos: String?,
        fechacompra: String?
    ): Long {
        var id: Long = 0
        try {
            val dbHelper = DbHelper(context)
            val db = dbHelper.writableDatabase
            val values = ContentValues()
            values.put("idusuario", idusuario)
            values.put("subtotal", subtotal)
            values.put("entrega", entrega)
            values.put("impuesto", impuesto)
            values.put("total", total)
            values.put("productos", productos)
            values.put("fechacompra", fechacompra)
            id = db.insert("compra", null, values)
        } catch (e: Exception) {
            e.toString()
        }
        return id
    }

    // lista a todos los usuarios
    fun listar(idusuario: Int): ArrayList<Compra> {
        val dbHelper = DbHelper(context)
        val db = dbHelper.writableDatabase
        val listaCompras = ArrayList<Compra>()
        var compra: Compra? = null
        var cursor: Cursor? = null
        val query = "select * from compra where idusuario=$idusuario"
        cursor = db.rawQuery(query, null)
        if (cursor.moveToFirst()) {
            do {
                compra = Compra()
                compra.setId(cursor.getInt(0))
                compra.setIdusuario(cursor.getInt(1))
                compra.setSubtotal(cursor.getString(2))
                compra.setEntrega(cursor.getString(3))
                compra.setImpuesto(cursor.getString(4))
                compra.setTotal(cursor.getString(5))
                compra.setProductos(cursor.getString(6))
                compra.setFechacompra(cursor.getString(7))
                listaCompras.add(compra)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return listaCompras
    }

}