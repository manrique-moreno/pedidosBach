package com.example.pedidosbach.presentation.listener

import com.example.pedidosbach.domain.model.CartModel


interface ICartCountListener {
    fun onCountCartSuccess(cartModelList: List<CartModel>)
    fun onCountCartFailed(message:String)
}