package com.example.pedidosbach.presentation.listener

interface ICartAddListener {
    fun onAddCartSuccess(message:String)
    fun onAddCartFailed(message:String)
}