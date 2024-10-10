package com.training.starthub.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide  // Make sure to include Glide for image loading
import com.training.starthub.R
import com.training.starthub.ui.model.ProductSold

class SalesAdapter(
    context: Context,
    private val productSoldList: List<ProductSold>
) : ArrayAdapter<ProductSold>(context, R.layout.company_sales_item, productSoldList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.company_sales_item, parent, false)

        // Get the current product
        val product = getItem(position)

        // Initialize views from the layout
        val productImage: ImageView = view.findViewById(R.id.product_image)
        val productName: TextView = view.findViewById(R.id.product_name)
        val productPrice: TextView = view.findViewById(R.id.product_price)
        val productDiscountPrice: TextView = view.findViewById(R.id.product_discount_price)
        val productCategory: TextView = view.findViewById(R.id.product_category)
        val productCount: TextView = view.findViewById(R.id.product_count)

        // Set the product details to the views
        product?.let {
            productName.text = it.name
            productPrice.text = "${it.price}$"
            productDiscountPrice.text = "${it.discountedPrice}$"
            productCategory.text = it.category
            productCount.text = "Count: ${it.count}"

            // Load product image (using Glide)
            Glide.with(context).load(it.imageUrl).into(productImage)
        }

        return view
    }
}
