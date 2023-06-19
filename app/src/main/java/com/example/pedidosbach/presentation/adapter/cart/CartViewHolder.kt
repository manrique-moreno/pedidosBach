package com.example.pedidosbach.presentation.adapter.cart

import android.app.AlertDialog
import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pedidosbach.databinding.LayoutCartItemBinding
import com.example.pedidosbach.domain.model.CartModel
import java.lang.StringBuilder

class CartViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val binding = LayoutCartItemBinding.bind(view)

    fun render(
        context: Context,
        cartItem: CartModel,
        onMinus: (LayoutCartItemBinding, CartModel) -> Unit,
        onPlus: (LayoutCartItemBinding, CartModel) -> Unit,
        onDelete: (Int, CartModel) -> Unit
    ) {
        Glide.with(context)
            .load(cartItem.image)
            .into(binding.imageView)
        binding.txtName.text = StringBuilder().append(cartItem.name)
        binding.txtDescription.text = StringBuilder("").append(cartItem.descripcion)
        binding.txtPrice.text = StringBuilder("Total: S/. ").append(cartItem.totalPrice)
        binding.txtQuantity.text = StringBuilder("").append(cartItem.quantity)

        //Event
        binding.btnMinus.setOnClickListener { onMinus(binding, cartItem) }
        binding.btnPlus.setOnClickListener { onPlus(binding, cartItem) }
        binding.btnDelete.setOnClickListener { onDelete(adapterPosition, cartItem) }
    }
}