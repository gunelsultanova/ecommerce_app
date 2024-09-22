package com.matrix.android105_android

data class Product(
    val name: String,
    val category: String,
    val price: String,
    val image: String,
    var favStatus: Boolean = false
)