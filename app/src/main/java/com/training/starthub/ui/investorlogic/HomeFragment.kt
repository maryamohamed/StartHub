package com.training.starthub.ui.investorlogic

import com.training.starthub.ui.adapter.CompanyAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.training.starthub.R
import com.training.starthub.databinding.FragmentHomeBinding
import com.training.starthub.ui.model.CompanyItem

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super. onViewCreated(view, savedInstanceState)
        val companyList = listOf(
            CompanyItem(R.drawable.ic_launcher_background, "Rolex", "Luxury"),
            CompanyItem(R.drawable.ic_launcher_background, "Apple", "Tech"),
            CompanyItem(R.drawable.ic_launcher_background, "McDonald's", "Food"),
            CompanyItem(R.drawable.ic_launcher_background, "Intrax", "Marketing"),
            CompanyItem(R.drawable.ic_launcher_background, "Adidas", "Sports")
        )

        binding.bottomNavigationBar.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_search -> {
                    findNavController().navigate(R.id.action_HomeFragment_to_SearchFragment)
                    true
                }
                else -> false
            }
        }

        binding.companyRecyclerView.layoutManager = GridLayoutManager(context, 2)
        binding.companyRecyclerView.adapter = CompanyAdapter(companyList)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}



