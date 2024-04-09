package com.example.appecommercekotlin.Activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.appecommercekotlin.R

class PresentationActivity : AppCompatActivity() {

    private var animacion: Animation? = null
    private var logoimg: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_presentation)
        // agregamos animaciones
        animacion = AnimationUtils.loadAnimation(this, R.anim.desplazamiento_abajo)
        logoimg = findViewById(R.id.logoimg)
        logoimg?.setAnimation(animacion)

        bottonNavigation()

    }

    private fun bottonNavigation() {
        // vamos a MainActivity
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this@PresentationActivity, MainActivity::class.java))
            finish()
        }, 3000)
    }

}