package com.jeekim.server.jocr.filter

import com.fasterxml.jackson.databind.ObjectMapper
import com.jeekim.server.jocr.exception.AuthException
import com.jeekim.server.jocr.exception.ErrorCode
import com.jeekim.server.jocr.exception.ErrorResponse
import com.jeekim.server.jocr.utils.logger
import org.springframework.core.annotation.Order
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
@Order(2)
class AuthorizationFilter(
    private val filterFactory: FilterFactory
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {

        val service = request.getHeader("service")
        val path = request.servletPath
        try {
            val abstractFilter = filterFactory.getFilter(service, path)
            abstractFilter?.handleRequest(request, response, filterChain)
        } catch (exception: AuthException) {
            val attributes = RequestContextHolder.currentRequestAttributes() as? ServletRequestAttributes
            attributes?.response?.let {
                sendErrorResponse(it, exception.getErrorCode())
            }
        }
    }
    private fun sendErrorResponse(response: HttpServletResponse, errorCode: ErrorCode) {
        response.status = errorCode.httpStatus.value()
        response.contentType = MediaType.APPLICATION_JSON_VALUE

        val errorResponse = ErrorResponse(
            code = errorCode.code,
            message = errorCode.message,
            status = errorCode.httpStatus.value()
        )

        ObjectMapper().writeValue(response.outputStream, errorResponse)
    }
}
