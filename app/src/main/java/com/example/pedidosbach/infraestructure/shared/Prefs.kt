package com.example.pedidosbach.infraestructure.shared

import android.content.Context

class Prefs(val context: Context) {
    private val SHARED_NAME = "dbPedidosBach"
    private val SHARED_EMAIL = "email"

    private val storage = context.getSharedPreferences(SHARED_NAME, Context.MODE_PRIVATE)

    var email : String
        get() =storage.getString(SHARED_EMAIL, "")!!
        set(value) =storage.edit().putString(SHARED_EMAIL, value).apply()
}