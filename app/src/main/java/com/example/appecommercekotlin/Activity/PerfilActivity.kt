package com.example.appecommercekotlin.Activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.example.appecommercekotlin.R
import com.example.appecommercekotlin.db.DbUsuario


class PerfilActivity : AppCompatActivity() {

    private var backBtn: ImageView? = null
    private var btnCerrarSesion: CardView? = null
    private var btnDatos : CardView? = null
    private var btnCompras:CardView? = null
    private var btnNotificaciones:CardView? = null
    private var textNombre_perfil: TextView? = null
    private var textId_perfil:TextView? = null
    private var textDireccion_perfil:TextView? = null
    private var btnUpdatePass:CardView? = null

    private var url = "https://api.whatsapp.com/send?phone=51924288288&text=%C2%A1Hola!%20Quiero%20recibir%20mayor%20informaci%C3%B3n%20"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil)

        initView()
        setVariable()
    }

    private fun setVariable() {
        backBtn!!.setOnClickListener { view: View? ->
            startActivity(Intent(this@PerfilActivity, MainActivity::class.java))
        }

        btnDatos!!.setOnClickListener { view: View? ->
            startActivity(Intent(this@PerfilActivity, UpdateUserActivity::class.java))
        }

        btnUpdatePass!!.setOnClickListener { view: View? ->
            startActivity(Intent(this@PerfilActivity, UpdatePasswordActivity::class.java))
        }

        btnCerrarSesion!!.setOnClickListener { view: View? ->
            DbUsuario.inicioSesion = false
            DbUsuario.idSesion = 0
            val intent = Intent(this@PerfilActivity, MainActivity::class.java)
            startActivity(intent)
        }
        if (DbUsuario.idSesion !== 0) {
            val dbUsuario = DbUsuario(this@PerfilActivity)
            val usuario = dbUsuario.listarPorId(DbUsuario.idSesion)
            textNombre_perfil!!.text = usuario!!.getNombre()
            textId_perfil!!.text = usuario.getCorreo()
            textDireccion_perfil!!.text = usuario.getDireccion()
        }
        btnCompras!!.setOnClickListener { view: View? ->
            startActivity(Intent(this@PerfilActivity, ShoppingsActivity::class.java))
        }

        btnNotificaciones!!.setOnClickListener { view: View? ->
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            intent.addCategory(Intent.CATEGORY_DEFAULT)
            startActivity(intent)
        }

    }

    private fun initView() {
        backBtn = findViewById(R.id.backBtn)
        btnNotificaciones = findViewById(R.id.btnNotificaciones)
        btnDatos = findViewById(R.id.btnDatos)
        btnCerrarSesion = findViewById(R.id.btnCerrarSesion)
        btnCompras = findViewById(R.id.btnCompras)
        textNombre_perfil = findViewById(R.id.textNombre_perfil)
        textId_perfil = findViewById(R.id.textId_perfil)
        textDireccion_perfil = findViewById(R.id.textDireccion_perfil)
        btnUpdatePass = findViewById(R.id.btnUpdatePass)
    }
}