package com.example.pedidosbach.aplication.listener

interface IOrderUpdateListener {
    fun onOrderUpdateSuccess(position: Int, message: String)
    fun onOrderUpdateFailed(message: String)
}