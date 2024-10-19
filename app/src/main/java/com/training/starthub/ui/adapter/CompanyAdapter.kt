package com.training.starthub.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.training.starthub.R
import com.training.starthub.ui.model.CompanyItem

class CompanyAdapter(private val companyList: List<CompanyItem>) :
    RecyclerView.Adapter<CompanyAdapter.CompanyViewHolder>() {

    inner class CompanyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val logo: ImageView = view.findViewById(R.id.companyLogo)
        val name: TextView = view.findViewById(R.id.companyName)
        val category: TextView = view.findViewById(R.id.category)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompanyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_investor_home, parent, false)
        return CompanyViewHolder(view)
    }

    override fun onBindViewHolder(holder: CompanyViewHolder, position: Int) {
        val companyItem = companyList[position]
        holder.logo.setImageResource(companyItem.logoResId)  // Set logo image
        holder.name.text = companyItem.companyName            // Set company name
        holder.category.text = companyItem.category // Set category
    }

    override fun getItemCount(): Int {
        return companyList.size
    }
}