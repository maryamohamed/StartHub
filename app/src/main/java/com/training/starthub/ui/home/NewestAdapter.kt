package com.training.starthub.ui.home


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.training.starthub.R

class NewestAdapter(private val onItemClicked: (Int) -> Unit) :
    RecyclerView.Adapter<NewestAdapter.NewestItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewestItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_newest, parent, false)
        return NewestItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewestItemViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            onItemClicked(position)  // Trigger click event
        }
    }

    override fun getItemCount(): Int = 20

    class NewestItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
