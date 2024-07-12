package com.mss.application.model

sealed interface ProductPrice {
    val id: Long
    val price: Int

    data class Brand(
        override val id: Long,
        override val price: Int,
        val brandId: Long,
        val brandName: String
    ) : ProductPrice

    data class Category(
        override val id: Long,
        override val price: Int,
        val categoryId: Long,
        val categoryName: String
    ) : ProductPrice
}