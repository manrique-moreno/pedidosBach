package com.example.pedidosbach.presentation.view.mains

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

open class ActivityFather : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
    }

    open fun pathColorButton(uri: String, context: Context): Drawable? {
        val gradientResource =
            resources.getIdentifier(uri, "drawable", applicationContext.packageName)
        return ContextCompat.getDrawable(context, gradientResource)
    }

    open fun validateFormatEmail(correo: String): Boolean {
        val regex = Regex("^([a-zA-Z0-9_.+-])+@([a-zA-Z0-9-])+\\.([a-zA-Z0-9-.])+$")
        return regex.matches(correo)
    }

}