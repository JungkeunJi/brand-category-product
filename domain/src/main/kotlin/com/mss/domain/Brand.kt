package com.mss.domain

import com.mss.domain.shared.BaseEntity
import jakarta.persistence.*
import org.hibernate.annotations.BatchSize
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

    // todo. brand와 category 각각 one to many 로 product 연결하고, cascade 옵션 통해 delete 전파되게 하기.
    // todo. 또한 one to many 걸면 이제 양방향이므로, create와 update API에서 one to many에 데이터 넣고 persist 하는 로직 추가
    // todo. one to many 배치 사이즈 추가하고, 굳이 브랜드 조회하고 상품레포지토리에서 또 조회하지 말고 브랜드 엔티티에서 상품 리스트 가져오는 로직 다 바꾸기 카테고리 마찬가지
    // todo. 따라서 product repository는 확인후 삭제해도 될듯
    @OneToMany(mappedBy = "brand", cascade = [CascadeType.PERSIST, CascadeType.REMOVE], orphanRemoval = true)
    @BatchSize(size = 10)
    val products: MutableList<Product> = mutableListOf()

    fun addProduct(product: Product) {
        product.brand = this
        products.add(product)
    }

    companion object {
        fun create(name: String): Brand {
            return Brand(name)
        }
    }
}