package com.jeekim.server.jocr.domain.converter

import com.jeekim.server.jocr.domain.enums.UsageStatus
import com.jeekim.server.jocr.domain.model.PrescriptionContent
import com.jeekim.server.jocr.utils.convertTo
import com.jeekim.server.jocr.utils.createJsonNode
import com.jeekim.server.jocr.utils.toJsonNode
import javax.persistence.AttributeConverter
import javax.persistence.Converter

@Converter
class UsageStatusConverter : AttributeConverter<UsageStatus, String> {
    override fun convertToDatabaseColumn(status: UsageStatus): String {
        return status.name
    }

    override fun convertToEntityAttribute(s: String): UsageStatus {
        return UsageStatus.valueOf(s)
    }
}