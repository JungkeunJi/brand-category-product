package com.mss.controller

import com.mss.application.BrandProductQuery
import com.mss.application.model.param.BrandCreateParam
import com.mss.application.model.param.BrandProductUpdateParam
import com.mss.application.model.response.BrandResponse
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/brands")
class BrandProductController(
    private val brandProductQuery: BrandProductQuery
) {
    // 구현 2
    @GetMapping("lowest-total-price")
    fun findLowestTotalPriceBrand(): BrandResponse.Detail {
        return BrandResponse.Detail(0L, "", emptyList(), 0)
    }

    // 구현 4
    @PostMapping
    fun createBrand(@RequestBody brandCreateParam: BrandCreateParam): BrandResponse.Base {
        return BrandResponse.Base(0L, "", emptyList())
    }

    // 구현 4
    @PutMapping("{name}")
    fun updateBrand(
        @PathVariable("name") name: String,
        @RequestBody brandProductUpdateParam: BrandProductUpdateParam
    ): BrandResponse.Base {
        return BrandResponse.Base(0L, "", emptyList())
    }

    // 구현 4
    @DeleteMapping("{name}")
    fun deleteBrand(@PathVariable("name") name: String) {

    }
}