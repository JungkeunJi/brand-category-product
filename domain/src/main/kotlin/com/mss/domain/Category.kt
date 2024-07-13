package com.mss.domain

import com.mss.domain.shared.BaseEntity
import jakarta.persistence.*
import org.hibernate.annotations.BatchSize
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.SQLRestriction
import java.time.LocalDateTime

@Entity
@Table(
    name = "category", uniqueConstraints = [
        UniqueConstraint(
            name = "uk_category_name",
            columnNames = ["name"]
        )
    ]
)
@SQLRestriction("deleted_at is NULL")
@SQLDelete(sql = "UPDATE category SET deleted_at = NOW() WHERE id = ?")
class Category private constructor(
    @Column(name = "name", nullable = false)
    var name: String
) : BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L

    @Column(name = "deleted_at")
    val deletedAt: LocalDateTime? = null

    @OneToMany(mappedBy = "category", cascade = [CascadeType.PERSIST, CascadeType.REMOVE], orphanRemoval = true)
    @BatchSize(size = 10)
    val products: MutableList<Product> = mutableListOf()

    fun addProduct(product: Product) {
        product.category = this
        products.add(product)
    }

    companion object {
        fun create(name: String): Category {
            return Category(name)
        }
    }
}