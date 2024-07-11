package com.mss.controller

import com.mss.application.BrandProductQuery
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/brands")
class BrandProductController(
    private val brandProductQuery: BrandProductQuery
) {
    @GetMapping("lowest-total-price")
    fun findLowestTotalPriceBrand(): Map<String, String> {
        return emptyMap()
    }
}