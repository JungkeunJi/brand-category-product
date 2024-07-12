package com.mss.application.model.response

import com.mss.application.model.Product

sealed interface BrandResponse {
    val id: Long
    val name: String

    data class Base(
        override val id: Long,
        override val name: String,
        val categoryProducts: List<Product.Category>
    ) : BrandResponse

    data class Detail(
        override val id: Long,
        override val name: String,
        val categoryProducts: List<Product.Category>,
        val totalPrice: Int
    ) : BrandResponse
}
