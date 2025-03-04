package com.mss.application.model.response

import com.mss.application.model.ProductPrice

interface CategoryResponse {
    val id: Long
    val name: String

    data class PriceRange(
        override val id: Long,
        override val name: String,
        val lowestPriceProducts: List<ProductPrice.Brand>,
        val highestPriceProducts: List<ProductPrice.Brand>,
    ) : CategoryResponse
}