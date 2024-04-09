package com.example.appecommercekotlin.Activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appecommercekotlin.Adapter.PopularAdapter
import com.example.appecommercekotlin.Adapter.SpacingItemDecoration
import com.example.appecommercekotlin.Domain.PopularDomain
import com.example.appecommercekotlin.R
import java.util.Arrays

class ProductosActivity : AppCompatActivity() {

    private var adapterPopular: RecyclerView.Adapter<*>? = null
    private var recyclerViewPopular: RecyclerView? = null
    private var backBtn: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_productos)

        initView()
        bottonNavigation()
        initRecyclerView()
    }


    private fun initView() {
        backBtn = findViewById(R.id.backBtn)
        recyclerViewPopular = findViewById(R.id.view2)
    }

    private fun bottonNavigation(){
        backBtn!!.setOnClickListener { view: View? ->
            startActivity(
                Intent(this@ProductosActivity, MainActivity::class.java))
        }
    }

    private fun initRecyclerView() {
        val description =
            "Lorem Ipsum es simplemente el texto de relleno de las imprentas y archivos de texto. Lorem Ipsum ha sido el texto de relleno estándar de las industrias desde el año 1500, cuando un impresor (N. del T. persona que se dedica a la imprenta) desconocido usó una galería de textos y los mezcló de tal manera que logró hacer un libro de textos especimen. No sólo sobrevivió 500 años, sino que tambien ingresó como texto de relleno en documentos electrónicos, quedando esencialmente igual al original. Fue popularizado en los 60s con la creación de las hojas \"Letraset\", las cuales contenian pasajes de Lorem Ipsum, y más recientemente con software de autoedición, como por ejemplo Aldus PageMaker, el cual incluye versiones de Lorem Ipsum."
        val tempList: List<PopularDomain> = Arrays.asList<PopularDomain>(
            PopularDomain("Red Label", description, "red_label", 15, 4.0, 58.9),
            PopularDomain("Jagger", description, "jagger", 10, 3.0, 99.0),
            PopularDomain("Fourloko", description, "four", 12, 3.0, 11.0),
            PopularDomain("Pilsen", description, "pilsen", 8, 4.5, 48.0),
            PopularDomain("Jack Daniels", description, "jackdaniels", 30, 4.6, 200.0),
            PopularDomain("Johnnie Walker Black", description, "johnniewalkerblack", 25, 4.5, 120.0),
            PopularDomain("Chivas Regal", description, "chivasregal", 50, 4.2, 150.0),
            PopularDomain("Bailey", description, "bailey", 26, 4.0, 90.0),
            PopularDomain("Gran Dold Parr", description, "grandoldparr", 37, 4.1, 70.0),
            PopularDomain("Gatorade", description, "gatorade", 37, 4.5, 3.0),
            PopularDomain("Redbull", description, "redbull", 37, 3.8, 10.0),
            PopularDomain("Fanta", description, "fanta", 37, 4.3, 6.0),
            PopularDomain("Pepsi", description, "pepsi", 37, 4.1, 7.0),
            PopularDomain("Coca-Cola", description, "cocacola", 37, 4.9, 3.5),
            PopularDomain("Sprite", description, "sprite", 37, 4.5, 8.0)
        )
        val items = ArrayList(tempList)
        val columnas = 2
        recyclerViewPopular = findViewById(R.id.view2)
        recyclerViewPopular!!.setLayoutManager(GridLayoutManager(this, columnas))
        recyclerViewPopular!!.addItemDecoration(SpacingItemDecoration(columnas, -16, true))
        adapterPopular = PopularAdapter(items)
        recyclerViewPopular!!.setAdapter(adapterPopular)
    }

}