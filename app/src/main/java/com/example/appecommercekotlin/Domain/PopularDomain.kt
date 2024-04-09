package com.example.appecommercekotlin.Domain

import java.io.Serializable

class PopularDomain(title: String?,
                    description: String?,
                    picUrl: String?,
                    review: Int,
                    score: Double,
                    price: Double) : Serializable{

    private var title: String? = null
    private var description: String? = null
    private var picUrl: String? = null
    private var review = 0
    private var score = 0.0
    private var numberInCart = 0
    private var numberInFavorite = 0
    private var price = 0.0

    init {
        this.title = title
        this.description = description
        this.picUrl = picUrl
        this.review = review
        this.score = score
        this.price = price
    }

    fun getNumberInCart(): Int {
        return numberInCart
    }

    fun setNumberInCart(numberInCart: Int) {
        this.numberInCart = numberInCart
    }

    fun getNumberInFavorite(): Int {
        return numberInFavorite
    }

    fun setNumberInFavorite(numberInFavorite: Int) {
        this.numberInFavorite = numberInFavorite
    }

    fun getTitle(): String? {
        return title
    }

    fun setTitle(title: String?) {
        this.title = title
    }

    fun getDescription(): String? {
        return description
    }

    fun setDescription(description: String?) {
        this.description = description
    }

    fun getPicUrl(): String? {
        return picUrl
    }

    fun setPicUrl(picUrl: String?) {
        this.picUrl = picUrl
    }

    fun getReview(): Int {
        return review
    }

    fun setReview(review: Int) {
        this.review = review
    }

    fun getScore(): Double {
        return score
    }

    fun setScore(score: Double) {
        this.score = score
    }

    fun getPrice(): Double {
        return price
    }

    fun setPrice(price: Double) {
        this.price = price
    }
}