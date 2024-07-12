package com.mss.domain.exception

enum class ErrorCode(val code: String, val message: String) {
    INVALID_JSON("INVALID_JSON", "잘못된 요청입니다"),
    NOT_FOUND_CATEGORY("NOT_FOUND_CATEGORY", "카테고리를 찾을 수가 없습니다"),
    NOT_FOUND_PRODUCT("NOT_FOUND_PRODUCT", "상품을 찾을 수가 없습니다"),
    NOT_FOUND_BRAND("NOT_FOUND_BRAND", "브랜드를 찾을 수가 없습니다"),
}