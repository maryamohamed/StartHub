package com.training.starthub.ui.model

data class Company(
    val name: String = "",
    val category: String = "",
    val description: String = "",
    val imageUrl: String = "",
    val coverImageUrl: String = "",   // take care
    val dateOfCreation: String = "",
    val email: String = "",
    val phone: String = "",
)