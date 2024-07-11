package com.mss.application.param

data class BrandCreateParam(
    val name: String,
    val categoryProducts: List<CategoryProductParam>
)
