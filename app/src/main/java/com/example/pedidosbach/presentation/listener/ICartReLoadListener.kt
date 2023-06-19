package com.example.pedidosbach.presentation.listener

import com.example.pedidosbach.domain.model.CartModel

interface ICartReLoadListener {
    fun onReLoadCartSuccess(cartModelList: MutableList<CartModel>)
    fun onReLoadNotCart()
    fun onReLoadCartFailed(message:String)
}