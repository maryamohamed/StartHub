package com.training.starthub.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.training.starthub.data.local.Product
import com.training.starthub.databinding.ItemProductBinding

class SearchAdapter(
    private var productList : List<Product> = emptyList()
) : RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    private var filteredList : List<Product> = productList

    inner class SearchViewHolder(val binding : ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product : Product) {
            binding.productName.text = product.name
            binding.productPrice.text = product.price
            binding.productCategory.text = product.category
            binding.productCompany.text = product.company
            binding.productImg.setImageResource(product.imageResId)
        }
    }

    override fun onCreateViewHolder(parent : ViewGroup, viewType : Int) : SearchViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchViewHolder(binding)
    }

    override fun onBindViewHolder(holder : SearchViewHolder, position : Int) {
        holder.bind(filteredList[position])
    }

    override fun getItemCount() : Int {
        return filteredList.size
    }

    fun filter(query : String) {
        filteredList = if (query.isEmpty()) {
            emptyList() // Clear the list if query is empty
        } else {
            productList.filter { it.name.contains(query, ignoreCase = true) }
        }
        notifyDataSetChanged()
    }

    fun updateData(newList : List<Product>) {
        productList = newList
        filteredList = newList
        notifyDataSetChanged()
    }
}

