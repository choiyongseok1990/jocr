package com.jeekim.server.jocr.domain.converter

import com.jeekim.server.jocr.domain.model.PrescriptionContent
import com.jeekim.server.jocr.utils.convertTo
import com.jeekim.server.jocr.utils.createJsonNode
import com.jeekim.server.jocr.utils.toJsonNode
import javax.persistence.AttributeConverter
import javax.persistence.Converter


@Converter
class PrescriptionContentConverter : AttributeConverter<List<PrescriptionContent>, String> {
    override fun convertToDatabaseColumn(json: List<PrescriptionContent>): String {
        return json.toJsonNode().toString()
    }

    override fun convertToEntityAttribute(s: String): List<PrescriptionContent> {
        return s.createJsonNode().map {
            it.convertTo(PrescriptionContent::class.java)
        }
    }
}

