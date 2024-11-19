package com.training.starthub.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.training.starthub.databinding.ItemAllProductBinding
import com.training.starthub.ui.model.CustomerProduct

class AllProductsAdapter(
    private var productList: MutableList<CustomerProduct>,
    private val onItemClicked: (Int) -> Unit) : RecyclerView.Adapter<AllProductsAdapter.AllProductsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllProductsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemAllProductBinding.inflate(inflater, parent, false)
        return AllProductsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AllProductsViewHolder, position: Int) {
        val product = productList[position]
        holder.bind(product)
        holder.binding.root.setOnClickListener {
            onItemClicked(position)  // Trigger the click event with the item's position
        }
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    inner class AllProductsViewHolder(val binding: ItemAllProductBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(product: CustomerProduct) {
            binding.apply {
                productName.text = product.name
                productPrice.text = "$${product.price}"
                productCategory.text = product.category
                productCompany.text = product.CompanyName
                productImg.let {
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