package com.mss.application.model

data class CategoryProduct(
    val categoryId: Long,
    val categoryName: String,
    val product: ProductPrice.Brand
)