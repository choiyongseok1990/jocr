package com.jeekim.server.jocr.domain.converter

import com.jeekim.server.jocr.domain.enums.ServiceType
import com.jeekim.server.jocr.domain.enums.UsageStatus
import com.jeekim.server.jocr.domain.model.PrescriptionContent
import com.jeekim.server.jocr.utils.convertTo
import com.jeekim.server.jocr.utils.createJsonNode
import com.jeekim.server.jocr.utils.toJsonNode
import javax.persistence.AttributeConverter
import javax.persistence.Converter

@Converter
class ServiceTypeConverter : AttributeConverter<ServiceType, String> {
    override fun convertToDatabaseColumn(type: ServiceType): String {
        return type.name
    }

    override fun convertToEntityAttribute(s: String): ServiceType {
        return ServiceType.valueOf(s)
    }
}