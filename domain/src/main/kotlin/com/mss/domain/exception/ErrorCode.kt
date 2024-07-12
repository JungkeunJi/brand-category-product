package com.mss.domain.exception

enum class ErrorCode(val code: String, val message: String) {
    INVALID_JSON("INVALID_JSON", "잘못된 요청입니다"),
    INVALID_CATEGORY_NAME_DUPLICATION("INVALID_CATEGORY_NAME_DUPLICATION", "중복된 카테고리 이름 요청값이 존재합니다"),
    INVALID_CATEGORY_NAME_MISSING("INVALID_CATEGORY_NAME_MISSING", "누락된 카테고리 이름이 존재합니다"),
    INVALID_CATEGORY_NAME_NOT_EXISTING("INVALID_CATEGORY_NAME_NOT_EXISTING", "존재하지 않는 카테고리 이름 요청이 존재합니다"),
    INVALID_BRAND_ALREADY_EXISTING("INVALID_BRAND_ALREADY_EXISTING", "이미 존재하는 브랜드입니다"),
    NOT_FOUND_CATEGORY("NOT_FOUND_CATEGORY", "카테고리를 찾을 수가 없습니다"),
    NOT_FOUND_PRODUCT("NOT_FOUND_PRODUCT", "상품을 찾을 수가 없습니다"),
    NOT_FOUND_BRAND("NOT_FOUND_BRAND", "브랜드를 찾을 수가 없습니다"),
}