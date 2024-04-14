package com.example.appecommercekotlin.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.core.widget.doAfterTextChanged
import com.example.appecommercekotlin.R
import com.example.appecommercekotlin.db.DbUsuario

class UpdatePasswordActivity : AppCompatActivity() {

    private var btnCancelar: AppCompatButton? = null
    private var btnActualizar: AppCompatButton? = null

    private var password_update: EditText? = null
    private var password_update_repeat: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_password)

        initView()
        bottonNavigation()
    }

    private fun bottonNavigation() {

        btnCancelar!!.setOnClickListener { view: View? ->
            startActivity(Intent(this@UpdatePasswordActivity, PerfilActivity::class.java))
        }


        btnActualizar!!.setOnClickListener {
            val dbUsuario = DbUsuario(this@UpdatePasswordActivity)
            try {
                if (validarCampos()){
                    val id: Int = dbUsuario.actualizarPassword(
                        DbUsuario.idSesion,
                        password_update!!.text.toString()
                    )
                    if (id != 0) {
                        Toast.makeText(this@UpdatePasswordActivity, "Contraseña actualizada", Toast.LENGTH_SHORT)
                            .show()
                        val intent = Intent(this@UpdatePasswordActivity, PerfilActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(
                            this@UpdatePasswordActivity, "Error al actualizar la Contraseña", Toast.LENGTH_SHORT).show()
                    }
                }else{
                    Toast.makeText(
                        this@UpdatePasswordActivity, "Ingresa los valores correctos.", Toast.LENGTH_SHORT).show()
                }

            } catch (ex: Exception) {
                Toast.makeText(
                    this@UpdatePasswordActivity,
                    "Ingresa los valores correctos.",
                    Toast.LENGTH_SHORT
                ).show()
                ex.toString()
            }
        }
    }

    private fun initView() {
        btnCancelar = findViewById(R.id.btnCancelar)
        btnActualizar = findViewById(R.id.btnActualizar)
        password_update = findViewById(R.id.password_update)
        password_update_repeat = findViewById(R.id.password_update_repeat)
    }

    private fun validarCampos():Boolean{
        var respuesta = false
        if(password_update!!.text.toString().replace(" ", "").isNotEmpty()){
            if(password_update!!.text.toString().equals(password_update_repeat!!.text.toString())){
                respuesta = true
            }
        }
        return respuesta
    }
}