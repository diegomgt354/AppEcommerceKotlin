package com.example.appecommercekotlin.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appecommercekotlin.Adapter.FavoriteAdapter
import com.example.appecommercekotlin.Domain.PopularDomain
import com.example.appecommercekotlin.Helper.ChangeNumberItemsListener
import com.example.appecommercekotlin.Helper.ManagmentFavorite
import com.example.appecommercekotlin.R
import java.util.stream.Collectors

class FavoritesActivity : AppCompatActivity() {

    private var backBtn: ImageView? = null

    private var adapter: RecyclerView.Adapter<*>? = null
    private var recyclerView: RecyclerView? = null
    private var managmentFavorite: ManagmentFavorite? = null

    private var productosFavoritos: List<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorites)

        managmentFavorite = ManagmentFavorite(this)

        initView()
        bottonNavigation()
        initList()
    }

    private fun initView() {
        backBtn = findViewById(R.id.backBtn)
        recyclerView = findViewById(R.id.viewFavoritos)
    }

    private fun initList() {

        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        recyclerView!!.layoutManager = linearLayoutManager

        adapter = FavoriteAdapter(managmentFavorite!!.getListFavorite, this, calculateFavorite())

        productosFavoritos = managmentFavorite!!
            .getListFavorite
            .stream()
            .map(PopularDomain::getTitle).collect(Collectors.toList()) as List<String>?

        recyclerView!!.adapter = adapter
    }

    private fun calculateFavorite() : ChangeNumberItemsListener {

        return object : ChangeNumberItemsListener {
            override fun change() {


            }
        }
    }

    private fun bottonNavigation() {
        backBtn!!.setOnClickListener { view: View? ->
            startActivity(Intent(this@FavoritesActivity, MainActivity::class.java))
        }
    }
}