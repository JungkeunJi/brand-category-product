package com.mss.application

import com.mss.domain.Brand
import com.mss.domain.repository.BrandRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class BrandProductQuery(
    private val brandRepository: BrandRepository
) {
    fun getAll(): List<Brand> {
        return brandRepository.findAll()
    }
}