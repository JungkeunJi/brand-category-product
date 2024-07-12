package com.mss.controller

import com.mss.application.CategoryProductQuery
import com.mss.application.model.response.CategoryProductResponse
import com.mss.application.model.response.CategoryResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/categories")
class CategoryProductController(
    private val categoryProductQuery: CategoryProductQuery
) {
    // 구현 1. 카테고리 별 최저가격 브랜드와 상품 가격, 총액을 조회하는 API
    @GetMapping("lowest-price")
    fun findAllCategoriesLowestPrice(): CategoryProductResponse {
        return categoryProductQuery.findAllCategoriesLowestPrice()
    }

    // 구현 3. 카테고리 이름으로 최저, 최고 가격 브랜드와 상품 가격을 조회하는 API
    @GetMapping("{name}/price-range")
    fun getCategoryPriceRange(@PathVariable name: String): CategoryResponse.PriceRange {
        return categoryProductQuery.getCategoryPriceRange(name)
    }
}