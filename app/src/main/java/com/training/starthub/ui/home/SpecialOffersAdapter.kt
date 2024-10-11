package com.training.starthub.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.training.starthub.R

class SpecialOffersAdapter : RecyclerView.Adapter<SpecialOffersAdapter.SpecialOfferViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpecialOfferViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_special_offer, parent, false)
        return SpecialOfferViewHolder(view)
    }

    override fun onBindViewHolder(holder: SpecialOfferViewHolder, position: Int) {
        // Bind  data here
    }

    override fun getItemCount(): Int {
        // Return the number of items
        return 10
    }

    class SpecialOfferViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Initialize  views here
    }
}
