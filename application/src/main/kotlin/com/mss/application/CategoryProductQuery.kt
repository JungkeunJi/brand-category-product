package com.mss.application

import com.mss.application.model.CategoryProduct
import com.mss.application.model.ProductPrice
import com.mss.application.model.response.CategoryProductResponse
import com.mss.application.model.response.CategoryResponse
import com.mss.domain.Category
import com.mss.domain.exception.ErrorCode
import com.mss.domain.exception.NotFoundDataException
import com.mss.domain.repository.CategoryRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class CategoryProductQuery(
    private val categoryRepository: CategoryRepository
) {
    fun findAllCategoriesLowestPrice(): CategoryProductResponse {
        val categories = categoryRepository.findAll()

        if (categories.isEmpty()) throw NotFoundDataException(errorCode = ErrorCode.NOT_FOUND_CATEGORY)

        val categoryProducts = findLowestPriceCategoryProducts(categories)

        if (categoryProducts.isEmpty()) throw NotFoundDataException(errorCode = ErrorCode.NOT_FOUND_PRODUCT)

        return CategoryProductResponse(
            categoryProducts = categoryProducts,
            totalPrice = categoryProducts.sumOf { it.product.price }
        )
    }

    fun getCategoryPriceRange(name: String): CategoryResponse.PriceRange {
        val category =
            categoryRepository.findByName(name) ?: throw NotFoundDataException(errorCode = ErrorCode.NOT_FOUND_CATEGORY)

        if (category.products.isEmpty()) throw NotFoundDataException(errorCode = ErrorCode.NOT_FOUND_PRODUCT)

        val lowestPrice = category.products.minBy { it.price }.price
        val highestPrice = category.products.maxBy { it.price }.price

        val lowestPriceProducts = category.products.filter { it.price == lowestPrice }
            .map { ProductPrice.Brand(id = it.id, price = it.price, brandId = it.brand!!.id, brandName = it.brand!!.name) }
        val highestPriceProducts = category.products.filter { it.price == highestPrice }
            .map { ProductPrice.Brand(id = it.id, price = it.price, brandId = it.brand!!.id, brandName = it.brand!!.name) }

        return CategoryResponse.PriceRange(
            id = category.id,
            name = category.name,
            lowestPriceProducts = lowestPriceProducts,
            highestPriceProducts = highestPriceProducts
        )
    }

    private fun findLowestPriceCategoryProducts(categories: List<Category>): List<CategoryProduct> {
        return categories.flatMap { it.products }.groupBy { it.category.id }.map { (_, products) ->
            val lowestPriceProductByCategory = products.sortedByDescending { it.brand!!.id }.minBy { it.price }
            CategoryProduct(
                categoryId = lowestPriceProductByCategory.category.id,
                categoryName = lowestPriceProductByCategory.category.name,
                product = ProductPrice.Brand(
                    id = lowestPriceProductByCategory.id,
                    price = lowestPriceProductByCategory.price,
                    brandId = lowestPriceProductByCategory.brand!!.id,
                    brandName = lowestPriceProductByCategory.brand!!.name
                )
            )
        }
    }
}