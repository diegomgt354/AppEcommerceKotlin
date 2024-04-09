package com.example.appecommercekotlin.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners

import com.example.appecommercekotlin.Activity.DetailActivity
import com.example.appecommercekotlin.Domain.PopularDomain
import com.example.appecommercekotlin.R
import kotlin.collections.ArrayList


class PopularAdapter(items: ArrayList<PopularDomain>) :
    RecyclerView.Adapter<PopularAdapter.Viewholder>() {

    var items: ArrayList<PopularDomain>
    var context: Context? = null

    init {
        this.items = items
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularAdapter.Viewholder {
        this.context = parent.context
        val inflate: View = LayoutInflater.from(context).inflate(R.layout.viewholder_pup_list, parent, false)
        return Viewholder(inflate)
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {
        holder.titleTxt.setText(items[position].getTitle())
        holder.feeTxt.text = "S/" + items[position].getPrice()
        holder.scoreTxt.text = "" + items[position].getScore()
        holder.reviewTxt.text = "" + items[position].getReview()
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

    override fun getItemCount(): Int {
        return items.size
    }

    inner class Viewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var titleTxt: TextView
        var feeTxt: TextView
        var scoreTxt: TextView
        var reviewTxt: TextView
        var picture: ImageView

        init {
            titleTxt = itemView.findViewById<TextView>(R.id.titleTxt)
            feeTxt = itemView.findViewById<TextView>(R.id.feeTxt)
            scoreTxt = itemView.findViewById<TextView>(R.id.scoreTxt)
            reviewTxt = itemView.findViewById<TextView>(R.id.reviewTxt)
            picture = itemView.findViewById<ImageView>(R.id.picture)
        }
    }
}