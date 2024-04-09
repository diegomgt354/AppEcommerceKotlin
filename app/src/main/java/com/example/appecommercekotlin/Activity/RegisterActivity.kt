package com.example.appecommercekotlin.Activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.appecommercekotlin.Entity.Usuario
import com.example.appecommercekotlin.R
import com.example.appecommercekotlin.db.DbUsuario

class RegisterActivity : AppCompatActivity() {

    private var correo_registro: EditText? = null
    private var usuario_registro:EditText? = null
    private var password_registro:EditText? = null
    private var nombre_registro:EditText? = null
    private var edad_registro:EditText? = null
    private var genero_registro:EditText? = null
    private var direccion_registro:EditText? = null
    private var sign_in: TextView? = null
    private var btnRegistrar: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        initView()
        bottonNavigation()
    }

    private fun bottonNavigation() {
        sign_in!!.setOnClickListener { view: View? ->
            startActivity(
                Intent(
                    this@RegisterActivity,
                    LoginActivity::class.java
                )
            )
        }
        btnRegistrar!!.setOnClickListener {
            val dbUsuario = DbUsuario(this@RegisterActivity)
            try {
                var validacion = true
                val usuariosExistentes = dbUsuario.listar()
                for (user in usuariosExistentes) {
                    if (user!!.getCorreo().equals(correo_registro!!.text.toString())) {
                        validacion = false
                        break
                    }
                }
                if (validacion) {
                    val id: Long = dbUsuario.guardar(
                        correo_registro!!.text.toString(),
                        usuario_registro!!.text.toString(),
                        password_registro!!.text.toString(),
                        nombre_registro!!.text.toString(), edad_registro!!.text.toString().toInt(),
                        genero_registro!!.text.toString(),
                        direccion_registro!!.text.toString()
                    )
                    if (id != 0L) {
                        Toast.makeText(this@RegisterActivity, "Usuario creado", Toast.LENGTH_SHORT)
                            .show()
                        val intent = Intent(this@RegisterActivity, MainActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(
                            this@RegisterActivity, "Error al crear usuario", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(
                        this@RegisterActivity, "Correo electronico ya registrado", Toast.LENGTH_SHORT).show()
                    correo_registro!!.requestFocus()
                }
            } catch (ex: Exception) {
                Toast.makeText(
                    this@RegisterActivity,
                    "Ingresa los valores correctos.",
                    Toast.LENGTH_SHORT
                ).show()
                ex.toString()
            }
        }
    }

    private fun initView() {
        correo_registro = findViewById(R.id.correo_registro)
        usuario_registro = findViewById(R.id.usuario_registro)
        direccion_registro = findViewById(R.id.direccion_registro)
        password_registro = findViewById(R.id.password_registro)
        nombre_registro = findViewById(R.id.nombre_registro)
        edad_registro = findViewById(R.id.edad_registro)
        genero_registro = findViewById(R.id.genero_registro)
        btnRegistrar = findViewById(R.id.btnRegistrar)
        sign_in = findViewById(R.id.sign_in)
    }
}