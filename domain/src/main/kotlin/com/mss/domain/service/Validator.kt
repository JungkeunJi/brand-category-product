package com.mss.domain.service

import com.mss.domain.exception.ErrorCode
import com.mss.domain.exception.InvalidRequestException
import com.mss.domain.repository.BrandRepository
import com.mss.domain.repository.CategoryRepository
import org.springframework.stereotype.Component

@Component
class Validator(
    private val brandRepository: BrandRepository,
    private val categoryRepository: CategoryRepository
) {
    fun validateCreateBrand(name: String) {
        val existingBrand = brandRepository.findByName(name)

        if (existingBrand != null) throw InvalidRequestException(errorCode = ErrorCode.INVALID_BRAND_ALREADY_EXISTING)
    }

    fun validateCreateProducts(categoryNames: List<String>) {
        requireDistinctCategoryNames(categoryNames)
        requireAllCategories(categoryNames)
    }

    fun validateUpdateProducts(categoryNames: List<String>) {
        requireDistinctCategoryNames(categoryNames)
        requireCategoryContains(categoryNames)
    }

    private fun requireCategoryContains(categoryNames: List<String>) {
        val existingCategories = categoryRepository.findAllByNameIn(categoryNames)

        if (existingCategories.size != categoryNames.size) throw InvalidRequestException(errorCode = ErrorCode.INVALID_CATEGORY_NAME_NOT_EXISTING)
    }

    private fun requireAllCategories(categoryNames: List<String>) {
        val allCategoryNames = categoryRepository.findAll().map { it.name }

        if (categoryNames.sorted() != allCategoryNames.sorted()) throw InvalidRequestException(errorCode = ErrorCode.INVALID_CATEGORY_NAME_MISSING)
    }

    private fun requireDistinctCategoryNames(categoryNames: List<String>) {
        val distinctCategoryNames = categoryNames.distinct()

        if (categoryNames.size != distinctCategoryNames.size) throw InvalidRequestException(errorCode = ErrorCode.INVALID_CATEGORY_NAME_DUPLICATION)
    }
}