package com.mss.application.model.response

import com.mss.application.model.CategoryProduct

data class CategoryProductResponse(
    val categoryProducts: List<CategoryProduct>,
    val totalPrice: Int
)