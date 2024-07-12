package com.mss.application.model.response

import com.mss.application.model.CategoryProduct

sealed interface BrandResponse {
    val id: Long
    val name: String

    data class Base(
        override val id: Long,
        override val name: String,
        val categoryProducts: List<CategoryProduct>
    ) : BrandResponse

    data class Detail(
        override val id: Long,
        override val name: String,
        val categoryProducts: List<CategoryProduct>,
        val totalPrice: Int
    ) : BrandResponse
}
