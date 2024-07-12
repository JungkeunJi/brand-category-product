package com.mss.domain.exception

class InvalidRequestException(
    override val data: String? = "",
    override val errorCode: ErrorCode
) : CustomException(data, errorCode)