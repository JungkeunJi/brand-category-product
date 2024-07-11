package com.mss.controller

import com.mss.application.CategoryProductQuery
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/categories")
class CategoryProductController(
    private val categoryProductQuery: CategoryProductQuery
) {
    @GetMapping("lowest-price")
    fun findAllCategoriesLowestPrice(): Map<String, String> {
        return emptyMap()
    }

    @GetMapping("{name}/price-range")
    fun getCategoryPriceRange(@PathVariable name: String): Map<String, String> {
        return emptyMap()
    }
}