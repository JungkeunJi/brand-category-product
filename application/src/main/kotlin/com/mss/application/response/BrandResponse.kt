package com.mss.application.response

data class BrandResponse(
    val id: Long,
    val name: String,
    val categoryProducts: List<CategoryProductResponse>
)
