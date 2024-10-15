package com.training.starthub.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.training.starthub.R
import com.training.starthub.ui.model.Product

class SalesAdapter(
    context: Context,
    private val productList: MutableList<Product>) :
    ArrayAdapter<Product>(context, R.layout.company_sales_item, productList) {

    override fun getCount(): Int {
        return productList.size
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.company_sales_item, parent, false)

        val product = productList[position]

        val productImage: ImageView = view.findViewById(R.id.product_image)
        val productName: TextView = view.findViewById(R.id.product_name)
        val productPrice: TextView = view.findViewById(R.id.product_price)
        val productCategory: TextView = view.findViewById(R.id.product_category)
        val productCount: TextView = view.findViewById(R.id.product_count)

        product?.let {
            productName.text = it.name
            productPrice.text = "${it.price}$"
            productCategory.text = it.category
            productCount.text = "Count: ${it.sold}"

            Glide.with(context).load(it.imageUrl).into(productImage)
        }

        return view
    }

    fun clearProducts() {
        productList.clear()
    }

    fun addAll(products: List<Product>) {
        productList.addAll(products)
    }
}