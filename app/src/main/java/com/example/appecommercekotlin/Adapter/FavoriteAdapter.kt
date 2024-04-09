package com.example.appecommercekotlin.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners
import com.example.appecommercekotlin.Domain.PopularDomain
import com.example.appecommercekotlin.Helper.ChangeNumberItemsListener
import com.example.appecommercekotlin.Helper.ManagmentFavorite
import com.example.appecommercekotlin.R
import java.lang.String
import kotlin.Int

class FavoriteAdapter(
    private var listItemSelected: ArrayList<PopularDomain>,
    private var context: Context,
    private var changeNumberItemsListener: ChangeNumberItemsListener
) : RecyclerView.Adapter<FavoriteAdapter.Viewholder>() {

    private val managmentFavorite: ManagmentFavorite = ManagmentFavorite(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
        val inflate: View =
            LayoutInflater.from(parent.context).inflate(R.layout.viewholder_favorite, parent, false)
        return Viewholder(inflate)
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {

        changeNumberItemsListener.change()

        holder.title.setText(listItemSelected[position].getTitle())
        holder.feeEachItem.text = "S/" + listItemSelected[position].getPrice()

        val drawableResourceId = holder.itemView.context.resources.getIdentifier(
            listItemSelected[position].getPicUrl(), "drawable", holder.itemView.context.packageName
        )
        Glide.with(holder.itemView.context)
            .load(drawableResourceId)
            .transform(GranularRoundedCorners(30f, 30f, 30f, 30f))
            .into(holder.picture)

        holder.img_megustacolor.setOnClickListener { view: View? ->
            managmentFavorite.minusNumberItem(listItemSelected, position, notifity())
        }
    }

    private fun notifity() : ChangeNumberItemsListener {

        return object : ChangeNumberItemsListener {
            override fun change() {
                notifyDataSetChanged()
                changeNumberItemsListener.change()
            }
        }
    }


    override fun getItemCount(): Int {
        return listItemSelected.size
    }

    inner class Viewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var title: TextView
        var feeEachItem: TextView
        var picture: ImageView
        var img_megustacolor: ImageView

        init {
            title = itemView.findViewById(R.id.titleTxt)
            picture = itemView.findViewById(R.id.picture)
            feeEachItem = itemView.findViewById(R.id.feeEachItem)
            img_megustacolor = itemView.findViewById(R.id.img_megustacolor)
        }
    }
}
