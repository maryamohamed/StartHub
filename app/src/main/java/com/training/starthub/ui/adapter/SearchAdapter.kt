package com.training.starthub.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.training.starthub.databinding.ItemAllProductBinding
import com.training.starthub.ui.model.CustomerProduct

class SearchAdapter(
    private var productList : List<CustomerProduct> = emptyList()
) : RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    private var filteredList : List<CustomerProduct> = productList

    inner class SearchViewHolder(val binding : ItemAllProductBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product : CustomerProduct) {
            binding.productName.text = product.name
            binding.productPrice.text = product.price.toString()
            binding.productCategory.text = product.category
            binding.productCompany.text = product.CompanyName
            binding.productImg.setImageResource(product.imageUrl.toInt())
        }
    }

    override fun onCreateViewHolder(parent : ViewGroup, viewType : Int) : SearchViewHolder {
        val binding = ItemAllProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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

    fun updateData(newList : List<CustomerProduct>) {
        productList = newList
        filteredList = newList
        notifyDataSetChanged()
    }
}