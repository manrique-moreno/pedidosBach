package com.example.pedidosbach.aplication.listener

import com.example.pedidosbach.domain.model.CartModel

interface ICartDeleteItemListener {
    fun onDeleteItemCartSuccess(position: Int, cart: CartModel)
    fun onDeleteItemCartFailed(message:String)
}