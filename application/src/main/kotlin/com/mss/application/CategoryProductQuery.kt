package com.mss.application

import com.mss.application.model.response.CategoryProductResponse
import com.mss.domain.exception.ErrorCode
import com.mss.domain.exception.NotFoundDataException
import com.mss.domain.repository.CategoryRepository
import com.mss.domain.repository.ProductRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class CategoryProductQuery(
    private val categoryRepository: CategoryRepository,
    private val productRepository: ProductRepository
) {
    fun findAllCategoriesLowestPrice(): CategoryProductResponse {
        val categories = categoryRepository.findAll()

        if (categories.isEmpty()) throw NotFoundDataException(errorCode = ErrorCode.NOT_FOUND_CATEGORY)

        productRepository.findAllByCategoryIn(categories)
    }
}