package com.example.appecommercekotlin.Activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import com.example.appecommercekotlin.R
import com.example.appecommercekotlin.db.DbUsuario
import com.google.android.material.textfield.TextInputEditText
import androidx.activity.result.contract.ActivityResultContracts.*

class RegisterActivity : AppCompatActivity() {

    private var correo_registro: TextInputEditText? = null
    private var usuario_registro:EditText? = null
    private var password_registro:EditText? = null
    private var nombre_registro:EditText? = null
    private var edad_registro:EditText? = null
    private var genero_registro:EditText? = null
    private var direccion_registro:EditText? = null
    private var sign_in: TextView? = null
    private var btnRegistrar: Button? = null

    //private var btnAddImg: Button? = null
    //private var imgFoto: ImageView? = null

    /*
    var pickMedia = registerForActivityResult(PickVisualMedia()){uri->
        if(uri!=null){
            imgFoto!!.setImageURI(uri)
            Toast.makeText(
                this@RegisterActivity, uri.toString(), Toast.LENGTH_SHORT).show()
        }else{
            // imagen no seleccionada
        }
    }

     */


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        initView()
        bottonNavigation()
    }

    private fun bottonNavigation() {

        //btnAddImg!!.setOnClickListener {
            //pickMedia.launch(PickVisualMediaRequest(PickVisualMedia.ImageOnly))
        //}

        correo_registro!!.doAfterTextChanged { text ->
            if (!isValidEmail(text.toString())) {
                correo_registro!!.error = "Formato de correo es incorrecto"
            }else{
                correo_registro!!.error = null
            }
        }

        edad_registro!!.doAfterTextChanged { text ->
            if (text!!.isNotEmpty() && text!!.toString().toInt() > 100) {
                edad_registro!!.error = "La edad no puede ser mayor a 100 años"
            } else {
                edad_registro!!.error = null
            }
        }

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

                if(edad_registro!!.error == null && correo_registro!!.error == null){
                    if (validacion) {
                        val id: Long = dbUsuario.guardar(
                            correo_registro!!.text.toString(),
                            usuario_registro!!.text.toString(),
                            password_registro!!.text.toString(),
                            nombre_registro!!.text.toString(),
                            edad_registro!!.text.toString().toInt(),
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
        // btnAddImg = findViewById(R.id.btnAddImg)
        // imgFoto = findViewById(R.id.imgFoto)
    }

    private fun isValidEmail(email: String): Boolean {
        val emailPattern = "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}"
        return Regex(emailPattern).matches(email)
    }
}