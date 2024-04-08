package com.jeekim.server.jocr.service

import com.jeekim.server.jocr.client.infotech.InfoTechAdapter
<<<<<<< Updated upstream
import com.jeekim.server.jocr.client.infotech.dto.MedicareRequest
import com.jeekim.server.jocr.dto.operation.CredentialCommand
import com.jeekim.server.jocr.dto.operation.TaxationResult
=======
import com.jeekim.server.jocr.client.infotech.dto.Disabled
import com.jeekim.server.jocr.client.infotech.dto.Medical
import com.jeekim.server.jocr.client.infotech.dto.MedicareRequest
import com.jeekim.server.jocr.client.infotech.dto.MedicareResponse
import com.jeekim.server.jocr.client.infotech.dto.Nursing
import com.jeekim.server.jocr.client.infotech.dto.Smoking
import com.jeekim.server.jocr.client.infotech.dto.Welfare
import com.jeekim.server.jocr.dto.operation.CredentialCommand
import com.jeekim.server.jocr.dto.operation.MedicareResult
>>>>>>> Stashed changes
import com.jeekim.server.jocr.dto.operation.MedicareType
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.YearMonth

@Service
class MedicareService(
    val infoTechAdapter: InfoTechAdapter
) {
<<<<<<< Updated upstream
    fun fetch(command: CredentialCommand, yearMonth: YearMonth): Map<LocalDate, List<TaxationResult.TaxationDaily>>{
=======
    fun fetch(command: CredentialCommand, yearMonth: YearMonth): Map<LocalDate, MedicareResult.MedicareDaily>{
>>>>>>> Stashed changes
        // Medicare fetching -> 순차적 으로
        val nursingResult = infoTechAdapter.fetchNursingExpenses(MedicareRequest.of(command, MedicareType.NURSING, yearMonth))
        val medicalResult = infoTechAdapter.fetchMedicalExpenses(MedicareRequest.of(command, MedicareType.MEDICAL,yearMonth))
        val disabledResult = infoTechAdapter.fetchDisabledExpenses(MedicareRequest.of(command, MedicareType.DISABLED,yearMonth))
        val smokingResult = infoTechAdapter.fetchSmokingExpenses(MedicareRequest.of(command, MedicareType.SMOKING,yearMonth))
        val welfareResult = infoTechAdapter.fetchWelfareExpenses(MedicareRequest.of(command, MedicareType.WELFARE,yearMonth))
        val nursingMap = nursingResult.outF0011?.getDailyMap() ?: emptyMap()
        val medicalMap = medicalResult.outF0012?.getDailyMap() ?: emptyMap()
        val disabledMap = disabledResult.outF0013?.getDailyMap() ?: emptyMap()
        val smokingMap = smokingResult.outF0014?.getDailyMap() ?: emptyMap()
        val welfareMap = welfareResult.outF0015?.getDailyMap() ?: emptyMap()
<<<<<<< Updated upstream
        return TaxationResult.ofMedicare(
            command.pharmacyName,
            command.businessNumber,
=======
        return MedicareResult.of(
>>>>>>> Stashed changes
            nursingMap,
            medicalMap,
            disabledMap,
            smokingMap,
            welfareMap
        ).data
    }
}