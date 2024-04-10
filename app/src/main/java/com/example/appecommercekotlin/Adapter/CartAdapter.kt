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
import com.example.appecommercekotlin.Helper.ManagmentCart
import com.example.appecommercekotlin.R
import java.lang.String
import kotlin.Int

class CartAdapter(
    private var listItemSelected: ArrayList<PopularDomain>,
    private var context: Context,
    private var changeNumberItemsListener: ChangeNumberItemsListener
) : RecyclerView.Adapter<CartAdapter.Viewholder>() {

    private val managmentCart: ManagmentCart = ManagmentCart(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
        val inflate: View =
            LayoutInflater.from(parent.context).inflate(R.layout.viewholder_cart, parent, false)
        return Viewholder(inflate)
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {

        changeNumberItemsListener.change()

        holder.title.setText(listItemSelected[position].getTitle())
        holder.feeEachItem.text = "S/" + listItemSelected[position].getPrice()
        holder.totalEachItem.text =
            "S/" + Math.round(listItemSelected[position].getNumberInCart() * listItemSelected[position].getPrice())
        holder.num.setText(String.valueOf(listItemSelected[position].getNumberInCart()))
        val drawableResourceId = holder.itemView.context.resources.getIdentifier(
            listItemSelected[position].getPicUrl(), "drawable", holder.itemView.context.packageName
        )
        Glide.with(holder.itemView.context)
            .load(drawableResourceId)
            .transform(GranularRoundedCorners(30f, 30f, 30f, 30f))
            .into(holder.picture)
/*
        holder.plusItem.setOnClickListener { view: View? ->
            managmentCart.plusNumberItem(listItemSelected, position, changeNumberItemsListener)
        }

        holder.minusItem.setOnClickListener { view: View? ->
            managmentCart.minusNumberItem(listItemSelected, position, changeNumberItemsListener)
        }
 */
        holder.plusItem.setOnClickListener { view: View? ->
            managmentCart.plusNumberItem(listItemSelected, position, notifity())
        }

        holder.minusItem.setOnClickListener { view: View? ->
            managmentCart.minusNumberItem(listItemSelected, position, notifity())
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
        var plusItem: TextView
        var minusItem: TextView
        var totalEachItem: TextView
        var num: TextView
        var picture: ImageView

        init {
            title = itemView.findViewById<TextView>(R.id.titleTxt)
            picture = itemView.findViewById<ImageView>(R.id.picture)
            feeEachItem = itemView.findViewById<TextView>(R.id.feeEachItem)
            totalEachItem = itemView.findViewById<TextView>(R.id.totalEachItem)
            plusItem = itemView.findViewById<TextView>(R.id.plusCartBtn)
            minusItem = itemView.findViewById<TextView>(R.id.minusCartBtn)
            num = itemView.findViewById(R.id.numItemTxt)
        }
    }
}
