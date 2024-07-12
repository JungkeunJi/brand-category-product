package com.mss.application

import com.mss.application.model.ProductPrice
import com.mss.application.model.param.BrandCreateParam
import com.mss.application.model.param.BrandProductUpdateParam
import com.mss.application.model.response.BrandResponse
import com.mss.domain.Brand
import com.mss.domain.Product
import com.mss.domain.exception.ErrorCode
import com.mss.domain.exception.NotFoundDataException
import com.mss.domain.repository.BrandRepository
import com.mss.domain.repository.CategoryRepository
import com.mss.domain.repository.ProductRepository
import com.mss.domain.service.Validator
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class BrandProductCommand(
    private val brandRepository: BrandRepository,
    private val productRepository: ProductRepository,
    private val categoryRepository: CategoryRepository,
    private val validator: Validator
) {
    fun createBrand(brandCreateParam: BrandCreateParam): BrandResponse.Base {
        validator.validateCreateBrand(brandCreateParam.name)
        val savedBrand = brandRepository.save(Brand.create(brandCreateParam.name))

        val categoryNames = brandCreateParam.categoryProducts.map { it.categoryName }
        validator.validateCreateProducts(categoryNames)

        val categoryProductParamMap = brandCreateParam.categoryProducts.associate { it.categoryName to it.productPrice }
        val products = categoryRepository.findAllByNameIn(categoryNames).map { category ->
            Product.create(
                savedBrand,
                category,
                categoryProductParamMap[category.name]!!
            )
        }
        val savedProducts = productRepository.saveAll(products)

        return BrandResponse.Base(
            id = savedBrand.id,
            name = savedBrand.name,
            categoryProducts = savedProducts.map {
                ProductPrice.Category(
                    id = it.id,
                    price = it.price,
                    categoryId = it.category.id,
                    categoryName = it.category.name
                )
            }
        )
    }

    fun updateBrand(name: String, brandProductUpdateParam: BrandProductUpdateParam): BrandResponse.Base {
        val brand =
            brandRepository.findByName(name) ?: throw NotFoundDataException(errorCode = ErrorCode.NOT_FOUND_BRAND)

        val categoryNames = brandProductUpdateParam.categoryProducts.map { it.categoryName }
        validator.validateUpdateProducts(categoryNames)

        val categoryProductParamMap =
            brandProductUpdateParam.categoryProducts.associate { it.categoryName to it.productPrice }
        val products = productRepository.findAllByBrandIn(listOf(brand))

        products.forEach { product ->
            categoryProductParamMap[product.category.name]?.let { product.updatePrice(it) }
        }

        return BrandResponse.Base(
            id = brand.id,
            name = brand.name,
            categoryProducts = products.map {
                ProductPrice.Category(
                    id = it.id,
                    price = it.price,
                    categoryId = it.category.id,
                    categoryName = it.category.name
                )
            }
        )
    }

    fun deleteBrand(name: String) {
        val brand =
            brandRepository.findByName(name) ?: throw NotFoundDataException(errorCode = ErrorCode.NOT_FOUND_BRAND)

        brandRepository.delete(brand)
    }
}