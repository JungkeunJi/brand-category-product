package com.mss.domain.repository

import com.mss.domain.Brand
import org.springframework.data.jpa.repository.JpaRepository

interface BrandRepository : JpaRepository<Brand, Long>