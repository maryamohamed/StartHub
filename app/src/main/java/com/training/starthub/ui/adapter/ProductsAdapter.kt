package com.training.starthub.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.training.starthub.databinding.CompanyHomeItemBinding
import com.training.starthub.ui.model.Product

class ProductsAdapter(private val productList: MutableList<Product>) : RecyclerView.Adapter<ProductsAdapter.ProductViewHolder>() {

    class ProductViewHolder(val binding: CompanyHomeItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = CompanyHomeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = productList[position]
        holder.binding.productName.text = product.name
        holder.binding.productPrice.text = product.price.toString()
        holder.binding.productCategory.text = product.category
        holder.binding.productDescription.text = product.description

        // Load product image using Glide
        Glide.with(holder.itemView.context)
            .load(product.imageUrl)
            .into(holder.binding.productImage)
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    // Clear the list of products
    fun clearProducts() {
        productList.clear()
    }

    // Add new products to the list
    fun addAll(products: List<Product>) {
        productList.addAll(products)
    }
}
