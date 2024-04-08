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
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@FeignClient(
    name = "info-tech-client",
    url = "\${info-tech.host}",
    configuration = [InfoTechClientConfiguration::class]
)
interface InfoTechClient {
    @PostMapping
<<<<<<< Updated upstream
    fun cardSales(@RequestBody request: TaxRequest): TaxResponse<TaxResponse.CardSales>
    @PostMapping
    fun cashSales(@RequestBody request: TaxRequest): TaxResponse<TaxResponse.CashSales>
    @PostMapping
    fun eTaxPurchase(@RequestBody request: TaxRequest): TaxResponse<TaxResponse.ETaxPurchase>
    @PostMapping
    fun cardPurchase(@RequestBody request: TaxRequest): TaxResponse<TaxResponse.CardPurchase>
    @PostMapping
    fun cashPurchase(@RequestBody request: TaxRequest): TaxResponse<TaxResponse.CashPurchase>

    @PostMapping
    fun healthInsurance(@RequestBody request: EdiRequest): EdiResponse<EdiResponse.HealthInsurance>

    @PostMapping
    fun nationalPension(@RequestBody request: EdiRequest): EdiResponse<EdiResponse.NationalPension>

    @PostMapping
    fun employeeInsurance(@RequestBody request: EdiRequest): EdiResponse<EdiResponse.EmployeeInsurance>
=======
    fun cardSales()
    @PostMapping
    fun cashSales()
    @PostMapping
    fun eTaxPurchase()

    @PostMapping
    fun cardPurchase()

    @PostMapping
    fun cashPurchase()

    @PostMapping
    fun healthInsurance(@RequestBody request: EdiRequest): EdiResponse<HealthInsurance>

    @PostMapping
    fun nationalPension(@RequestBody request: EdiRequest): EdiResponse<NationalPension>

    @PostMapping
    fun employeeInsurance(@RequestBody request: EdiRequest): EdiResponse<EmployeeInsurance>
>>>>>>> Stashed changes

    @PostMapping
    fun nursingExpense(
        @RequestBody request: MedicareRequest
<<<<<<< Updated upstream
    ): MedicareResponse<MedicareResponse.Nursing>
=======
    ): MedicareResponse<Nursing>
>>>>>>> Stashed changes

    @PostMapping
    fun medicalExpense(
        @RequestBody request: MedicareRequest
<<<<<<< Updated upstream
    ): MedicareResponse<MedicareResponse.Medical>
=======
    ): MedicareResponse<Medical>
>>>>>>> Stashed changes

    @PostMapping
    fun disabledExpense(
        @RequestBody request: MedicareRequest
<<<<<<< Updated upstream
    ): MedicareResponse<MedicareResponse.Disabled>
    @PostMapping
    fun smokingExpense(
        @RequestBody request: MedicareRequest
    ): MedicareResponse<MedicareResponse.Smoking>
=======
    ): MedicareResponse<Disabled>
    @PostMapping
    fun smokingExpense(
        @RequestBody request: MedicareRequest
    ): MedicareResponse<Smoking>
>>>>>>> Stashed changes

    @PostMapping
    fun welfareExpense(
        @RequestBody request: MedicareRequest
<<<<<<< Updated upstream
    ): MedicareResponse<MedicareResponse.Welfare>
=======
    ): MedicareResponse<Welfare>
>>>>>>> Stashed changes

}