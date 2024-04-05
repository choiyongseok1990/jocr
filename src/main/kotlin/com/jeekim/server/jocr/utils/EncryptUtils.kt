package com.jeekim.server.jocr.utils

import com.jeekim.server.jocr.exception.ErrorCode
import java.util.Base64
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec


object EncryptUtils {
    private const val alg = "AES/CBC/PKCS5Padding"
    private const val key = "LqnNqzbTWgsQQaenUyt8JI9qPJbmWvxj"
    private val iv = key.substring(0, 16)
    fun encrypt(text: String): String {
        val cipher = Cipher.getInstance(alg)
        val keySpec = SecretKeySpec(key.toByteArray(), "AES")
        val ivParamSpec = IvParameterSpec(iv.toByteArray())
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivParamSpec)
        val encrypted = cipher.doFinal(text.toByteArray(charset("UTF-8")))
        return Base64.getEncoder().encodeToString(encrypted)
    }
}

