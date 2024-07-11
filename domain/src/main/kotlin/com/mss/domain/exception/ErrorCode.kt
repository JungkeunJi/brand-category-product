package com.mss.domain.exception

enum class ErrorCode(val code: String, val message: String) {
    INVALID_JSON("INVALID_JSON", "잘못된 요청입니다"),
}