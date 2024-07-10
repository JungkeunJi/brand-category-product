package com.mss.domain

import com.mss.domain.shared.BaseEntity
import jakarta.persistence.*
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.SQLRestriction
import java.time.LocalDateTime

@Entity
@Table(name = "product")
@SQLRestriction("deleted_at is NULL")
@SQLDelete(sql = "UPDATE product SET deleted_at = NOW() WHERE id = ?")
class Product private constructor(
    @Column(name = "price", nullable = false)
    var price: Int
) : BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L

    @Column(name = "deleted_at")
    val deletedAt: LocalDateTime? = null

    companion object {
        fun create(price: Int): Product {
            return Product(price)
        }
    }
}