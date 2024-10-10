package com.training.starthub.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.training.starthub.R

// Product Data Model
data class Product(val name: String, val description: String, val category: String, val price: String, val imageResId: Int)

class ProductAdapter(private val productList: List<Product>) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    // ViewHolder class
    class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productImage: ImageView = itemView.findViewById(R.id.product_image)
        val productName: TextView = itemView.findViewById(R.id.product_name)
        val productDescription: TextView = itemView.findViewById(R.id.product_description)
        val productCategory: TextView = itemView.findViewById(R.id.product_category)
        val productPrice: TextView = itemView.findViewById(R.id.product_price)
        val editButton: ImageView = itemView.findViewById(R.id.edit_button)
        val deleteButton: ImageView = itemView.findViewById(R.id.delete_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.company_home_item, parent, false)
        return ProductViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val currentItem = productList[position]
        holder.productImage.setImageResource(currentItem.imageResId)
        holder.productName.text = currentItem.name
        holder.productDescription.text = currentItem.description
        holder.productCategory.text = currentItem.category
        holder.productPrice.text = currentItem.price

        // Handle Edit and Delete button clicks here (add click listeners if needed)
    }

    override fun getItemCount(): Int {
        return productList.size
    }
}
