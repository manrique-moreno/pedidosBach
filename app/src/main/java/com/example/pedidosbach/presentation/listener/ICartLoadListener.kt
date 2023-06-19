package com.example.pedidosbach.presentation.listener

import com.example.pedidosbach.domain.model.CartModel

interface ICartLoadListener {
    fun onLoadCartLoading()
    fun onLoadCartSuccess(cartModelList: MutableList<CartModel>)
    fun onNotCart()
    fun onLoadCartFailed(message:String)
}