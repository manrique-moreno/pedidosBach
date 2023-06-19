package com.example.pedidosbach.presentation.adapter.order

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pedidosbach.R
import com.example.pedidosbach.domain.model.PedidoModel

class OrderAdapter(
    private var listOrders: List<PedidoModel>,
    private val onClickListener: (Int, PedidoModel) -> Unit
) : RecyclerView.Adapter<OrderViewHolder>() {

    fun refreshListProducts(listOrder: MutableList<PedidoModel>) {
        this.listOrders = listOrder
        notifyDataSetChanged()
    }

    fun deleteOrder(position: Int, listOrder: MutableList<PedidoModel>){
        this.listOrders = listOrder
        notifyItemRemoved(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return OrderViewHolder(layoutInflater.inflate(R.layout.layout_pedido_item, parent, false))
    }

    override fun getItemCount(): Int = listOrders.size

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val orderItem = listOrders[position]
        holder.render(orderItem, onClickListener)

    }


}