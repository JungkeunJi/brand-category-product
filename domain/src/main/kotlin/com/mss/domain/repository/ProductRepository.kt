package com.mss.domain.repository

import com.mss.domain.Product
import org.springframework.data.jpa.repository.JpaRepository

interface ProductRepository : JpaRepository<Product, Long> {
    // todo. entity graph 사용하고 brand, category 소프트딜리트 조건 포함 잘되는지 확인
}