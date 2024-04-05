package com.jeekim.server.jocr.controller

import com.jeekim.server.jocr.exception.ErrorCode
import com.jeekim.server.jocr.exception.ErrorResponse
import com.jeekim.server.jocr.exception.JocrException
import com.jeekim.server.jocr.utils.logger
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import javax.validation.ConstraintViolationException

@RestControllerAdvice
class ControllerAdvice {
    @ExceptionHandler(JocrException::class)
    fun handleExistingCrewException(jocrException: JocrException): ResponseEntity<Any> {
        logger().info("handleExistingCrewException: ${jocrException.getErrorCode().code}")
        return createResponseEntity(jocrException.getErrorCode())
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidMethodException(exception: MethodArgumentNotValidException): ResponseEntity<Any> {
        val errorCode: ErrorCode = ErrorCode.INPUT_NOT_VALID
        val response: ErrorResponse = ErrorResponse.ofUserMessage(
            errorCode,
            exception.bindingResult.fieldError?.defaultMessage ?: errorCode.message
        )
        return ResponseEntity
            .status(errorCode.httpStatus)
            .body<Any>(response)
    }

    @ExceptionHandler(ConstraintViolationException::class)
    fun handleValidException(exception: ConstraintViolationException): ResponseEntity<Any> {
        val errorCode = ErrorCode.INPUT_NOT_VALID
        val response: ErrorResponse = ErrorResponse.ofUserMessage(
            errorCode,
            exception.message ?: errorCode.message
        )
        return ResponseEntity
            .status(errorCode.httpStatus)
            .body<Any>(response)
    }
    @ExceptionHandler(Exception::class)
    fun handleBadRequestException(exception: Exception): ResponseEntity<Any> {
        val errorCode = ErrorCode.UNEXPECTED
        val response: ErrorResponse = ErrorResponse.ofUserMessage(
            errorCode,
            exception.message ?: errorCode.message
        )
        return ResponseEntity
            .status(errorCode.httpStatus)
            .body<Any>(response)
    }

    private fun createResponseEntity(errorCode: ErrorCode): ResponseEntity<Any> {
        return ResponseEntity
            .status(errorCode.httpStatus)
            .body(ErrorResponse(errorCode))
    }
}