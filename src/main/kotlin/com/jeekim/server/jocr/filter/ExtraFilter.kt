package com.jeekim.server.jocr.filter

import com.jeekim.server.jocr.utils.logger
import org.springframework.stereotype.Component
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class ExtraFilter: AbstractFilter {
    override fun handleRequest(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        filterChain.doFilter(request, response)
    }
}