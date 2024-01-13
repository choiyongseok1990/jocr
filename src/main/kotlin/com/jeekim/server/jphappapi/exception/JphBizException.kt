package com.jeekim.server.jphappapi.exception

class JphBizException(
    val errorCode: ErrorCode
) : RuntimeException()