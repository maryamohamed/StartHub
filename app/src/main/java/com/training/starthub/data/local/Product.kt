package com.training.starthub.data.local

data class Product(
    val name: String = "",
    val price: Double = 0.0,
    val description: String = "",
    val category: String = "",
    val company: String = "",
    val image: String = ""
)

data class AllProducts(
    val items: List<Product> = listOf()
)

