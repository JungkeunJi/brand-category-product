package com.mss.domain.repository

import com.mss.domain.Category
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository

interface CategoryRepository : JpaRepository<Category, Long> {
    fun findByName(name: String): Category?

    fun findAllByNameIn(names: List<String>): List<Category>

    @EntityGraph(attributePaths = ["products", "products.brand"])
    override fun findAll():List<Category>
}