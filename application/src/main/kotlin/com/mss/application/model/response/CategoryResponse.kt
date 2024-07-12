package com.mss.application.model.response

import com.mss.application.model.Product

interface CategoryResponse {
    val id: Long
    val name: String

    data class PriceRange(
        override val id: Long,
        override val name: String,
        val lowestPriceProducts: List<Product.Brand>,
        val highestPriceProducts: List<Product.Brand>,
    ) : CategoryResponse
}