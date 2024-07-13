package com.mss.application

import com.mss.application.model.ProductPrice
import com.mss.application.model.response.BrandResponse
import com.mss.domain.Brand
import com.mss.domain.exception.ErrorCode
import com.mss.domain.exception.NotFoundDataException
import com.mss.domain.repository.BrandRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class BrandProductQuery(
    private val brandRepository: BrandRepository
) {
    fun findLowestTotalPriceBrand(): BrandResponse.Detail {
        val brands = brandRepository.findAll()

        if (brands.isEmpty()) throw NotFoundDataException(errorCode = ErrorCode.NOT_FOUND_BRAND)

        return findLowestTotalPriceBrandIn(brands)
            ?: throw NotFoundDataException(errorCode = ErrorCode.NOT_FOUND_PRODUCT)
    }

    private fun findLowestTotalPriceBrandIn(brands: List<Brand>): BrandResponse.Detail? {
        return brands.flatMap { it.products }.groupBy { it.brand }.map { (brand, products) ->
            val categoryProducts = products.map {
                ProductPrice.Category(
                    id = it.id,
                    price = it.price,
                    categoryId = it.category.id,
                    categoryName = it.category.name
                )
            }

            BrandResponse.Detail(
                id = brand.id,
                name = brand.name,
                categoryProducts = categoryProducts,
                totalPrice = categoryProducts.sumOf { it.price }
            )
        }.minByOrNull { it.totalPrice }
    }
}