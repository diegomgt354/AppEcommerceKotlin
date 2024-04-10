package com.example.appecommercekotlin.Activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appecommercekotlin.Adapter.CartAdapter
import com.example.appecommercekotlin.Domain.PopularDomain
import com.example.appecommercekotlin.Helper.ChangeNumberItemsListener
import com.example.appecommercekotlin.Helper.ManagmentCart
import com.example.appecommercekotlin.R
import com.example.appecommercekotlin.db.DbUsuario
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.io.Serializable
import java.util.stream.Collectors

class CartActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMapClickListener,
    GoogleMap.OnMapLongClickListener {

    private var adapter: RecyclerView.Adapter<*>? = null
    private var recyclerView: RecyclerView? = null
    private var managmentCart: ManagmentCart? = null

    private var totalFeeTxt: TextView? = null
    private var taxTxt:TextView? = null
    private var deliveryTxt:TextView? = null
    private var totalTxt:TextView? = null
    private var btnEfectivo:CardView? = null
    private var pedirAhoraBtn:CardView? = null
    private var txt_direccion_carrito:TextView? = null
    private var tax = 0.0
    private var scrollView: ScrollView? = null
    private var backBtn: ImageView? = null

    private var productosCarrito: List<String>? = null

    private var direccion_expand : LinearLayout? = null
    private var direccion_btn_expand: ImageButton?=null

    private var metodo_expand : LinearLayout? = null
    private var metodo_btn_expand: ImageButton?=null

    private var txtLatitud : TextView?=null
    private var txtLongitud : TextView?=null
    private var mapa : GoogleMap?=null
    private var mapFrangment:SupportMapFragment?=null

    object Carrito {
        var cantidad = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        managmentCart = ManagmentCart(this)

        initView()
        setVariable()
        calculateCart()
        initList()

    }

    private fun initList() {
        
        validateBtn()

        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        recyclerView!!.layoutManager = linearLayoutManager

        adapter = CartAdapter(managmentCart!!.getListCart, this, calculateCart())

        productosCarrito = managmentCart!!
            .getListCart
            .stream()
            .map(PopularDomain::getTitle).collect(Collectors.toList()) as List<String>?

        recyclerView!!.adapter = adapter

        Carrito.cantidad = productosCarrito?.size!!
//        Toast.makeText(this, Carrito.cantidad.toString(), Toast.LENGTH_SHORT).show()
    }

    fun cantidadProductos(): Int {
        managmentCart = ManagmentCart(this)
        productosCarrito = managmentCart!!
            .getListCart
            .stream()
            .map(PopularDomain::getTitle).collect(Collectors.toList()) as List<String>?

        return productosCarrito?.size!!
    }


    private fun calculateCart() : ChangeNumberItemsListener {

        return object : ChangeNumberItemsListener {
            override fun change() {
                var percentTax = 0.18
                var delivery = 10.0

                tax = Math.round(managmentCart!!.getTotalFee * percentTax * 100.0) / 100.0

                val total =
                    (Math.round((managmentCart!!.getTotalFee + tax + delivery) * 100) / 100).toDouble()
                val itemTotal = (Math.round(managmentCart!!.getTotalFee * 100) / 100).toDouble()

                totalFeeTxt!!.text = "S/$itemTotal"
                taxTxt!!.text = "S/$tax"
                deliveryTxt!!.text = "S/$delivery"
                totalTxt!!.text = "S/$total"

                validateBtn()

            }
        }
    }

    private fun validateBtn(){
        if (managmentCart!!.getListCart.isEmpty()) {
            pedirAhoraBtn!!.isEnabled = false
            pedirAhoraBtn?.setBackgroundResource(R.drawable.deshabilitado_btn)
        } else {
            pedirAhoraBtn!!.isEnabled = true
            pedirAhoraBtn?.setBackgroundResource(R.drawable.green_btn)
        }
    }


    private fun setVariable() {
        backBtn!!.setOnClickListener { view: View? ->
            startActivity(Intent(this@CartActivity, MainActivity::class.java))
        }
        pedirAhoraBtn!!.setOnClickListener { view: View? ->
            try {
                var intent: Intent? = null
                if (DbUsuario.inicioSesion) {
                    intent = Intent(this@CartActivity, PagoTarjetaActivity::class.java)
                } else {
                    intent = Intent(this@CartActivity, LoginActivity::class.java)
                    intent.putExtra("ACTIVITY", "PAGO")
                }
                val infoCompra: MutableMap<String, Any> =
                    HashMap()
                infoCompra["subtotal"] = totalFeeTxt!!.text
                infoCompra["entrega"] = deliveryTxt!!.text
                infoCompra["impuesto"] = taxTxt!!.text
                infoCompra["total"] = totalTxt!!.text
                infoCompra["productos"] = productosCarrito!!
                intent.putExtra("COMPRA", infoCompra as Serializable)
                startActivity(intent)
            } catch (ex: Exception) {
                println(ex.toString())
            }
        }

        btnEfectivo!!.setOnClickListener { view: View? ->
            startActivity(Intent(this@CartActivity, MainActivity::class.java))
            Toast.makeText(this, "Solicitud realizada, pronto tendras tus productos", Toast.LENGTH_SHORT).show()
        }

        direccion_btn_expand!!.setOnClickListener { view :View?->
            if (direccion_expand!!.isVisible){
                direccion_expand!!.isGone = true
            }else{
                direccion_expand!!.isVisible = true
            }
        }

        metodo_btn_expand!!.setOnClickListener { view :View?->
            if (metodo_expand!!.isVisible){
                metodo_expand!!.isGone = true
            }else{
                metodo_expand!!.isVisible = true
            }
        }

        if (DbUsuario.inicioSesion) {
            val dbUsuario = DbUsuario(this@CartActivity)
            val usuario = dbUsuario.listarPorId(DbUsuario.idSesion)
            txt_direccion_carrito!!.text = usuario!!.getDireccion()
        }
    }

    private fun initView() {
        totalFeeTxt = findViewById(R.id.totalFeeTxt)
        taxTxt = findViewById(R.id.taxTxt)
        deliveryTxt = findViewById(R.id.deliveryTxt)
        totalTxt = findViewById(R.id.totalTxt)
        recyclerView = findViewById(R.id.view2)
        backBtn = findViewById(R.id.backBtn)
        scrollView = findViewById(R.id.scrollview3)
        pedirAhoraBtn = findViewById(R.id.pedirAhoraBtn)
        btnEfectivo = findViewById(R.id.btnEfectivo)
        txt_direccion_carrito = findViewById(R.id.txt_direccion_carrito)
        direccion_btn_expand = findViewById(R.id.direccion_btn_expand)
        metodo_btn_expand = findViewById(R.id.metodo_btn_expand)
        metodo_expand = findViewById(R.id.metodo_expand)
        direccion_expand = findViewById(R.id.direccion_expand)

        txtLatitud = findViewById(R.id.txtLatitud)
        txtLongitud = findViewById(R.id.txtLongitud)
        mapFrangment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFrangment!!.getMapAsync(this)
    }

    override fun onMapReady(p0: GoogleMap) {
        mapa = p0
        this.mapa?.setOnMapClickListener(this)
        this.mapa?.setOnMapLongClickListener(this)

        var peruAte = LatLng(-12.0367484, -76.9326242)
        mapa!!.addMarker(MarkerOptions().position(peruAte).title("PeruAte"))
        mapa!!.moveCamera(CameraUpdateFactory.newLatLng(peruAte))
    }
    override fun onMapClick(p0: LatLng) {
        txtLatitud!!.text = ""+ p0.latitude
        txtLongitud!!.text = ""+ p0.longitude
    }

    override fun onMapLongClick(p0: LatLng) {
        txtLatitud!!.text = ""+ p0.latitude
        txtLongitud!!.text = ""+ p0.longitude
    }
}