package com.training.starthub.ui.model

data class ProductSold(
    val name: String,
    val price: Int,
    val discountedPrice: Int = price,
    val category: String,
    val count: Int,
    val imageUrl: String
)