package com.example.appecommercekotlin.Activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.appecommercekotlin.Entity.Usuario
import com.example.appecommercekotlin.R
import com.example.appecommercekotlin.db.DbUsuario
import java.io.Serializable

class LoginActivity : AppCompatActivity() {

    private var sign_up: TextView? = null
    private var usuario_login: TextView? = null
    private var password_login:TextView? = null

    private var btnIngresar: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initView()
        bottonNavigation()
    }

    private fun bottonNavigation() {
        sign_up!!.setOnClickListener { view: View? ->
            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
        }

        btnIngresar!!.setOnClickListener { view: View? ->
            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
        }

        btnIngresar!!.setOnClickListener {
            try {
                val usuario = usuario_login!!.text.toString()
                val password = password_login!!.text.toString()
                val dbUsuario = DbUsuario(this@LoginActivity)
                val usuarioLogin = dbUsuario.validarIngreso(usuario, password)
                if (usuarioLogin != null) {
                    var intent: Intent? = null
                    val extras = getIntent().extras
                    val pagina = extras!!.getString("ACTIVITY")
                    when (pagina) {
                        "PERFIL" -> intent = Intent(this@LoginActivity, PerfilActivity::class.java)
                        "PAGO" -> {
                            intent = Intent(this@LoginActivity, PagoTarjetaActivity::class.java)
                            val intentRecibido = getIntent()
                            val infoCompra =
                                intentRecibido.getSerializableExtra("COMPRA") as Map<String, Any>?
                            intent.putExtra("COMPRA", infoCompra as Serializable?)
                        }

                        else -> intent = Intent(this@LoginActivity, MainActivity::class.java)
                    }
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    startActivity(intent)
                    DbUsuario.inicioSesion = true
                    Toast.makeText(
                        this@LoginActivity, "Bienvenido(a) " + usuarioLogin.getNombre(),
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(this@LoginActivity, "Información incorrecta", Toast.LENGTH_SHORT)
                        .show()
                }
            } catch (ex: Exception) {
                println(ex.toString())
            }
        }
    }

    private fun initView() {
        sign_up = findViewById(R.id.sign_up)
        usuario_login = findViewById(R.id.usuario_login)
        password_login = findViewById(R.id.password_login)
        btnIngresar = findViewById(R.id.btnIngresar)
    }
}