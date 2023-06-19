package com.example.pedidosbach.presentation.adapter.cart

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pedidosbach.R
import com.example.pedidosbach.databinding.LayoutCartItemBinding
import com.example.pedidosbach.domain.model.CartModel

class CartAdapter (
    private val context : Context,
    private var listcart: MutableList<CartModel>,
    private val onMinus: (LayoutCartItemBinding, CartModel) -> Unit,
    private val onPlus: (LayoutCartItemBinding, CartModel) -> Unit,
    private val onDelete: (Int, CartModel) -> Unit

    ) : RecyclerView.Adapter<CartViewHolder>() {

    fun refreshListCart(listCart: MutableList<CartModel>) {
        this.listcart = listCart
        notifyDataSetChanged()
    }

    fun removeItem(position: Int){
        listcart.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return CartViewHolder(
            layoutInflater.inflate(R.layout.layout_cart_item, parent, false)
        )
    }

    override fun getItemCount(): Int = listcart.size

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val item = listcart[position]
        holder.render(context, item,onMinus, onPlus, onDelete)
    }

}