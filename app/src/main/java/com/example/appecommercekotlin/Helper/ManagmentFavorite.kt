package com.example.appecommercekotlin.Helper

import android.content.Context
import android.widget.Toast
import com.example.appecommercekotlin.Domain.PopularDomain

class ManagmentFavorite(private val context: Context?) {
    private val tinyDB: TinyDB

    init {
        tinyDB = TinyDB(context)
    }

    fun insertBeer(item: PopularDomain) {
        val listpop = getListFavorite
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
            listpop[n].setNumberInFavorite(item.getNumberInFavorite())
        } else {
            listpop.add(item)
        }
        tinyDB.putListObject("FavoriteList", listpop)
        Toast.makeText(context, "Agregado a favoritos", Toast.LENGTH_SHORT).show()
    }

    val getListFavorite: ArrayList<PopularDomain>
        get() = tinyDB.getListObject("FavoriteList")

    fun clearCart() {
        tinyDB.remove("FavoriteList")
    }

    val getTotalFee: Double
        get() {
            val listItem = getListFavorite
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
        if (listItem[position].getNumberInFavorite() === 1) {
            listItem.removeAt(position)
        } else {
            listItem[position].setNumberInFavorite(listItem[position].getNumberInFavorite() - 1)
        }
        tinyDB.putListObject("FavoriteList", listItem)
        changeNumberItemsListener.change()
    }

    fun plusNumberItem(
        listItem: ArrayList<PopularDomain>,
        position: Int,
        changeNumberItemsListener: ChangeNumberItemsListener
    ) {
        listItem[position].setNumberInFavorite(listItem[position].getNumberInFavorite() + 1)
        tinyDB.putListObject("FavoriteList", listItem)
        changeNumberItemsListener.change()
    }
}
