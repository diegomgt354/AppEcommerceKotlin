package com.example.appecommercekotlin.Activity

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appecommercekotlin.Adapter.PopularAdapter
import com.example.appecommercekotlin.Adapter.SearchAdapter
import com.example.appecommercekotlin.Domain.PopularDomain
import com.example.appecommercekotlin.Helper.ManagmentCart
import com.example.appecommercekotlin.R
import com.example.appecommercekotlin.db.DbHelper
import com.example.appecommercekotlin.db.DbUsuario
import java.util.Arrays
import java.util.stream.Collectors

class MainActivity : AppCompatActivity() {

    private var adapterPopular: RecyclerView.Adapter<*>? = null
    private var adapterPopularlicores: RecyclerView.Adapter<*>? = null

    private var recyclerViewPopular: RecyclerView? = null
    private var recyclerViewPopularLicores: RecyclerView? = null

    private var txtBuscardor: androidx.appcompat.widget.SearchView?=null
    private var recyclerViewBuscador: RecyclerView? =  null

    private var homeBtn: LinearLayout? = null
    private var btnPerfil: LinearLayout? = null
    private var cartBtn: LinearLayout? = null
    private var favoriteBtn: LinearLayout? = null
    private var btnVerTodo: TextView? = null
    private var btnVerTodo2: TextView? = null
    private var numberCart: TextView? = null
    private var txtBienvenido: TextView? = null
    private var txtUsuarioMain: TextView? = null
    private var imgcarrito: ImageView? = null
    private var avatarImg: ImageView? = null

    private var scrollView2: ScrollView? = null

    private var cartActivity:CartActivity?=null

    private var body:LinearLayout?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        cartActivity = CartActivity()

        initView()
        initRecyclerView()
        initRecyclerViewSearch()
        bottonNavigation()

        // actualizamos el monto del carrito
        //numberCart!!.text = productosCarrito!!.size.toString()
        //numberCart!!.text = cartActivity!!.cantidadProductos().toString()

        // creacion de la la database
        val dbHelper = DbHelper(this@MainActivity)
        val db: SQLiteDatabase = dbHelper.getWritableDatabase()
        if (db == null) {
            Toast.makeText(
                this@MainActivity,
                "Error en la creacion de la base de datos.",
                Toast.LENGTH_SHORT
            ).show()
        }

        Toast.makeText(this, CartActivity.Carrito.cantidad.toString(), Toast.LENGTH_SHORT).show()
    }

    private fun initView() {

        txtBuscardor = findViewById(R.id.txtBuscardor)
        recyclerViewBuscador = findViewById(R.id.listBuscador)

        homeBtn = findViewById(R.id.homeBtn)
        favoriteBtn = findViewById(R.id.favoriteBtn)
        btnPerfil = findViewById(R.id.btnPerfil)
        cartBtn = findViewById(R.id.cartBtn)
        avatarImg = findViewById(R.id.avatarImg)
        btnVerTodo = findViewById(R.id.btnVerTodo)
        btnVerTodo2 = findViewById(R.id.btnVerTodo2)
        recyclerViewPopular = findViewById(R.id.view1)
        recyclerViewPopularLicores = findViewById(R.id.view2)
        scrollView2 = findViewById(R.id.scrollView2)
        txtBienvenido = findViewById(R.id.txtBienvenido)
        txtUsuarioMain = findViewById(R.id.txtUsuarioMain)
        imgcarrito = findViewById(R.id.imgcarrito)
        numberCart = findViewById(R.id.numberCart)

        body =  findViewById(R.id.body)
    }

    private fun bottonNavigation() {
        homeBtn!!.setOnClickListener { view: View? ->
            scrollView2!!.fullScroll(View.FOCUS_UP)
        }
        cartBtn!!.setOnClickListener { view: View? ->
            startActivity(Intent(this@MainActivity, CartActivity::class.java))
        }
        imgcarrito!!.setOnClickListener { view: View? ->
            startActivity(Intent(this@MainActivity, CartActivity::class.java))
        }
        favoriteBtn!!.setOnClickListener { view: View? ->
            startActivity(Intent(this@MainActivity, FavoritesActivity::class.java))
        }
        btnVerTodo!!.setOnClickListener { view: View? ->
            startActivity(Intent(this@MainActivity, ProductosActivity::class.java))
        }

        btnVerTodo2!!.setOnClickListener { view: View? ->
            startActivity(Intent(this@MainActivity, ProductosActivity::class.java))
        }

        btnPerfil!!.setOnClickListener { view: View? ->
            var intent: Intent? = null
            intent = if (DbUsuario.inicioSesion) {
                Intent(this@MainActivity, PerfilActivity::class.java)
            } else {
                Intent(this@MainActivity, LoginActivity::class.java)
            }
            intent.putExtra("ACTIVITY", "PERFIL")
            startActivity(intent)
        }

        avatarImg!!.setOnClickListener { view: View? ->
            var intent: Intent? = null
            intent = if (DbUsuario.inicioSesion) {
                Intent(this@MainActivity, PerfilActivity::class.java)
            } else {
                Intent(this@MainActivity, LoginActivity::class.java)
            }
            intent!!.putExtra("ACTIVITY", "PERFIL")
            startActivity(intent)
        }

        if (DbUsuario.inicioSesion) {
            val dbUsuario = DbUsuario(this@MainActivity)
            val usuario = dbUsuario.listarPorId(DbUsuario.idSesion)
            txtBienvenido!!.text = "Bienvenido(a)"
            txtUsuarioMain!!.text = usuario?.getNombre()
        } else {
            txtBienvenido!!.text = "Holaaa!"
            txtUsuarioMain!!.text = "Inicia Sesión"
        }


        txtBuscardor?.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText!!.length > 0) {
                    recyclerViewBuscador!!.isVisible  =  true
                    body!!.isGone = true
                } else {
                    body!!.isVisible  =  true
                    recyclerViewBuscador!!.isGone = true
                }
                recyclerViewBuscador!!.invalidate() // Actualiza la vista para mostrar el cambio de visibilidad
                return false
            }
        })

    }

    private fun initRecyclerViewSearch() {
        var description =
            "Lorem Ipsum es simplemente el texto de relleno de las imprentas y archivos de texto. Lorem Ipsum ha sido el texto de relleno estándar de las industrias desde el año 1500, cuando un impresor (N. del T. persona que se dedica a la imprenta) desconocido usó una galería de textos y los mezcló de tal manera que logró hacer un libro de textos especimen. No sólo sobrevivió 500 años, sino que tambien ingresó como texto de relleno en documentos electrónicos, quedando esencialmente igual al original. Fue popularizado en los 60s con la creación de las hojas \"Letraset\", las cuales contenian pasajes de Lorem Ipsum, y más recientemente con software de autoedición, como por ejemplo Aldus PageMaker, el cual incluye versiones de Lorem Ipsum."
        var tempList = Arrays.asList(
            PopularDomain("Red Label", description, "red_label", 15, 4.0, 58.9),
            PopularDomain("Jagger", description, "jagger", 10, 3.0, 99.0),
            PopularDomain("Fourloko", description, "four", 12, 3.0, 11.0),
            PopularDomain("Pilsen", description, "pilsen", 8, 4.5, 48.0),
            PopularDomain("Jack Daniels", description, "jackdaniels", 30, 4.6, 200.0),
            PopularDomain("Johnnie Walker Black", description, "johnniewalkerblack", 25, 4.5, 120.0),
            PopularDomain("Chivas Regal", description, "chivasregal", 50, 4.2, 150.0),
            PopularDomain("Bailey", description, "bailey", 26, 4.0, 90.0),
            PopularDomain("Gran Dold Parr", description, "grandoldparr", 37, 4.1, 70.0)
        )
        var items: ArrayList<PopularDomain> = ArrayList(tempList)

        recyclerViewBuscador!!.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        adapterPopular = SearchAdapter(items)
        recyclerViewBuscador!!.adapter = adapterPopular

    }

    private fun initRecyclerView() {
        var description =
            "Lorem Ipsum es simplemente el texto de relleno de las imprentas y archivos de texto. Lorem Ipsum ha sido el texto de relleno estándar de las industrias desde el año 1500, cuando un impresor (N. del T. persona que se dedica a la imprenta) desconocido usó una galería de textos y los mezcló de tal manera que logró hacer un libro de textos especimen. No sólo sobrevivió 500 años, sino que tambien ingresó como texto de relleno en documentos electrónicos, quedando esencialmente igual al original. Fue popularizado en los 60s con la creación de las hojas \"Letraset\", las cuales contenian pasajes de Lorem Ipsum, y más recientemente con software de autoedición, como por ejemplo Aldus PageMaker, el cual incluye versiones de Lorem Ipsum."
        var tempList = Arrays.asList(
            PopularDomain("Red Label", description, "red_label", 15, 4.0, 58.9),
            PopularDomain("Jagger", description, "jagger", 10, 3.0, 99.0),
            PopularDomain("Fourloko", description, "four", 12, 3.0, 11.0),
            PopularDomain("Pilsen", description, "pilsen", 8, 4.5, 48.0)
        )
        var items: ArrayList<PopularDomain> = ArrayList(tempList)

        recyclerViewPopular!!.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        adapterPopular = PopularAdapter(items)
        recyclerViewPopular!!.adapter = adapterPopular


        var tempListLicores = Arrays.asList(
            PopularDomain("Jack Daniels", description, "jackdaniels", 30, 4.6, 200.0),
            PopularDomain("Johnnie Walker Black", description, "johnniewalkerblack", 25, 4.5, 120.0),
            PopularDomain("Chivas Regal", description, "chivasregal", 50, 4.2, 150.0),
            PopularDomain("Bailey", description, "bailey", 26, 4.0, 90.0),
            PopularDomain("Gran Dold Parr", description, "grandoldparr", 37, 4.1, 70.0)
        )
        var itemsLicores: ArrayList<PopularDomain> = ArrayList(tempListLicores)
        recyclerViewPopularLicores!!.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        adapterPopularlicores = PopularAdapter(itemsLicores)
        recyclerViewPopularLicores!!.adapter = adapterPopularlicores
    }
}