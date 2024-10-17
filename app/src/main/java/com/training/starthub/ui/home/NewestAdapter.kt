package com.training.starthub.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.training.starthub.databinding.ItemNewestBinding
import Product

class NewestAdapter(
    private var productList: MutableList<Product>,
    private val onItemClicked: (Int) -> Unit
) : RecyclerView.Adapter<NewestAdapter.NewestViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewestViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemNewestBinding.inflate(inflater, parent, false)
        return NewestViewHolder(binding)
    }

    override fun getItemCount(): Int = productList.size

    override fun onBindViewHolder(holder: NewestViewHolder, position: Int) {
        val product = productList[position]
        holder.bind(product)
        holder.binding.root.setOnClickListener {
            onItemClicked(position)
        }
    }

    inner class NewestViewHolder(val binding: ItemNewestBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) {
            binding.apply {
                newestProductName.text = product.name
                newestPrice.text = "$${product.price}"
                productDesc.text = product.description
                newestProductCategory.text = product.category
                newestProductCompany.text = product.company

                // Load image with Glide
                Glide.with(newestProductImage.context)
                    .load(product.image)
                    .into(newestProductImage)
            }
        }
    }

    fun setData(newProductList: List<Product>) {
        productList.clear()
        productList.addAll(newProductList)
        notifyDataSetChanged()
    }
}
