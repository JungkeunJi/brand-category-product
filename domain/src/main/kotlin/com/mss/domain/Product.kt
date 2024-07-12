package com.mss.domain

import com.mss.domain.shared.BaseEntity
import jakarta.persistence.*
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.SQLRestriction
import java.time.LocalDateTime

@Entity
@Table(
    name = "product", uniqueConstraints = [
        UniqueConstraint(name = "uk_brand_category", columnNames = ["brand_id", "category_id"])
    ]
)
@SQLRestriction("deleted_at is NULL")
@SQLDelete(sql = "UPDATE product SET deleted_at = NOW() WHERE id = ?")
class Product private constructor(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    val brand: Brand,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    val category: Category,

    @Column(name = "price", nullable = false)
    var price: Int
) : BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L

    @Column(name = "deleted_at")
    val deletedAt: LocalDateTime? = null

    companion object {
        fun create(brand: Brand, category: Category, price: Int): Product {
            return Product(brand, category, price)
        }
    }
}