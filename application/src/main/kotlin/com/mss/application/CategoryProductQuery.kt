package com.mss.application

import com.mss.application.model.CategoryProduct
import com.mss.application.model.Product
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

        val categoryProducts =
            productRepository.findAllByCategoryIn(categories).groupBy { it.category.id }.map { (_, products) ->
                val lowestPriceProductByCategory = products.minBy { it.price }
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

        if (categoryProducts.isEmpty()) throw NotFoundDataException(errorCode = ErrorCode.NOT_FOUND_PRODUCT)

        return CategoryProductResponse(
            categoryProducts = categoryProducts,
            totalPrice = categoryProducts.sumOf { it.product.price }
        )
    }
}