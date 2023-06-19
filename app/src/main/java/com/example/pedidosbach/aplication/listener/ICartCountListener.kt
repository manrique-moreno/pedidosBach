package com.example.pedidosbach.aplication.listener

import com.example.pedidosbach.domain.model.CartModel


interface ICartCountListener {
    fun onCountCartSuccess(cartModelList: List<CartModel>)
    fun onCountCartFailed(message:String)
}