package com.example.pedidosbach.aplication.listener

import com.example.pedidosbach.domain.model.CartModel

interface ICartUpdateListener {
    fun onUpdateCartSuccess(message:String)
    fun onUpdateCartFailed(message:String)
}