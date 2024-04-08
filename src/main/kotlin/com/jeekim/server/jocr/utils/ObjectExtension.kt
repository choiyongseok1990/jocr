package com.jeekim.server.jocr.utils


import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.databind.node.JsonNodeFactory
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale
import java.util.TimeZone


fun String.toDate(): LocalDate = LocalDate.parse(this, CommonUtils.NO_DASH_FORMAT)
fun String.toLongOrZero(): Long = this.toLongOrNull() ?: 0
fun Any.toJsonNode(): JsonNode {
    return DEFAULT_MAPPER.valueToTree(this)
}

fun <T> T?.toJsonNodeWithDefault(): JsonNode = DEFAULT_MAPPER.valueToTree(this) ?: JsonNodeFactory.instance.objectNode()

fun <T> JsonNode.convertTo(responseClass: Class<T>): T {
    return DEFAULT_MAPPER.treeToValue(this, responseClass)
}
fun <T> Any.convertTo(responseClass: Class<T>): T {
    return DEFAULT_MAPPER.convertValue(this, responseClass)
}

fun <T> String.convertTo(responseClass: Class<T>): T {
    return DEFAULT_MAPPER.readValue(this, responseClass)
}

fun <T> String.convertTo(clazz: TypeReference<T>): T {
    return DEFAULT_MAPPER.readValue(this, clazz)
}

fun String.createJsonNode(): JsonNode {
    return DEFAULT_MAPPER.readTree(this)
}

fun copyDefaultObjectMapper(): ObjectMapper = DEFAULT_MAPPER.copy()

fun LocalDate.toNoDash(): String = this.format(DateTimeFormatter.ofPattern("yyyyMMdd"))

fun LocalDate.toDash(): String = this.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))

/**
 * Default ObjectMapper
 * - Hibernate, Spring 등 Application 전역에서 사용하는 ObjectMapper의 베이스 설정 인스턴스
 * - 각 사용처마다 `objectMapper.copy()`를 호출하여 베이스 설정을 그대로 사용하며 개별 설정이 필요한 경우 `copy()`메소드 호출 후 사용처에서 설정
 */
private val DEFAULT_MAPPER: ObjectMapper =
    Jackson2ObjectMapperBuilder.json().featuresToEnable(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL)
        .featuresToEnable(
            DeserializationFeature.USE_LONG_FOR_INTS
        )
        .featuresToDisable(
            SerializationFeature.WRITE_DATES_AS_TIMESTAMPS,
            SerializationFeature.FAIL_ON_EMPTY_BEANS,
            DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
            DeserializationFeature.FAIL_ON_MISSING_EXTERNAL_TYPE_ID_PROPERTY,
        )
        .timeZone(TimeZone.getDefault())
        .modulesToInstall(
            JavaTimeModule(),
        )
        .locale(Locale.getDefault())
        .simpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
        .build<ObjectMapper>().apply {
            setSerializationInclusion(JsonInclude.Include.NON_NULL)
            setVisibility(
                this.serializationConfig.defaultVisibilityChecker
                    .withFieldVisibility(JsonAutoDetect.Visibility.ANY)
                    .withGetterVisibility(JsonAutoDetect.Visibility.NONE)
                    .withSetterVisibility(JsonAutoDetect.Visibility.NONE)
                    .withCreatorVisibility(JsonAutoDetect.Visibility.NONE)
                    .withIsGetterVisibility(JsonAutoDetect.Visibility.NONE)
            )
            setVisibility(
                this.deserializationConfig.defaultVisibilityChecker
                    .withFieldVisibility(JsonAutoDetect.Visibility.ANY)
                    .withGetterVisibility(JsonAutoDetect.Visibility.NONE)
                    .withSetterVisibility(JsonAutoDetect.Visibility.NONE)
                    .withCreatorVisibility(JsonAutoDetect.Visibility.NONE)
                    .withIsGetterVisibility(JsonAutoDetect.Visibility.NONE)
            )
        }