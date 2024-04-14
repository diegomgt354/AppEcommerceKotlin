package com.example.appecommercekotlin.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.core.widget.doAfterTextChanged
import com.example.appecommercekotlin.R
import com.example.appecommercekotlin.db.DbUsuario
import com.google.android.material.textfield.TextInputEditText

class UpdateUserActivity : AppCompatActivity() {

    private var btnCancelar: AppCompatButton? = null
    private var btnActualizar: AppCompatButton? = null

    // private var correo_registro: TextInputEditText? = null
    // private var usuario_registro: EditText? = null
    // private var password_registro: EditText? = null
    private var nombre_registro: EditText? = null
    private var edad_registro: EditText? = null
    private var genero_registro: EditText? = null
    private var direccion_registro: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_user)

        initView()
        setVariables()
        bottonNavigation()



    }

    private fun setVariables() {
        if (DbUsuario.idSesion !== 0) {
            val dbUsuario = DbUsuario(this@UpdateUserActivity)
            val usuario = dbUsuario.listarPorId(DbUsuario.idSesion)
            nombre_registro!!.setText(usuario!!.getNombre())
            edad_registro!!.setText(usuario!!.getEdad().toString())
            genero_registro!!.setText(usuario!!.getGenero())
            direccion_registro!!.setText(usuario!!.getDireccion())
        }
    }

    private fun bottonNavigation() {

        btnCancelar!!.setOnClickListener { view: View? ->
            startActivity(Intent(this@UpdateUserActivity, PerfilActivity::class.java))
        }

        edad_registro!!.doAfterTextChanged { text ->
            if (text!!.isNotEmpty() && text!!.toString().toInt() > 100) {
                edad_registro!!.error = "La edad no puede ser mayor a 100 años"
            } else {
                edad_registro!!.error = null
            }
        }


        btnActualizar!!.setOnClickListener {
            val dbUsuario = DbUsuario(this@UpdateUserActivity)
            try {

                if(edad_registro!!.error == null){
                    val id: Int = dbUsuario.actualizar(
                        DbUsuario.idSesion,
                        // correo_registro!!.text.toString(),
                        // usuario_registro!!.text.toString(),
                        // password_registro!!.text.toString(),
                        nombre_registro!!.text.toString(),
                        edad_registro!!.text.toString().toInt(),
                        genero_registro!!.text.toString(),
                        direccion_registro!!.text.toString()
                    )
                    if (id != 0) {
                        Toast.makeText(this@UpdateUserActivity, "Usuario actualizado", Toast.LENGTH_SHORT)
                            .show()
                        val intent = Intent(this@UpdateUserActivity, PerfilActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(
                            this@UpdateUserActivity, "Error al actualizar usuario", Toast.LENGTH_SHORT).show()
                    }
                }

            } catch (ex: Exception) {
                Toast.makeText(
                    this@UpdateUserActivity,
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

        // correo_registro = findViewById(R.id.correo_registro)
        // usuario_registro = findViewById(R.id.usuario_registro)
        direccion_registro = findViewById(R.id.direccion_registro)
        // password_registro = findViewById(R.id.password_registro)
        nombre_registro = findViewById(R.id.nombre_registro)
        edad_registro = findViewById(R.id.edad_registro)
        genero_registro = findViewById(R.id.genero_registro)
    }
}