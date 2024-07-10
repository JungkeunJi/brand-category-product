package com.mss.domain

import com.mss.domain.shared.BaseEntity
import jakarta.persistence.*

@Entity
@Table(
    name = "brand", uniqueConstraints = [
        UniqueConstraint(
            name = "uk_brand_category",
            columnNames = ["brand_id", "category_id"]
        )
    ]
)
class BrandCategoryProduct private constructor(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    val brand: Brand,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    val category: Category,

    product: Product
) : BaseEntity() {
    @Id
    @Column(nullable = false, name = "product_id")
    val productId: Long = 0L

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "product_id")
    var product: Product = product
        protected set

    companion object {
        fun create(brand: Brand, category: Category, product: Product): BrandCategoryProduct {
            return BrandCategoryProduct(brand, category, product)
        }
    }
}