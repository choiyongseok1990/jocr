package com.jeekim.server.jocr.filter

import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

interface AbstractFilter {
    @Throws(ServletException::class, IOException::class)
    fun handleRequest(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain)
}
