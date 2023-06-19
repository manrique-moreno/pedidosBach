package com.example.pedidosbach.presentation.listener

import com.example.pedidosbach.domain.model.CartModel

interface ICartDeleteItemListener {
    fun onDeleteItemCartSuccess(position: Int, cart: CartModel)
    fun onDeleteItemCartFailed(message:String)
}