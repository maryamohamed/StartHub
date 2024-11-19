package com.training.starthub.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.training.starthub.R

class SpecialOffersAdapter(private val onItemClicked: (Int) -> Unit) :
    RecyclerView.Adapter<SpecialOffersAdapter.SpecialOfferViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpecialOfferViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_special_offer, parent, false)
        return SpecialOfferViewHolder(view)
    }

    override fun onBindViewHolder(holder: SpecialOfferViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            onItemClicked(position)  // Trigger click event
        }
    }

    override fun getItemCount(): Int = 20

    class SpecialOfferViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}