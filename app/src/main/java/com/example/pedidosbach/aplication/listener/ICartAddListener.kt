package com.example.pedidosbach.aplication.listener

interface ICartAddListener {
    fun onAddCartSuccess(message:String)
    fun onAddCartFailed(message:String)
}