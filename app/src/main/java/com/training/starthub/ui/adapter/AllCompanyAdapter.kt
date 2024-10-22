package com.training.starthub.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.training.starthub.databinding.CustomerAllCompaniesItemBinding
import com.training.starthub.ui.model.Company

class AllCompanyAdapter(private var companies: List<Company>) :
    RecyclerView.Adapter<AllCompanyAdapter.CompanyViewHolder>() {

    class CompanyViewHolder(private val binding: CustomerAllCompaniesItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(company: Company) {
            binding.companyName.text = company.name
            binding.companyCategory.text = company.category
            binding.companyDescription.text = company.description

            Glide.with(binding.companyImage.context)
                .load(company.imageUrl)
                .into(binding.companyImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompanyViewHolder {
        val binding = CustomerAllCompaniesItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return CompanyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CompanyViewHolder, position: Int) {
        holder.bind(companies[position])
    }

    override fun getItemCount(): Int = companies.size

    // Function to update the data in the adapter
    fun updateData(newCompanies: List<Company>) {
        companies = newCompanies
        notifyDataSetChanged()
    }
}