package com.mss.domain.service

import com.mss.domain.Brand
import com.mss.domain.Category
import com.mss.domain.Product
import com.mss.domain.exception.ErrorCode
import com.mss.domain.exception.NotFoundDataException
import org.springframework.stereotype.Component

@Component
class PriceService {
    fun findLowestTotalPriceBrand(brands: List<Brand>): Brand {
        return brands.minByOrNull { brand -> brand.products.sumOf { it.price } }
            ?: throw NotFoundDataException(errorCode = ErrorCode.NOT_FOUND_BRAND)
    }

    fun findLowestPriceCategoryProducts(categories: List<Category>): List<Product> {
        if (categories.isEmpty()) throw NotFoundDataException(errorCode = ErrorCode.NOT_FOUND_CATEGORY)

        return categories.map { category ->
            category.products.sortedByDescending { it.brand!!.id }.minByOrNull { it.price }
                ?: throw NotFoundDataException(errorCode = ErrorCode.NOT_FOUND_PRODUCT, data = "카테고리: ${category.name}")
        }
    }
}