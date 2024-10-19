package com.training.starthub.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.training.starthub.databinding.ItemInvestorSearchBinding


data class Company(
    val logoUrl: Int,
    val name: String,
    val description: String,
    val category: String
)

class InvestorSearchAdapter(private val context: Context, private val companyList: List<Company>) :
    RecyclerView.Adapter<InvestorSearchAdapter.CompanyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompanyViewHolder {
        val binding = ItemInvestorSearchBinding.inflate(LayoutInflater.from(context), parent, false)
        return CompanyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CompanyViewHolder, position: Int) {
        val company = companyList[position]
        holder.bind(company)
    }

    override fun getItemCount(): Int {
        return companyList.size
    }

    inner class CompanyViewHolder(private val binding: ItemInvestorSearchBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(company: Company) {
            Glide.with(context).load(company.logoUrl).into(binding.companyLogo)
            binding.companyName.text = company.name
            binding.inputDec.text = company.description
            binding.category.text = company.category
        }
    }
}