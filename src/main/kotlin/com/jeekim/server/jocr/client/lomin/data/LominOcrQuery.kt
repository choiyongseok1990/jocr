package com.jeekim.server.jocr.client.lomin.data

import com.fasterxml.jackson.annotation.JsonProperty
import com.jeekim.server.jocr.dto.ocr.KeyValue
import com.jeekim.server.jocr.dto.ocr.Table

class LominOcrQuery {
    data class IN(
        @JsonProperty("document")
        val document: Document,
        @JsonProperty("rectify")
        val rectify: Rectify = Rectify(),
        @JsonProperty("hint")
        val hint: Hint = Hint(),
        @JsonProperty("detection_score_threshold")
        val detectionScoreThreshold: Double = 0.5,
        @JsonProperty("detection_resize_ratio")
        val detectionResizeRatio: Double = 1.0,
        @JsonProperty("idcard_version")
        val idcardVersion: String = "v1",
        @JsonProperty("page")
        val page: Int = 1,
    ){
        data class Document(
            @JsonProperty("file")
            val file: String,
            @JsonProperty("file_path")
            val filePath: String
        )

        data class Rectify(
            @JsonProperty("rotation_90n")
            val rotation90n: Boolean = true,
            @JsonProperty("rotation_fine")
            val rotationFine: Boolean = true,
        )

        data class Hint(
            @JsonProperty("doc_type")
            val docType: DocType = DocType(),
            @JsonProperty("key_value")
            val keyValue: List<Any> = listOf()
        ){
            data class DocType(
                @JsonProperty("use")
                val use: Boolean = true,
                @JsonProperty("trust")
                val trust: Boolean = true,
                @JsonProperty("doc_type")
                val docType: String = "MD-PRS"
            )
        }
    }

    data class OUT(
        @JsonProperty("prediction")
        val prediction: Prediction
    ){
        data class Prediction(
            @JsonProperty("key_values") val keyValues: List<KeyValue>,
            @JsonProperty("tables") val tables: List<Table>
        )
    }


    companion object {
        fun of(file: String, filePath: String): IN {
            return IN(
                document = IN.Document(file, filePath)
            )
        }
    }
}