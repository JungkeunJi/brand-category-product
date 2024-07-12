package com.mss.controller

import com.mss.application.BrandProductCommand
import com.mss.application.BrandProductQuery
import com.mss.application.model.param.BrandCreateParam
import com.mss.application.model.param.BrandProductUpdateParam
import com.mss.application.model.response.BrandResponse
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/brands")
class BrandProductController(
    private val brandProductQuery: BrandProductQuery,
    private val brandProductCommand: BrandProductCommand
) {
    // 구현 2. 단일 브랜드로 모든 카테고리 상품을 구매할 때 최저가격에 판매하는 브랜드와 카테고리의 상품가격, 총액을 조회하는 API
    @GetMapping("lowest-total-price")
    fun findLowestTotalPriceBrand(): BrandResponse.Detail {
        return brandProductQuery.findLowestTotalPriceBrand()
    }

    // 구현 4. 브랜드 및 상품 추가
    @PostMapping
    fun createBrand(@RequestBody brandCreateParam: BrandCreateParam): BrandResponse.Base {
        return brandProductCommand.createBrand(brandCreateParam)
    }

    // 구현 4. 브랜드의 상품 업데이트
    @PutMapping("{name}")
    fun updateBrand(
        @PathVariable("name") name: String,
        @RequestBody brandProductUpdateParam: BrandProductUpdateParam
    ): BrandResponse.Base {
        return BrandResponse.Base(0L, "", emptyList())
    }

    // 구현 4. 브랜드 삭제
    @DeleteMapping("{name}")
    fun deleteBrand(@PathVariable("name") name: String) {

    }
}