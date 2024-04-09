package com.example.appecommercekotlin.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

import com.example.appecommercekotlin.Entity.Compra
import com.example.appecommercekotlin.R
import java.util.ArrayList


class ShoppingAdapter(listaCompra: ArrayList<Compra>) :
    RecyclerView.Adapter<ShoppingAdapter.CompraViewHolder>() {
    var listaCompra: ArrayList<Compra>

    init {
        this.listaCompra = listaCompra
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompraViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.viewholder_shopping, null, false)
        return CompraViewHolder(view)
    }

    override fun onBindViewHolder(holder: CompraViewHolder, position: Int) {
        holder.fecha_compra.setText(listaCompra[position].getFechacompra())
        holder.subtotal_compra.setText(listaCompra[position].getSubtotal())
        holder.entrega_compra.setText(listaCompra[position].getEntrega())
        holder.impuesto_compra.setText(listaCompra[position].getImpuesto())
        holder.total_compra.setText(listaCompra[position].getTotal())
        holder.productos_compra.setText(listaCompra[position].getProductos())
    }

    override fun getItemCount(): Int {
        return listaCompra.size
    }

    inner class CompraViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var fecha_compra: TextView
        var subtotal_compra: TextView
        var entrega_compra: TextView
        var impuesto_compra: TextView
        var total_compra: TextView
        var productos_compra: TextView

        init {
            fecha_compra = itemView.findViewById<TextView>(R.id.fecha_compra)
            subtotal_compra = itemView.findViewById<TextView>(R.id.subtotal_compra)
            entrega_compra = itemView.findViewById<TextView>(R.id.entrega_compra)
            impuesto_compra = itemView.findViewById<TextView>(R.id.impuesto_compra)
            total_compra = itemView.findViewById<TextView>(R.id.total_compra)
            productos_compra = itemView.findViewById<TextView>(R.id.productos_compra)
        }
    }
}
