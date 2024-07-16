package com.mss.controller

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.mss.application.model.param.BrandCreateParam
import com.mss.application.model.param.BrandProductUpdateParam
import com.mss.application.model.param.CategoryProductParam
import com.mss.application.model.response.BrandResponse
import com.mss.domain.repository.BrandRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.transaction.annotation.Transactional

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
internal class BrandProductControllerTest {
    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var brandRepository: BrandRepository

    val brandUrlPrefix: String = "/api/brands"

    @Test
    fun `구현 2 단일 브랜드로 모든 카테고리 상품을 구매할 때 최저가격에 판매하는 브랜드와 카테고리의 상품가격, 총액을 조회하는 API`() {
        val lowestTotalPriceBrand = mockMvc
            .perform(
                get("${brandUrlPrefix}/lowest-total-price")
                    .accept(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk)
            .andReturn()

        val fetchSuccess =
            jacksonObjectMapper().readValue(
                lowestTotalPriceBrand.response.contentAsByteArray,
                BrandDetailResponseSuccess::class.java
            )

        assertEquals("D", fetchSuccess.data.name)
        assertEquals(36100, fetchSuccess.data.totalPrice)
    }

    @Test
    fun `구현 4 브랜드 및 상품 추가`() {
        val brandCreateParam = BrandCreateParam(
            "Z", listOf(
                CategoryProductParam("상의", 1000),
                CategoryProductParam("아우터", 2000),
                CategoryProductParam("바지", 3000),
                CategoryProductParam("스니커즈", 4000),
                CategoryProductParam("가방", 5000),
                CategoryProductParam("모자", 6000),
                CategoryProductParam("양말", 7000),
                CategoryProductParam("액세서리", 8000),
            )
        )

        val content =
            jacksonObjectMapper().writeValueAsString(brandCreateParam)

        val response = mockMvc
            .perform(
                post(brandUrlPrefix)
                    .content(content)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk)
            .andReturn()

        val brandBaseResponse =
            jacksonObjectMapper().readValue(response.response.contentAsByteArray, BrandBaseResponseSuccess::class.java)

        assertEquals(brandCreateParam.name, brandBaseResponse.data.name)
        assertEquals(brandCreateParam.categoryProducts.size, brandBaseResponse.data.categoryProducts.size)
    }

    @Test
    fun `구현 4 브랜드의 상품 업데이트`() {
        val brandProductUpdateParam = BrandProductUpdateParam(
            listOf(
                CategoryProductParam("상의", 100),
                CategoryProductParam("아우터", 500),
            )
        )

        val content =
            jacksonObjectMapper().writeValueAsString(brandProductUpdateParam)

        val brandName = "A"
        val response = mockMvc
            .perform(
                put("${brandUrlPrefix}/${brandName}")
                    .content(content)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk)
            .andReturn()

        val brandBaseResponse =
            jacksonObjectMapper().readValue(response.response.contentAsByteArray, BrandBaseResponseSuccess::class.java)

        assertEquals(brandName, brandBaseResponse.data.name)
        assertEquals(
            brandProductUpdateParam.categoryProducts.first { it.categoryName == "상의" }.productPrice,
            brandBaseResponse.data.categoryProducts.first { it.categoryName == "상의" }.price
        )
        assertEquals(
            brandProductUpdateParam.categoryProducts.first { it.categoryName == "아우터" }.productPrice,
            brandBaseResponse.data.categoryProducts.first { it.categoryName == "아우터" }.price
        )
    }

    @Test
    fun `구현 4 브랜치 삭제`() {
        val brandName = "D"
        assertNotEquals(null, brandRepository.findByName(brandName))

        mockMvc
            .perform(
                delete("${brandUrlPrefix}/${brandName}")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk)
            .andReturn()

        assertEquals(null, brandRepository.findByName(brandName))
    }

    data class BrandDetailResponseSuccess(
        val data: BrandResponse.Detail
    )

    data class BrandBaseResponseSuccess(
        val data: BrandResponse.Base
    )
}