package com.mss.controller

import com.mss.application.BrandProductQuery
import com.mss.application.param.BrandCreateParam
import com.mss.application.param.BrandProductUpdateParam
import com.mss.application.response.BrandResponse
import com.mss.domain.Brand
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/brands")
class BrandProductController(
    private val brandProductQuery: BrandProductQuery
) {
    @GetMapping("lowest-total-price")
    fun findLowestTotalPriceBrand(): Map<String, String> {
        return emptyMap()
    }

    @PostMapping
    fun createBrand(@RequestBody brandCreateParam: BrandCreateParam): BrandResponse {
        return BrandResponse(0L, "", emptyList())
    }

    @PutMapping("{name}")
    fun updateBrand(
        @PathVariable("name") name: String,
        @RequestBody brandProductUpdateParam: BrandProductUpdateParam
    ): BrandResponse {
        return BrandResponse(0L, "", emptyList())
    }

    @DeleteMapping("{name}")
    fun deleteBrand(@PathVariable("name") name: String) {

    }
}