package com.training.starthub.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.training.starthub.databinding.ItemNewestBinding
import com.training.starthub.ui.model.CustomerProduct

class NewestAdapter(
    private var productList: MutableList<CustomerProduct>,
    private val onItemClicked: (position: Int) -> Unit  // Pass the position to the click listener
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
            onItemClicked(position)  // Trigger the click event with the item's position
        }
    }

    inner class NewestViewHolder(val binding: ItemNewestBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(product: CustomerProduct) {
            binding.apply {
                newestProductName.text = product.name
                newestPrice.text = "$${product.price}"
                productDesc.text = product.description
                newestProductCategory.text = product.category
                newestProductCompany.text = product.CompanyName
                newestProductImage.let {
                    Glide.with(it.context)
                        .load(product.imageUrl)
                        .into(it)
                }
            }
        }
    }

    fun setData(newProductList: List<CustomerProduct>) {
        productList.clear()
        productList.addAll(newProductList)
        notifyDataSetChanged()
    }
}
