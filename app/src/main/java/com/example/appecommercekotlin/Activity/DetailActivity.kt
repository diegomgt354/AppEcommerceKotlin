package com.example.appecommercekotlin.Activity

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.appecommercekotlin.Domain.PopularDomain
import com.example.appecommercekotlin.Helper.ManagmentCart
import com.example.appecommercekotlin.Helper.ManagmentFavorite
import com.example.appecommercekotlin.R

class DetailActivity : AppCompatActivity() {

    private var addToCartBtn: Button? = null
    private var titleTxt: TextView? = null
    private var priceTxt:TextView? = null
    private var img_megusta:ImageView?=null
    private var descriptionTxt:TextView? = null
    private var reviewTxt:TextView? = null
    private var scoreTxt:TextView? = null
    private var pictureItem: ImageView? = null
    private var backBtn:ImageView? = null
    private var objecto: PopularDomain? = null
    private val numberOrder = 1
    private val numberFavorite = 1
    private var managmentCart: ManagmentCart? = null
    private var managmentFavorite: ManagmentFavorite? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        managmentCart = ManagmentCart(this)
        managmentFavorite = ManagmentFavorite(this)

        initView()
        getBundle()
    }

    private fun getBundle() {
        objecto = intent.getSerializableExtra("object") as PopularDomain?
        val drawableResourceId =
            this.resources.getIdentifier(objecto!!.getPicUrl(), "drawable", this.packageName)
        Glide.with(this).load(drawableResourceId).into(pictureItem!!)
        titleTxt!!.text = objecto!!.getTitle()
        priceTxt!!.text = "S/" + objecto!!.getPrice()
        descriptionTxt!!.text = objecto!!.getDescription()
        reviewTxt!!.text = objecto!!.getReview().toString()
        scoreTxt!!.text = objecto!!.getScore().toString()

        addToCartBtn!!.setOnClickListener { view: View? ->
            objecto!!.setNumberInCart(numberOrder)
            managmentCart!!.insertBeer(objecto!!)
        }

        img_megusta!!.setOnClickListener { view: View? ->
            objecto!!.setNumberInFavorite(numberFavorite)
            managmentFavorite!!.insertBeer(objecto!!)
        }

        backBtn!!.setOnClickListener { view: View? -> finish() }
    }

    private fun initView() {
        addToCartBtn = findViewById(R.id.addToCartBtn)
        titleTxt = findViewById(R.id.titleTxt)
        priceTxt = findViewById(R.id.priceTxt)
        descriptionTxt = findViewById(R.id.descriptionTxt)
        reviewTxt = findViewById(R.id.reviewTxt)
        scoreTxt = findViewById(R.id.scoreTxt)
        pictureItem = findViewById(R.id.pictureItem)
        backBtn = findViewById(R.id.backBtn)
        img_megusta = findViewById(R.id.img_megusta)

    }
}