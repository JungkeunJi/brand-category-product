package com.mss.domain.repository

import com.mss.domain.Brand
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository

interface BrandRepository : JpaRepository<Brand, Long> {
    @EntityGraph(attributePaths = ["products", "products.category"])
    override fun findAll(): List<Brand>

    fun findByName(name: String): Brand?
}