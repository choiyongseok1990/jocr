package com.jeekim.server.jocr.dto.ocr

data class OcrByBase64Request (
    val extension: String,
    val encodedPrescription: String
)