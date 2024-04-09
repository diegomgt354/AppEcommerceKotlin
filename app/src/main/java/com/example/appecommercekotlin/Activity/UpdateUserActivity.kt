package com.example.appecommercekotlin.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import com.example.appecommercekotlin.R

class UpdateUserActivity : AppCompatActivity() {

    private var btnCancelar: AppCompatButton? = null
    private var btnActualizar: AppCompatButton? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_user)

        initView()

        btnCancelar!!.setOnClickListener { view: View? ->
            startActivity(Intent(this@UpdateUserActivity, PerfilActivity::class.java))
        }

        btnActualizar!!.setOnClickListener { view: View? ->
            // capturamos los datos
            // actualizamos en la db
            // regresamos a la pantalla de perfil
            startActivity(Intent(this@UpdateUserActivity, PerfilActivity::class.java))
            Toast.makeText(this@UpdateUserActivity, "Usuario actualizado", Toast.LENGTH_SHORT).show()
        }
    }

    private fun initView() {
        btnCancelar = findViewById(R.id.btnCancelar)
        btnActualizar = findViewById(R.id.btnActualizar)
    }
}