package com.mss.domain.exception

open class CustomException(
    open val data: String?,
    open val errorCode: ErrorCode,
    override val cause: Throwable?
) : RuntimeException()