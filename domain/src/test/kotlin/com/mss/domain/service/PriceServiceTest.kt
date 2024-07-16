package com.mss.domain.service

import com.mss.domain.Brand
import com.mss.domain.Category
import com.mss.domain.Product
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

internal class PriceServiceTest {
    private val priceService = PriceService()

    @Test
    fun `단일 브랜드로 모든 카테고리 상품을 구매할 때 최저가격에 판매하는 브랜드 조회`() {
        val brandA = Brand.create("A")
        brandA.addProduct(Product.create(Category.create("상의"), 1000))
        brandA.addProduct(Product.create(Category.create("하의"), 2000))

        val brandB = Brand.create("B")
        brandB.addProduct(Product.create(Category.create("상의"), 10000))
        brandB.addProduct(Product.create(Category.create("하의"), 20000))

        val lowestTotalPriceBrand = priceService.findLowestTotalPriceBrand(listOf(brandA, brandB))

        assertEquals(brandA, lowestTotalPriceBrand)
    }

    @Test
    fun `카테고리 별 최저가격 브랜드와 상품 조회`() {
        val brandA = Brand.create("A")
        val brandB = Brand.create("B")

        val category1 = Category.create("상의")
        val product1 = Product.create(category1, 1000) // category1 최저가 상품
        product1.brand = brandA
        val product2 = Product.create(category1, 3000)
        product2.brand = brandB
        category1.products.addAll(mutableListOf(product1, product2))

        val category2 = Category.create("모자")
        val product3 = Product.create(category2, 12000)
        product3.brand = brandA
        val product4 = Product.create(category2, 9900) // category2 최저가 상품
        product4.brand = brandB
        category2.products.addAll(mutableListOf(product3, product4))

        val lowestPriceCategoryProducts = priceService.findLowestPriceCategoryProducts(listOf(category1, category2))

        assertEquals(2, lowestPriceCategoryProducts.size)
        assertTrue(lowestPriceCategoryProducts.containsAll(listOf(product1, product4)))
    }

    @Test
    fun `특정 카테고리 내 최저가격 상품 조회`() {
        val category1 = Category.create("상의")
        val product1 = Product.create(category1, 13000)
        product1.brand = Brand.create("A")
        val product2 = Product.create(category1, 1500) // category1 최저가 상품
        product2.brand = Brand.create("B")
        category1.products.addAll(mutableListOf(product1, product2))

        val lowestPriceProducts = priceService.findLowestPriceProducts(category1)

        assertEquals(1, lowestPriceProducts.size)
        assertTrue(lowestPriceProducts.contains(product2))
    }

    @Test
    fun `특정 카테고리 내 최고가격 상품 조회`() {
        val category1 = Category.create("상의")
        val product1 = Product.create(category1, 13000) // category1 최고가 상품
        product1.brand = Brand.create("A")
        val product2 = Product.create(category1, 1500)
        product2.brand = Brand.create("B")
        val product3 = Product.create(category1, 13000) // category1 최고가 상품
        product1.brand = Brand.create("C")
        category1.products.addAll(mutableListOf(product1, product2, product3))

        val highestPriceProducts = priceService.findHighestPriceProducts(category1)

        assertEquals(2, highestPriceProducts.size)
        assertTrue(highestPriceProducts.containsAll(listOf(product1, product3)))
    }
}