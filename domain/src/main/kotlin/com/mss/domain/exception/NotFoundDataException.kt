package com.mss.domain.exception

class NotFoundDataException(
    override val data: String? = "",
    override val errorCode: ErrorCode
) : CustomException(data, errorCode)