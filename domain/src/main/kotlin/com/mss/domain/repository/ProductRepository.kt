package com.mss.domain.repository

import com.mss.domain.Brand
import com.mss.domain.Category
import com.mss.domain.Product
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository

interface ProductRepository : JpaRepository<Product, Long> {
    @EntityGraph(attributePaths = ["brand", "category"])
    fun findAllByCategoryIn(categories: List<Category>): List<Product>

    @EntityGraph(attributePaths = ["brand", "category"])
    fun findAllByBrandIn(brands: List<Brand>): List<Product>
}