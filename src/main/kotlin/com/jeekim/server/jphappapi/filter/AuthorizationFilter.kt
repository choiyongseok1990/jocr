package com.jeekim.server.jphappapi.filter

import com.fasterxml.jackson.databind.ObjectMapper
import com.jeekim.server.jphappapi.exception.ErrorCode
import com.jeekim.server.jphappapi.service.HospitalService
import jakarta.security.auth.message.AuthException
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.ErrorResponse
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import org.springframework.web.filter.OncePerRequestFilter
@Component
class AuthorizationFilter(
    private val hospitalService: HospitalService
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val path = request.servletPath
        if(path.startsWith("/check")) {
            hospitalService.check("test", 1)
            filterChain.doFilter(request, response)
            return
        }
        filterChain.doFilter(request, response)
    }
}