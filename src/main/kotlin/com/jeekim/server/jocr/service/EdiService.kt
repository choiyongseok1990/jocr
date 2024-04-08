package com.jeekim.server.jocr.service

import com.jeekim.server.jocr.client.infotech.InfoTechAdapter
import com.jeekim.server.jocr.client.infotech.dto.EdiRequest
import com.jeekim.server.jocr.dto.operation.CredentialCommand
<<<<<<< Updated upstream
import com.jeekim.server.jocr.dto.operation.EdiResult
=======
>>>>>>> Stashed changes
import com.jeekim.server.jocr.dto.operation.EdiType
import org.springframework.stereotype.Service
import java.time.YearMonth

@Service
class EdiService(
    val infoTechAdapter: InfoTechAdapter
){
<<<<<<< Updated upstream
    fun process(command: CredentialCommand, yearMonth: YearMonth) {
        val healthInsurance = infoTechAdapter.fetchHealthInsurance(EdiRequest.of(command, EdiType.HEALTH_INSURANCE, yearMonth))
        val nationalPension = infoTechAdapter.fetchNationalPension(EdiRequest.of(command, EdiType.NATIONAL_PENSION, yearMonth))
        val employeeInsurance = infoTechAdapter.fetchEmployeeInsurance(EdiRequest.of(command, EdiType.EMPLOYEE_INSURANCE, yearMonth))
        val healthInsuranceMap = healthInsurance.outE0001?.getEmployeeMap() ?: emptyMap()
        val nationalPensionMap = nationalPension.outE0002?.getEmployeeMap() ?: emptyMap()
        val employeeInsuranceMap = employeeInsurance.outE0003?.getEmployeeMap() ?: emptyMap()
        EdiResult.of(
            command.crewId,
            command.pharmacyName,
            yearMonth,
            healthInsuranceMap,
            nationalPensionMap,
            employeeInsuranceMap
        )
        // DB 업데이트 send (여러 월)
=======
    fun fetch(command: CredentialCommand, yearMonth: YearMonth){
        val healthInsurance = infoTechAdapter.fetchHealthInsurance(EdiRequest.of(command, EdiType.HEALTH_INSURANCE, yearMonth))
        val nationalPension = infoTechAdapter.fetchNationalPension(EdiRequest.of(command, EdiType.NATIONAL_PENSION, yearMonth))
        val employeeInsurance = infoTechAdapter.fetchEmployeeInsurance(EdiRequest.of(command, EdiType.EMPLOYEE_INSURANCE, yearMonth))
>>>>>>> Stashed changes

    }
}