package com.example.pedidosbach.presentation.listener

interface IOrderUpdateListener {
    fun onOrderUpdateSuccess(position: Int, message: String)
    fun onOrderUpdateFailed(message: String)
}