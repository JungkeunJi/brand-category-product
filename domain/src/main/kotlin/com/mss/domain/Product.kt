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
    var brand: Brand? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    var category: Category,

    @Column(name = "price", nullable = false)
    var price: Int
) : BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L

    @Column(name = "deleted_at")
    val deletedAt: LocalDateTime? = null

    fun updatePrice(price: Int) {
        this.price = price
    }

    companion object {
        fun create(category: Category, price: Int): Product {
            return Product(category = category, price = price)
        }
    }
}