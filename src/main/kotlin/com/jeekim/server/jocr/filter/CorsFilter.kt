package com.jeekim.server.jocr.filter

import org.slf4j.LoggerFactory
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
@Order(1)
class CorsFilter : OncePerRequestFilter() {
    companion object {
        private const val ORIGIN = "origin"
        private val logger = LoggerFactory.getLogger(CorsFilter::class.java)
    }

    @Throws(ServletException::class)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        response.setHeader("Access-Control-Allow-Origin", getOrigin(request))
        response.setHeader("Access-Control-Allow-Credentials", "true")
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS, DELETE, PUT, PATCH")
        response.setHeader("Access-Control-Max-Age", "3600")
        response.setHeader("Access-Control-Allow-Headers",
            "Origin, X-Requested-With, Content-Type, Accept, Authorization, hospital-key, service")

        if ("OPTIONS".equals(request.method, ignoreCase = true)) {
            response.status = HttpServletResponse.SC_OK
        } else {
            filterChain.doFilter(request, response)
        }
    }

    private fun getOrigin(request: HttpServletRequest): String? {
        val origin = request.getHeader(ORIGIN)
        return if (origin != null && isJeekimDomain(origin)) {
            origin
        } else null
    }

    private fun isJeekimDomain(origin: String): Boolean {
        return origin.contains("jeekim.co.kr") || origin.contains("jeekim.company") || origin.contains("localhost:3000") || origin.contains("jeekim-server.com")
    }
}
