package com.mss.application

import com.mss.application.model.CategoryProduct
import com.mss.application.model.Product
import com.mss.application.model.response.CategoryProductResponse
import com.mss.application.model.response.CategoryResponse
import com.mss.domain.Category
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

        val products = productRepository.findAllByCategoryIn(listOf(category))

        if (products.isEmpty()) throw NotFoundDataException(errorCode = ErrorCode.NOT_FOUND_PRODUCT)

        val lowestPrice = products.minBy { it.price }.price
        val highestPrice = products.maxBy { it.price }.price

        val lowestPriceProducts = products.filter { it.price == lowestPrice }
            .map { Product.Brand(id = it.id, price = it.price, brandId = it.brand.id, brandName = it.brand.name) }
        val highestPriceProducts = products.filter { it.price == highestPrice }
            .map { Product.Brand(id = it.id, price = it.price, brandId = it.brand.id, brandName = it.brand.name) }

        return CategoryResponse.PriceRange(
            id = category.id,
            name = category.name,
            lowestPriceProducts = lowestPriceProducts,
            highestPriceProducts = highestPriceProducts
        )
    }

    private fun findLowestPriceCategoryProducts(categories: List<Category>): List<CategoryProduct> {
        return productRepository.findAllByCategoryIn(categories).groupBy { it.category.id }.map { (_, products) ->
            val lowestPriceProductByCategory = products.sortedByDescending { it.brand.id }.minBy { it.price }
            CategoryProduct(
                categoryId = lowestPriceProductByCategory.category.id,
                categoryName = lowestPriceProductByCategory.category.name,
                product = Product.Brand(
                    id = lowestPriceProductByCategory.id,
                    price = lowestPriceProductByCategory.price,
                    brandId = lowestPriceProductByCategory.brand.id,
                    brandName = lowestPriceProductByCategory.brand.name
                )
            )
        }
    }
}