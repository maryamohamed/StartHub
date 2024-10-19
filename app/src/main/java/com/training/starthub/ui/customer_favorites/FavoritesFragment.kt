package com.training.starthub.ui.customer_favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.training.starthub.R
import com.training.starthub.ui.adapter.ProductAdapter
import com.training.starthub.ui.model.Product
import com.training.starthub.ui.customer_favorites.FavoritesViewModel

class FavoritesFragment : Fragment() {

    private lateinit var favoritesGrid: GridView
    private lateinit var productAdapter: ProductAdapter
    private lateinit var favoritesViewModel: FavoritesViewModel
    private var productList: MutableList<Product> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout
        val view = inflater.inflate(R.layout.fragment_favorites, container, false)

        // Initialize ViewModel
        favoritesViewModel = ViewModelProvider(this).get(FavoritesViewModel::class.java)

        // Set up the GridView
        favoritesGrid = view.findViewById(R.id.favorites_grid)
        productAdapter = ProductAdapter(context, productList)
        favoritesGrid.adapter = productAdapter

        // Observe changes in product list
        favoritesViewModel.getFavoriteProducts().observe(viewLifecycleOwner) { products ->
            if (products != null) {
                productList.clear()
                productList.addAll(products)
                productAdapter.notifyDataSetChanged()
            } else {
                Toast.makeText(context, "Failed to fetch favorites", Toast.LENGTH_SHORT).show()
            }
        }

        // Fetch favorite products
        favoritesViewModel.fetchFavoriteProducts()

        return view
    }
}
