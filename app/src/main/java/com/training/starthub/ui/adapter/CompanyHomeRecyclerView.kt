package com.training.starthub.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.training.starthub.R
import com.training.starthub.ui.model.ProductSold

class ProductAdapter(private val productList: List<ProductSold>) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productImage: ImageView = itemView.findViewById(R.id.product_image)
        val productName: TextView = itemView.findViewById(R.id.product_name)
        val productCategory: TextView = itemView.findViewById(R.id.product_category)
        val productPrice: TextView = itemView.findViewById(R.id.product_price)
        val productCount: TextView = itemView.findViewById(R.id.product_count)
        val editButton: ImageView = itemView.findViewById(R.id.edit_button)
        val deleteButton: ImageView = itemView.findViewById(R.id.delete_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.company_home_item, parent, false)
        return ProductViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val currentItem = productList[position]

        // Load image using Glide for Firebase Storage images
        Glide.with(holder.itemView.context)
            .load(currentItem.imageUrl)  // Firebase image URL
            .into(holder.productImage)

        holder.productName.text = currentItem.name
        holder.productCategory.text = currentItem.category
        holder.productPrice.text = "Price: ${currentItem.price}"  // Ensure price is displayed correctly
        holder.productCount.text = "Count: ${currentItem.count}"

        // Handle Edit and Delete button clicks here (add click listeners if needed)
        holder.editButton.setOnClickListener {
            // Add code to handle edit action
        }

        holder.deleteButton.setOnClickListener {
            // Add code to handle delete action
        }
    }

    override fun getItemCount(): Int {
        return productList.size
    }
}
