package com.example.pedidosbach.presentation.adapter.product

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pedidosbach.databinding.LayoutProductItemBinding
import com.example.pedidosbach.domain.model.product.ProductResponse
import java.lang.StringBuilder
import java.math.BigDecimal
import java.math.RoundingMode

class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val binding = LayoutProductItemBinding.bind(view)

    fun render(
        context: Context, productItem: ProductResponse, onClickListener: (ProductResponse) -> Unit
    ) {
        Glide.with(context)
            .load(productItem.image)
            .into(binding.imageView)
        binding.txtName.text = StringBuilder().append(productItem.name)
        binding.txtDescripcion.text = StringBuilder().append(productItem.descripcion)

        if (tieneDecimales(productItem.price!!.toDouble())){
            var bd = BigDecimal(productItem.price)
            bd = bd.setScale(2, RoundingMode.HALF_UP)
            binding.txtPrice.text = StringBuilder("S/. ").append(bd)
        }else{
            binding.txtPrice.text = StringBuilder("S/. ").append(productItem.price)
        }

        binding.addCart.setOnClickListener { onClickListener(productItem) }
    }

    fun tieneDecimales(numero: Double): Boolean {
        val numeroDouble = numero.toDouble()
        return numeroDouble % 1 != 0.0
    }
}