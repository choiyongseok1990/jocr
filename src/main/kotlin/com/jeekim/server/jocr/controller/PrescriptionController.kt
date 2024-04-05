package com.jeekim.server.jocr.controller

import com.jeekim.server.jocr.dto.prescription.PrescriptionRequest
import com.jeekim.server.jocr.domain.enums.ServiceType
import com.jeekim.server.jocr.dto.drug.DrugInfoRequest
import com.jeekim.server.jocr.dto.drug.DrugInfoResponse
import com.jeekim.server.jocr.dto.prescription.UsageCount
import com.jeekim.server.jocr.exception.ErrorResponse
import com.jeekim.server.jocr.service.DrugService
import com.jeekim.server.jocr.service.PrescriptionFacade
import com.jeekim.server.jocr.service.PrescriptionManageService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestAttribute
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid


/**
 * 처방전 전송/보관/조회
 */
@RestController
@RequestMapping("/prescription/")
@Tag(name = "처방전 전송/보관/조회")
class PrescriptionController(
    private val prescriptionFacade: PrescriptionFacade,
    private val prescriptionManageService: PrescriptionManageService,
    private val drugService: DrugService
) {

//    @PostMapping("/gateway")
//    fun gateway(
//        @RequestBody request: PrescriptionRequest,
//        @RequestAttribute userId: String,
//        @RequestAttribute type: ServiceType
//    ) {
//        /**
//         * 1. 마스킹된 문자열을 제외
//         * 2. 약품코드, 약품명 없는 약품을 제외
//         */
//        request.clear()
//        return prescriptionFacade.sendToGateway(request, userId, type)
//    }

    @PostMapping("/kims")
    @Operation(summary = "검증된 OCR 인식 결과 전송(KIMS)")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "OK"),
            ApiResponse(responseCode = "400", description = "[300001] 입력값이 올바르지 않습니다.", content = [Content(schema = Schema(implementation = ErrorResponse::class))]),
            ApiResponse(responseCode = "500", description = "[200003] 킴스 API 호출 에러", content = [Content(schema = Schema(implementation = ErrorResponse::class))])
        ]
    )
    fun sendToKIMS(
        @RequestBody @Valid request: PrescriptionRequest,
        @RequestAttribute id: String,
        @RequestAttribute service: ServiceType
    ){
        request.clear()
        return prescriptionFacade.sendToKims(request, id, service)
    }

    @ApiResponses(
        value = [ApiResponse(responseCode = "200", description = "OK"), ]
    )
    @Operation(summary = "약품코드로 약품 검색")
    @GetMapping("/drugCode/{drugCode}")
    fun getDrugInfoByDrugCode(@PathVariable drugCode: String): List<DrugInfoResponse> {
        return drugService.getDrugsByDrugCode(drugCode).map { DrugInfoResponse.of(it) }
    }

    @ApiResponses(
        value = [ApiResponse(responseCode = "200", description = "OK"), ]
    )
    @Operation(summary = "약품명으로 약품 검색")
    @PostMapping("/drugName")
    fun getDrugInfoByDrugName(@RequestBody request: DrugInfoRequest): List<DrugInfoResponse> {
        return drugService.getDrugsByDrugName(request.drugName).map { DrugInfoResponse.of(it) }
    }
}