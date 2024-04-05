package com.jeekim.server.jocr.filter

import com.jeekim.server.jocr.exception.AuthException
import com.jeekim.server.jocr.exception.ErrorCode
import com.jeekim.server.jocr.exception.JocrException
import com.jeekim.server.jocr.utils.Hospital.HOSPITAL_MAP
import com.jeekim.server.jocr.utils.logger
import org.springframework.stereotype.Component
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JphFilter: AbstractFilter {
    override fun handleRequest(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        logger().info("jph-path: {}", request.servletPath)
        val hospitalId = request.getHeader("hospital-key")
        if(hospitalId.isNullOrEmpty()) {
            throw AuthException(ErrorCode.HEADER_NOT_FOUND)
        }
        HOSPITAL_MAP[hospitalId] ?: throw AuthException(ErrorCode.HOSPITAL_NOT_FOUND)
        request.setAttribute("id", hospitalId)
        request.setAttribute("service", "JPH")
        filterChain.doFilter(request, response)
    }
}