package com.mss.config

sealed interface Response {
    data class Success<T>(val data: T) : Response {
        companion object {
            fun <T> of(data: T): Success<T> {
                return Success(data)
            }
        }
    }

    data class Fail(val error: Error) : Response {
        companion object {
            fun create(code: String, exceptionName: String, message: String): Fail {
                return Fail(Error(code, exceptionName, message))
            }
        }
    }
}

data class Error(
    val code: String,
    val name: String,
    val message: String? = ""
)