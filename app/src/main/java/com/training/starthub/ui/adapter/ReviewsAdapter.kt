package com.training.starthub.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.training.starthub.databinding.ItemReviewsBinding
import com.training.starthub.ui.model.Review

class ReviewsAdapter(private var productList: MutableList<Review>) : RecyclerView.Adapter<ReviewsAdapter.ReviewsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemReviewsBinding.inflate(inflater, parent, false)
        return ReviewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReviewsViewHolder, position: Int) {
        val product = productList[position]
        holder.bind(product)
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    inner class ReviewsViewHolder(val binding: ItemReviewsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Review) {
            binding.apply {
                rating.rating = product.rating.toFloat()
                reviewDesc.text = product.feedback
                PersonName.text = product.name
                personImage.let {
                    Glide.with(it.context)
                        .load(product.imageUrl)
                        .into(it)
                }
            }
        }
    }

    fun setData(newProductList: List<Review>) {
        productList.clear()
        productList.addAll(newProductList)
        notifyDataSetChanged()
    }
}
