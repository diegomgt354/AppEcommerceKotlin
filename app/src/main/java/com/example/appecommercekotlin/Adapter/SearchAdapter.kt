package com.example.appecommercekotlin.Adapter

import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners

import com.example.appecommercekotlin.Activity.DetailActivity
import com.example.appecommercekotlin.Domain.PopularDomain
import com.example.appecommercekotlin.R
import kotlin.collections.ArrayList

class SearchAdapter (items: ArrayList<PopularDomain>) :
    RecyclerView.Adapter<SearchAdapter.Viewholder>() {

    var items: ArrayList<PopularDomain>
    var context: Context? = null

    var items_filter: ArrayList<PopularDomain>

    init {
        this.items = items
        this.items_filter = ArrayList(items)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchAdapter.Viewholder {
        this.context = parent.context
        val inflate: View = LayoutInflater.from(context).inflate(R.layout.viewholder_search_list, parent, false)
        return Viewholder(inflate)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: SearchAdapter.Viewholder, position: Int) {
        holder.titleTxt.setText(items[position].getTitle())

        val drawableResourceId = holder.itemView.resources.getIdentifier(
            items[position].getPicUrl(),
            "drawable", holder.itemView.context.packageName
        )
        Glide.with(holder.itemView.context)
            .load(drawableResourceId)
            .transform(GranularRoundedCorners(30f, 30f, 0f, 0f))
            .into(holder.picture)
        holder.itemView.setOnClickListener { view: View? ->
            val intent = Intent(
                holder.itemView.context,
                DetailActivity::class.java
            )
            intent.putExtra("object", items[position])
            holder.itemView.context.startActivity(intent)
        }
    }

    inner class Viewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var titleTxt: TextView
        var picture: ImageView

        init {
            titleTxt = itemView.findViewById(R.id.titleTxt)
            picture = itemView.findViewById(R.id.picture)
        }
    }


    fun filtrado(text : String, context: Context){


        var  longitud = text.length
        if (longitud ==  0){
            items.clear()
            items.addAll(items_filter)
        }else{
            val collection: List<PopularDomain> =
                items.filter { it.getTitle().toString().toLowerCase().contains(text.toLowerCase()) }


            items.clear()
            items.addAll(collection)

            /*
            items_filter.stream().forEach { v ->
                Toast.makeText(
                    context,
                    v.getTitle(),
                    Toast.LENGTH_SHORT
                ).show()
            }

             */
        }
        notifyDataSetChanged()
    }

}