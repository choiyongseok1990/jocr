package com.jeekim.server.jocr.dto.operation

import com.jeekim.server.jocr.client.infotech.dto.EdiResponse
import com.jeekim.server.jocr.utils.toDate
import java.time.LocalDate
import java.time.YearMonth

data class EdiResult(
    val crewId: Long,
    val yearMonth: YearMonth,
    val employees: List<EmployeeEdi>,
){
    data class EmployeeEdi(
        val crewId: Long,
        val employeeId: Long? = null,
        val employeeName: String,
        val pharmacyName: String,
        val employeeRrnNumber: String,
        val workStartDate : LocalDate?,
        val employeeSalary: Long =0, // 근로자 보수월액
        val employeeHealthInsurance: Long =0, // 건강보험료
        val companyHealthInsurance: Long =0, // 건강보험료
        val employeeNursingInsurance: Long =0, // 요양보험료
        val companyNursingInsurance: Long =0, // 요양보험료
        val employeeNationalPension: Long =0, // 국민연금
        val companyNationalPension: Long =0, // 국민연금
        val employeeEmploymentInsurance: Long =0, // 고용보험
        val companyEmploymentInsurance: Long =0, // 고용보험
        val companyIndustrialExpense: Long =0, // 산재
    )
    companion object{
        fun of(
            crewId: Long,
            pharmacyName: String,
            yearMonth: YearMonth,
            healthInsuranceMap : Map<String, EdiResponse.HealthInsurance>,
            nationalPensionMap : Map<String, EdiResponse.NationalPension>,
            employmentInsuranceMap : Map<String, EdiResponse.EmployeeInsurance>
        ): EdiResult{
            val ceoName = employmentInsuranceMap.keys.first()
            val employeeNames = (healthInsuranceMap.keys + nationalPensionMap.keys + employmentInsuranceMap.keys)
                .filterNot { it == ceoName }.toSet()

            return EdiResult(
                crewId = crewId,
                yearMonth = yearMonth,
                employees = employeeNames.map { name ->
                    val healthInsurance = healthInsuranceMap[name]
                    val nationalPension = nationalPensionMap[name]
                    val employmentInsurance = employmentInsuranceMap[name]
                    EmployeeEdi(
                        crewId = crewId,
                        employeeName = name,
                        pharmacyName = pharmacyName,
                        employeeRrnNumber = healthInsurance?.juminNo ?: "",
                        workStartDate = employmentInsurance?.getWorkStartDate()?.toDate(),
                        employeeSalary = listOf(
                            healthInsurance?.getSalary() ?: 0,
                            nationalPension?.getSalary() ?: 0,
                            employmentInsurance?.getSalary() ?: 0
                        ).max(),
                        employeeHealthInsurance = healthInsurance?.getHealthInsurance() ?: 0,
                        companyHealthInsurance = healthInsurance?.getHealthInsurance() ?: 0,
                        employeeNursingInsurance = healthInsurance?.getNursingInsurance() ?: 0,
                        companyNursingInsurance = healthInsurance?.getNursingInsurance() ?: 0,
                        employeeNationalPension = nationalPension?.getNationalPension() ?: 0,
                        companyNationalPension = nationalPension?.getNationalPension() ?: 0,
                        employeeEmploymentInsurance = employmentInsurance?.getEmploymentInsurance() ?: 0,
                        companyEmploymentInsurance = employmentInsurance?.getCompanyEmploymentInsurance() ?: 0,
                        companyIndustrialExpense = employmentInsurance?.getCompanyIndustrialExpense() ?: 0
                    )
                }
            )
        }
    }
}