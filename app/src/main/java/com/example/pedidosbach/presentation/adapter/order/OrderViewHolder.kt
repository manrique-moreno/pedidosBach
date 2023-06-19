package com.example.pedidosbach.presentation.adapter.order

import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pedidosbach.databinding.LayoutPedidoItemBinding
import com.example.pedidosbach.domain.model.CartModel
import com.example.pedidosbach.domain.model.PedidoModel

class OrderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val binding = LayoutPedidoItemBinding.bind(view)

    fun render(orderItem: PedidoModel, onClickListener: (Int, PedidoModel) -> Unit) {
        binding.txtName.text = StringBuilder().append(orderItem.name)
        binding.txtDirection.text = StringBuilder().append(orderItem.direccion)
        binding.txtDate.text = StringBuilder().append(orderItem.fecha)
        binding.txtTime.text = StringBuilder().append(orderItem.hora)
        binding.txtMetodo.text = StringBuilder().append(orderItem.metodo)
        binding.txtTotal.text = StringBuilder().append(orderItem.total)
        showProducts(binding.txtProducts, orderItem.productos!!)

        binding.btnAtender.setOnClickListener { onClickListener(adapterPosition, orderItem) }
    }

    private fun showProducts(textView: TextView, carts: MutableList<CartModel>) {
        val stringBuilder = StringBuilder()
        for (cart in carts) {
            val descripcion = when (cart.descripcion.toString()) {
                "mediana", "pequeña", "chica", "personal", "familiar" -> cart.descripcion.toString()
                else -> ""
            }
            stringBuilder.append("- ${cart.quantity} ${cart.name} $descripcion \n")
        }
        textView.text = stringBuilder.toString().trim()
    }
}