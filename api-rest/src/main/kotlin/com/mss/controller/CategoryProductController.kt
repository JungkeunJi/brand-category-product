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
    // 구현 1
    @GetMapping("lowest-price")
    fun findAllCategoriesLowestPrice(): CategoryProductResponse {
        return emptyMap()
    }

    // 구현 3
    @GetMapping("{name}/price-range")
    fun getCategoryPriceRange(@PathVariable name: String): CategoryResponse.PriceRange {
        return emptyMap()
    }
}