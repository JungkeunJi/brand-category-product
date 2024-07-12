package com.mss.application.model.param

data class BrandCreateParam(
    val name: String,
    val categoryProducts: List<CategoryProductParam>
)
