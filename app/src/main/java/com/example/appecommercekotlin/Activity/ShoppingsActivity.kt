package com.example.appecommercekotlin.Activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appecommercekotlin.Adapter.ShoppingAdapter
import com.example.appecommercekotlin.Entity.Compra
import com.example.appecommercekotlin.R
import com.example.appecommercekotlin.db.DbCompra
import com.example.appecommercekotlin.db.DbUsuario

class ShoppingsActivity : AppCompatActivity() {

    var listaCompras: RecyclerView? = null
    private var backBtn: ImageView? = null

    private var compras: ArrayList<Compra>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shoppings)

        initView()
        setVariable()
    }

    private fun setVariable() {
        try {
            backBtn!!.setOnClickListener { view: View? ->
                startActivity(Intent(this@ShoppingsActivity, PerfilActivity::class.java))
            }
            listaCompras!!.layoutManager = LinearLayoutManager(this)
            val dbCompra = DbCompra(this@ShoppingsActivity)
            compras = java.util.ArrayList()
            val adapter = ShoppingAdapter(dbCompra.listar(DbUsuario.idSesion))
            listaCompras!!.adapter = adapter
        } catch (ex: Exception) {
            ex.toString()
        }
    }

    private fun initView() {
        listaCompras = findViewById(R.id.listaCompras)
        backBtn = findViewById(R.id.backBtn)
    }

}