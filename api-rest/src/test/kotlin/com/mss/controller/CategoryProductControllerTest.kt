package com.mss.controller

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.mss.application.model.response.CategoryProductResponse
import com.mss.application.model.response.CategoryResponse
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.transaction.annotation.Transactional

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
internal class CategoryProductControllerTest {
    @Autowired
    lateinit var mockMvc: MockMvc

    val categoryUrlPrefix: String = "/api/categories"

    @Test
    fun `구현 1 카테고리 별 최저가격 브랜드와 상품 가격, 총액을 조회하는 API`() {
        val allCategoriesLowestPriceResponse = mockMvc
            .perform(
                get("${categoryUrlPrefix}/lowest-price")
                    .accept(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk)
            .andReturn()

        val fetchSuccess =
            jacksonObjectMapper().readValue(
                allCategoriesLowestPriceResponse.response.contentAsByteArray,
                CategoryProductResponseSuccess::class.java
            )

        assertEquals(8, fetchSuccess.data.categoryProducts.size)
        assertEquals(34100, fetchSuccess.data.totalPrice)
    }

    @Test
    fun `구현 3 카테고리 이름으로 최저, 최고 가격 브랜드와 상품 가격을 조회하는 API`() {
        val categoryName = "상의"
        val categoryPriceRangeResponse = mockMvc
            .perform(
                get("${categoryUrlPrefix}/price-range")
                    .param("name", categoryName)
                    .accept(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk)
            .andReturn()

        val fetchSuccess =
            jacksonObjectMapper().readValue(
                categoryPriceRangeResponse.response.contentAsByteArray,
                CategoryPriceRangeResponseSuccess::class.java
            )

        assertEquals("상의", fetchSuccess.data.name)
        assertEquals("C", fetchSuccess.data.lowestPriceProducts[0].brandName)
        assertEquals(10000, fetchSuccess.data.lowestPriceProducts[0].price)
        assertEquals("I", fetchSuccess.data.highestPriceProducts[0].brandName)
        assertEquals(11400, fetchSuccess.data.highestPriceProducts[0].price)
    }

    data class CategoryProductResponseSuccess(
        val data: CategoryProductResponse
    )

    data class CategoryPriceRangeResponseSuccess(
        val data: CategoryResponse.PriceRange
    )
}