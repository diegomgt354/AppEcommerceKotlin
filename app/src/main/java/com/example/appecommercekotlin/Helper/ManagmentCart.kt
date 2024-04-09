package com.example.appecommercekotlin.Helper

import android.content.Context
import android.widget.Toast
import com.example.appecommercekotlin.Domain.PopularDomain


class ManagmentCart(private val context: Context?) {
    private val tinyDB: TinyDB

    init {
        tinyDB = TinyDB(context)
    }

    fun insertBeer(item: PopularDomain) {
        val listpop = getListCart
        var existAlready = false
        var n = 0
        for (i in listpop.indices) {
            if (listpop[i].getTitle().equals(item.getTitle())) {
                existAlready = true
                n = i
                break
            }
        }
        if (existAlready) {
            listpop[n].setNumberInCart(item.getNumberInCart())
        } else {
            listpop.add(item)
        }
        tinyDB.putListObject("CartList", listpop)
        Toast.makeText(context, "Agregado al carrito", Toast.LENGTH_SHORT).show()
    }

    val getListCart: ArrayList<PopularDomain>
        get() = tinyDB.getListObject("CartList")


    val getCount: Int
        get() = tinyDB.getListObject("CartList").size

    fun clearCart() {
        tinyDB.remove("CartList")
    }

    val getTotalFee: Double
        get() {
            val listItem = getListCart
            var fee = 0.0
            for (i in listItem.indices) {
                fee = fee + listItem[i].getPrice() * listItem[i].getNumberInCart()
            }
            return fee
        }

    fun minusNumberItem(
        listItem: ArrayList<PopularDomain>,
        position: Int,
        changeNumberItemsListener: ChangeNumberItemsListener
    ) {
        if (listItem[position].getNumberInCart() === 1) {
            listItem.removeAt(position)
        } else {
            listItem[position].setNumberInCart(listItem[position].getNumberInCart() - 1)
        }
        tinyDB.putListObject("CartList", listItem)
        changeNumberItemsListener.change()
    }

    fun plusNumberItem(
        listItem: ArrayList<PopularDomain>,
        position: Int,
        changeNumberItemsListener: ChangeNumberItemsListener
    ) {
        listItem[position].setNumberInCart(listItem[position].getNumberInCart() + 1)
        tinyDB.putListObject("CartList", listItem)
        changeNumberItemsListener.change()
    }
}
