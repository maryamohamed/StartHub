package com.training.starthub.ui.home


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.training.starthub.R

class NewestAdapter : RecyclerView.Adapter<NewestAdapter.NewestItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewestItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_newest, parent, false)
        return NewestItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewestItemViewHolder, position: Int) {
        // Bind your data here
    }

    override fun getItemCount(): Int {
        // Return the number of items
        return 10
    }

    class NewestItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Initialize your views (e.g., ImageView, TextView) here
    }
}
