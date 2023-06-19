package com.example.pedidosbach.presentation.adapter.product

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pedidosbach.R
import com.example.pedidosbach.domain.model.product.ProductResponse

class ProductAdapter(
    private val context : Context,
    private var listProducts: List<ProductResponse>,
    private val onClickListener: (ProductResponse) -> Unit
) : RecyclerView.Adapter<ProductViewHolder>() {

    fun refreshListProducts(listProducts: MutableList<ProductResponse>) {
        this.listProducts = listProducts
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ProductViewHolder(
            layoutInflater.inflate(R.layout.layout_product_item, parent, false)
        )
    }

    override fun getItemCount(): Int = listProducts.size

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val item = listProducts[position]
        holder.render(context, item, onClickListener)
    }
}