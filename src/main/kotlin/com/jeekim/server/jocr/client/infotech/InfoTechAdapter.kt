package com.jeekim.server.jocr.client.infotech

<<<<<<< Updated upstream
import com.jeekim.server.jocr.client.infotech.dto.EdiRequest
import com.jeekim.server.jocr.client.infotech.dto.EdiResponse
import com.jeekim.server.jocr.client.infotech.dto.MedicareRequest
import com.jeekim.server.jocr.client.infotech.dto.MedicareResponse
import com.jeekim.server.jocr.client.infotech.dto.TaxRequest
import com.jeekim.server.jocr.client.infotech.dto.TaxResponse
=======
import com.jeekim.server.jocr.client.infotech.dto.Disabled
import com.jeekim.server.jocr.client.infotech.dto.EdiRequest
import com.jeekim.server.jocr.client.infotech.dto.EdiResponse
import com.jeekim.server.jocr.client.infotech.dto.EmployeeInsurance
import com.jeekim.server.jocr.client.infotech.dto.HealthInsurance
import com.jeekim.server.jocr.client.infotech.dto.Medical
import com.jeekim.server.jocr.client.infotech.dto.MedicareRequest
import com.jeekim.server.jocr.client.infotech.dto.MedicareResponse
import com.jeekim.server.jocr.client.infotech.dto.NationalPension
import com.jeekim.server.jocr.client.infotech.dto.Nursing
import com.jeekim.server.jocr.client.infotech.dto.Smoking
import com.jeekim.server.jocr.client.infotech.dto.Welfare
>>>>>>> Stashed changes
import org.springframework.stereotype.Component

@Component
class InfoTechAdapter(
    private val infoTechClient: InfoTechClient,
) {

<<<<<<< Updated upstream
    /**
     * Tax
     */

    fun fetchCardSales(request: TaxRequest): TaxResponse<TaxResponse.CardSales> {
        return infoTechClient.cardSales(request)
    }
    fun fetchCashSales(request: TaxRequest): TaxResponse<TaxResponse.CashSales> {
        return infoTechClient.cashSales(request)
    }
    fun fetchETaxPurchase(request: TaxRequest): TaxResponse<TaxResponse.ETaxPurchase> {
        return infoTechClient.eTaxPurchase(request)
    }
    fun fetchCardPurchase(request: TaxRequest): TaxResponse<TaxResponse.CardPurchase> {
        return infoTechClient.cardPurchase(request)
    }
    fun fetchCashPurchase(request: TaxRequest): TaxResponse<TaxResponse.CashPurchase> {
        return infoTechClient.cashPurchase(request)
    }

    /**
     * Medicare
     */

    fun fetchNursingExpenses(request: MedicareRequest): MedicareResponse<MedicareResponse.Nursing> {
        return  infoTechClient.nursingExpense(request)
    }
    fun fetchMedicalExpenses(request: MedicareRequest): MedicareResponse<MedicareResponse.Medical> {
        return  infoTechClient.medicalExpense(request)
    }
    fun fetchDisabledExpenses(request: MedicareRequest): MedicareResponse<MedicareResponse.Disabled> {
        return infoTechClient.disabledExpense(request)
    }
    fun fetchSmokingExpenses(request: MedicareRequest): MedicareResponse<MedicareResponse.Smoking> {
        return infoTechClient.smokingExpense(request)
    }
    fun fetchWelfareExpenses(request: MedicareRequest): MedicareResponse<MedicareResponse.Welfare> {
        return  infoTechClient.welfareExpense(request)
    }

    /**
     * EDI
     */

    fun fetchHealthInsurance(request: EdiRequest): EdiResponse<EdiResponse.HealthInsurance>{
        return infoTechClient.healthInsurance(request)
    }
    fun fetchNationalPension(request: EdiRequest): EdiResponse<EdiResponse.NationalPension>{
        return infoTechClient.nationalPension(request)
    }
    fun fetchEmployeeInsurance(request: EdiRequest): EdiResponse<EdiResponse.EmployeeInsurance>{
=======
    fun fetchNursingExpenses(request: MedicareRequest): MedicareResponse<Nursing> {
        return  infoTechClient.nursingExpense(request)
    }
    fun fetchMedicalExpenses(request: MedicareRequest): MedicareResponse<Medical> {
        return  infoTechClient.medicalExpense(request)
    }
    fun fetchDisabledExpenses(request: MedicareRequest): MedicareResponse<Disabled> {
        return infoTechClient.disabledExpense(request)
    }
    fun fetchSmokingExpenses(request: MedicareRequest): MedicareResponse<Smoking> {
        return infoTechClient.smokingExpense(request)
    }
    fun fetchWelfareExpenses(request: MedicareRequest): MedicareResponse<Welfare> {
        return  infoTechClient.welfareExpense(request)
    }

    fun fetchHealthInsurance(request: EdiRequest): EdiResponse<HealthInsurance>{
        return infoTechClient.healthInsurance(request)
    }
    fun fetchNationalPension(request: EdiRequest): EdiResponse<NationalPension>{
        return infoTechClient.nationalPension(request)
    }
    fun fetchEmployeeInsurance(request: EdiRequest): EdiResponse<EmployeeInsurance>{
>>>>>>> Stashed changes
        return infoTechClient.employeeInsurance(request)
    }
}