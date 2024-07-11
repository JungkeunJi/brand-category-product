package com.mss.config

import com.mss.domain.exception.CustomException
import com.mss.domain.exception.ErrorCode.*
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ExceptionHandlerAdvice {
    @ExceptionHandler(CustomException::class)
    fun customExceptionHandler(ex: CustomException): ResponseEntity<Response.Fail> {
        val errorCode = ex.errorCode
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(Response.Fail.create(errorCode.code, ex.javaClass.simpleName, errorCode.message))
    }

    //요청에 대한 json 파싱 문제
    @ExceptionHandler(HttpMessageNotReadableException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun httpMessageNotReadableExceptionHandler(ex: HttpMessageNotReadableException): Response.Fail {
        return Response.Fail.create(INVALID_JSON.code, ex.javaClass.simpleName, INVALID_JSON.message)
    }
}