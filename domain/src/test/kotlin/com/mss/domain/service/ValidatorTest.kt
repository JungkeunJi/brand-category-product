package com.mss.domain.service

import com.mss.domain.Brand
import com.mss.domain.Category
import com.mss.domain.exception.ErrorCode
import com.mss.domain.exception.InvalidRequestException
import com.mss.domain.repository.BrandRepository
import com.mss.domain.repository.CategoryRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.kotlin.times
import org.mockito.kotlin.whenever

internal class ValidatorTest {
    val brandRepository: BrandRepository = mock()
    val categoryRepository: CategoryRepository = mock()
    val validator = Validator(brandRepository, categoryRepository)

    @Test
    fun `브랜드 생성 시 이미 존재하는 이름으로 생성 불가`() {
        val name = "A"
        whenever(brandRepository.findByName(name)).thenReturn(mockBrand(name))

        val assertThrows = assertThrows<InvalidRequestException> { validator.validateCreateBrand(name) }

        verify(brandRepository, times(1)).findByName(name)
        assertEquals(ErrorCode.INVALID_BRAND_ALREADY_EXISTING, assertThrows.errorCode)
    }

    @Test
    fun `브랜드의 카테고리별 상품 생성시, 중복 카테고리 이름 요청 허용하지 않음`() {
        val categoryNames = listOf("상의", "상의", "아우터", "바지", "스니커즈", "가방", "모자", "양말", "액세서리")

        val assertThrows = assertThrows<InvalidRequestException> { validator.validateCreateProducts(categoryNames) }

        assertEquals(ErrorCode.INVALID_CATEGORY_NAME_DUPLICATION, assertThrows.errorCode)
    }

    @Test
    fun `브랜드의 카테고리별 상품 생성시, 모든 카테고리가 존재하지 않을 경우 예외`() {
        val categoryNames = listOf("아우터", "바지", "스니커즈", "가방", "모자", "양말", "액세서리")
        whenever(categoryRepository.findAll()).thenReturn(mockAllCategories())

        val assertThrows = assertThrows<InvalidRequestException> { validator.validateCreateProducts(categoryNames) }

        verify(categoryRepository, times(1)).findAll()
        assertEquals(ErrorCode.INVALID_CATEGORY_NAME_MISSING, assertThrows.errorCode)
    }

    @Test
    fun `브랜드의 카테고리별 상품 업데이트시, 중복 카테고리 이름 요청 허용하지 않음`() {
        val categoryNames = listOf("상의", "상의", "아우터", "바지", "스니커즈", "가방", "모자", "양말", "액세서리")

        val assertThrows = assertThrows<InvalidRequestException> { validator.validateUpdateProducts(categoryNames) }

        assertEquals(ErrorCode.INVALID_CATEGORY_NAME_DUPLICATION, assertThrows.errorCode)
    }

    @Test
    fun `브랜드의 카테고리별 상품 업데이트시, 존재하지 않는 카테고리 이름 요청 허용하지 않음`() {
        val categoryNames = listOf("뷰티", "상의", "아우터", "바지", "스니커즈", "가방", "모자", "양말", "액세서리")
        whenever(categoryRepository.findAllByNameIn(categoryNames)).thenReturn(mockAllCategories())

        val assertThrows = assertThrows<InvalidRequestException> { validator.validateUpdateProducts(categoryNames) }

        verify(categoryRepository, times(1)).findAllByNameIn(categoryNames)
        assertEquals(ErrorCode.INVALID_CATEGORY_NAME_NOT_EXISTING, assertThrows.errorCode)
    }

    companion object {
        fun mockBrand(name: String): Brand {
            return Brand.create(name)
        }

        fun mockAllCategories(): List<Category> {
            return listOf(
                Category.create("상의"),
                Category.create("아우터"),
                Category.create("바지"),
                Category.create("스니커즈"),
                Category.create("가방"),
                Category.create("모자"),
                Category.create("양말"),
                Category.create("액세서리"),
            )
        }
    }
}