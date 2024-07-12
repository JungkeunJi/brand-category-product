package com.mss.application.model

sealed interface CategoryProduct {
    val categoryId: Long
    val categoryName: String
    val productId: Long
    val productPrice: Int

    data class Base(
        override val categoryId: Long,
        override val categoryName: String,
        override val productId: Long,
        override val productPrice: Int
    ) : CategoryProduct

    data class Detail(
        override val categoryId: Long,
        override val categoryName: String,
        val brandId: Long,
        val brandName: String,
        override val productId: Long,
        override val productPrice: Int
    ) : CategoryProduct
}
