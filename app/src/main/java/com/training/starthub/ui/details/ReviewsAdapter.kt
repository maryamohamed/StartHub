package com.training.starthub.ui.details


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.training.starthub.R

class ReviewsAdapter : RecyclerView.Adapter<ReviewsAdapter.ReviewsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_reviews, parent, false)
        return ReviewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReviewsViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return 30
    }

    class ReviewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        }
    }
