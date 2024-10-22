package com.training.starthub.ui.customerlogic.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.training.starthub.databinding.FragmentCustomerFavoritesBinding
import com.training.starthub.ui.adapter.FavoritesProductsAdapter
import com.training.starthub.ui.model.CustomerProduct

class CustomerFavoritesFragment : Fragment() {
    private lateinit var binding: FragmentCustomerFavoritesBinding
    private lateinit var viewModel: CustomerFavoriteViewModel
    private lateinit var productAdapter: FavoritesProductsAdapter
    private var productList: MutableList<CustomerProduct> = mutableListOf()
    private var favoritesIds: List<String> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCustomerFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize ViewModel
        viewModel = ViewModelProvider(this).get(CustomerFavoriteViewModel::class.java)

        // Initialize the adapter for GridView
        productAdapter = FavoritesProductsAdapter(productList) { position ->
            // Handle item click
            navigateToItemDetails(position)
        }

        // Set adapter to GridView
        binding.recyclerView.adapter = productAdapter

        // Observe favorite product IDs
        viewModel.getFavoritesIds().observe(viewLifecycleOwner) { mapOfIds ->
            favoritesIds = mapOfIds.entries.map { it.value }
            viewModel.getProductsByIds(favoritesIds)

            // Observe products list LiveData
            viewModel.productListLiveData.observe(viewLifecycleOwner) { products ->
                productAdapter.setData(products)
            }
        }
    }

    fun navigateToItemDetails(position: Int) {
        val action = CustomerFavoritesFragmentDirections.actionNavigationFavoritesToProductDetailsFragment(
            favoritesIds[position]
        )
        findNavController().navigate(action)
    }
}
