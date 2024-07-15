package com.mss.application

import com.mss.application.model.ProductPrice
import com.mss.application.model.response.BrandResponse
import com.mss.domain.repository.BrandRepository
import com.mss.domain.service.PriceService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class BrandProductQuery(
    private val brandRepository: BrandRepository,
    private val priceService: PriceService
) {
    fun findLowestTotalPriceBrand(): BrandResponse.Detail {
        val brands = brandRepository.findAll()

        val lowestTotalPriceBrand = priceService.findLowestTotalPriceBrand(brands)

        return BrandResponse.Detail(
            id = lowestTotalPriceBrand.id,
            name = lowestTotalPriceBrand.name,
            categoryProducts = lowestTotalPriceBrand.products.map {
                ProductPrice.Category(
                    id = it.id,
                    price = it.price,
                    categoryId = it.category.id,
                    categoryName = it.category.name
                )
            },
            totalPrice = lowestTotalPriceBrand.products.sumOf { it.price }
        )
    }
}