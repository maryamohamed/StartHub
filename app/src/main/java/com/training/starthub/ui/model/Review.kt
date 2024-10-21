package com.training.starthub.ui.model

data class Review(
    val rating: Double = 0.0,
    val feedback: String = "",
    val imageUrl: String = "",
    val name: String = ""
)
{
    // Firebase Firestore requires a no-argument constructor for data classes.
    constructor() : this(0.0, "", "", "")
}