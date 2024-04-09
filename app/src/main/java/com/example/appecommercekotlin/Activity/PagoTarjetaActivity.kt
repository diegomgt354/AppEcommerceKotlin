package com.example.appecommercekotlin.Activity

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.appecommercekotlin.Helper.ManagmentCart
import com.example.appecommercekotlin.R
import com.example.appecommercekotlin.db.DbCompra
import com.example.appecommercekotlin.db.DbUsuario
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

class PagoTarjetaActivity : AppCompatActivity() {

    private var backBtn: ImageView? = null
    private var btnPaga: Button? = null
    private var managmentCart: ManagmentCart? = null
    private var card_name: TextView? = null
    private var card_number:TextView? = null
    private var tarjeta_name: EditText? = null
    private var tarjeta_card_number:EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pago_tarjeta)

        managmentCart = ManagmentCart(this)

        initView()
        setVariable()
    }

    private fun setVariable() {
        backBtn!!.setOnClickListener { view: View? ->
            startActivity(Intent(this@PagoTarjetaActivity, CartActivity::class.java))
        }
        btnPaga!!.setOnClickListener {
            try {
                // obtenemos los valores de CartActivity.class
                val intentRecibido = intent
                val infoCompra =
                    intentRecibido.getSerializableExtra("COMPRA") as Map<String, Any>?
                val subtotal = infoCompra!!["subtotal"].toString()
                val productos = infoCompra["productos"] as List<String>?
                val fechaLocal = LocalDateTime.now()
                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                val fecha = fechaLocal.format(formatter)
                val dbCompra = DbCompra(this@PagoTarjetaActivity)
                dbCompra.insertar(
                    DbUsuario.idSesion,
                    subtotal,
                    infoCompra["entrega"].toString(),
                    infoCompra["impuesto"].toString(),
                    infoCompra["total"].toString(),
                    java.lang.String.join(",", productos).toString(),
                    fecha
                )
                val intent = Intent(this@PagoTarjetaActivity, MainActivity::class.java)
                startActivity(intent)

                // limpiamos el carrito
                managmentCart!!.clearCart()
                Toast.makeText(this@PagoTarjetaActivity, "PAGO EXITOSO", Toast.LENGTH_SHORT).show()
            } catch (ex: Exception) {
                Toast.makeText(
                    this@PagoTarjetaActivity,
                    "Ingresa valores correctos",
                    Toast.LENGTH_SHORT
                ).show()
                println(ex.toString())
            }
        }
        tarjeta_name!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun afterTextChanged(editable: Editable) {
                card_name!!.text = tarjeta_name!!.text.toString().uppercase(Locale.getDefault())
            }
        })
        tarjeta_card_number!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun afterTextChanged(editable: Editable) {
                val number_target = tarjeta_card_number!!.text.toString().replace(" ", "")
                var newNumber = number_target
                for (i in 0 until 16 - number_target.length) {
                    newNumber = "$newNumber*"
                }
                val formattedNumber = String.format(
                    "%s %s %s %s",
                    newNumber.substring(0, 4),
                    newNumber.substring(4, 8),
                    newNumber.substring(8, 12),
                    newNumber.substring(12)
                )
                card_number!!.text = formattedNumber
            }
        })
    }

    private fun initView() {
        backBtn = findViewById(R.id.backBtn)
        btnPaga = findViewById(R.id.btnPaga)
        card_name = findViewById(R.id.card_name)
        tarjeta_name = findViewById(R.id.tarjeta_name)
        tarjeta_card_number = findViewById(R.id.tarjeta_card_number)
        card_number = findViewById(R.id.card_number)
    }

}