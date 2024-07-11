package com.mss.domain

import com.mss.domain.shared.BaseEntity
import jakarta.persistence.*
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.SQLRestriction
import java.time.LocalDateTime

@Entity
@Table(
    name = "brand", uniqueConstraints = [
        UniqueConstraint(
            name = "uk_brand_name",
            columnNames = ["name"]
        )
    ]
)
@SQLRestriction("deleted_at is NULL")
@SQLDelete(sql = "UPDATE brand SET deleted_at = NOW() WHERE id = ?")
class Brand private constructor(
    @Column(name = "name", nullable = false)
    var name: String
) : BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L

    @Column(name = "deleted_at")
    val deletedAt: LocalDateTime? = null

    companion object {
        fun create(name: String): Brand {
            return Brand(name)
        }
    }
}