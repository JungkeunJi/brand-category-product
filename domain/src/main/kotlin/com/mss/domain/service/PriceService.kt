package com.mss.domain.service

import com.mss.domain.Brand
import com.mss.domain.exception.ErrorCode
import com.mss.domain.exception.NotFoundDataException
import org.springframework.stereotype.Component

@Component
class PriceService {
    fun findLowestTotalPriceBrand(brands: List<Brand>): Brand {
        return brands.minByOrNull { brand -> brand.products.sumOf { it.price } }
            ?: throw NotFoundDataException(errorCode = ErrorCode.NOT_FOUND_BRAND)
    }
}