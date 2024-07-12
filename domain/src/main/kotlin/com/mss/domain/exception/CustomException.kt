package com.mss.domain.exception

abstract class CustomException(
    open val data: String?,
    open val errorCode: ErrorCode
) : RuntimeException()