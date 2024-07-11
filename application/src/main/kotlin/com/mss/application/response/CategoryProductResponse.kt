package com.mss.application.response

data class CategoryProductResponse(
    val categoryId: Long,
    val categoryName: String,
    val productId: Long,
    val productPrice: Int
)
