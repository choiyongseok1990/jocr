package com.jeekim.server.jocr.dto.operation

import com.jeekim.server.jocr.client.infotech.dto.TaxResponse
import java.time.LocalDate

data class TaxationResult (
    val data: Map<LocalDate, List<TaxationDaily>>
){
    data class TaxationDaily(
        val category: SalesPurchaseCategory,
        val details: List<TaxationDailyDetail>
    ){
        data class TaxationDailyDetail(
            val companyName: String,
            val bizNo: String,
            val amount: Long,
            val businessType: String?
        )
    }

    companion object{

        fun ofTax(
            cashSalesMap : Map<LocalDate, List<TaxResponse.TaxDetail>>,
            cashPurchaseMap : Map<LocalDate, List<TaxResponse.TaxDetail>>,
            eTaxPurchaseMap : Map<LocalDate, List<TaxResponse.TaxDetail>>,
            cardSalesMap : Map<LocalDate, List<TaxResponse.TaxDetail>>,
            cardPurchaseMap : Map<LocalDate, List<TaxResponse.TaxDetail>>
        ): TaxationResult{
            val keys = cashSalesMap.keys + cashPurchaseMap.keys + eTaxPurchaseMap.keys + cardSalesMap.keys + cardPurchaseMap.keys
            val data = keys.associateWith { date ->
                val cashSales = cashSalesMap[date]?.map { it.toDaily() }
                val cashPurchase = cashPurchaseMap[date]?.map { it.toDaily() }
                val eTaxPurchase = eTaxPurchaseMap[date]?.map { it.toDaily() }
                val cardSales = cardSalesMap[date]?.map { it.toDaily() }
                val cardPurchase = cardPurchaseMap[date]?.map { it.toDaily() }
                val cashSalesDaily = TaxationDaily(SalesPurchaseCategory.CASH_SALES, cashSales ?: emptyList())
                val cashPurchaseDaily = TaxationDaily(SalesPurchaseCategory.CASH_PURCHASE, cashPurchase ?: emptyList())
                val eTaxPurchaseDaily = TaxationDaily(SalesPurchaseCategory.E_TAX_PURCHASE, eTaxPurchase ?: emptyList())
                val cardSalesDaily = TaxationDaily(SalesPurchaseCategory.CARD_SALES, cardSales ?: emptyList())
                val cardPurchaseDaily = TaxationDaily(SalesPurchaseCategory.CARD_PURCHASE, cardPurchase ?: emptyList())
                listOf(cashSalesDaily, cashPurchaseDaily, eTaxPurchaseDaily, cardSalesDaily, cardPurchaseDaily)
            }
            return TaxationResult(data)

        }

        fun ofMedicare(
            companyName: String,
            bizNo: String,
            nursingMap : Map<LocalDate, List<Long>>,
            medicalMap : Map<LocalDate, List<Long>>,
            disabledMap : Map<LocalDate, List<Long>>,
            smokingMap : Map<LocalDate, List<Long>>,
            welfareMap : Map<LocalDate, List<Long>>
        ): TaxationResult{
            val keys = nursingMap.keys + medicalMap.keys + disabledMap.keys + smokingMap.keys + welfareMap.keys
            val data = keys.associateWith { date ->
                val nursing = nursingMap[date]?.toDaily(MedicareType.NURSING, companyName, bizNo) ?: emptyList()
                val medical = medicalMap[date]?.toDaily(MedicareType.MEDICAL, companyName, bizNo) ?: emptyList()
                val disabled = disabledMap[date]?.toDaily(MedicareType.DISABLED, companyName, bizNo) ?: emptyList()
                val smoking = smokingMap[date]?.toDaily(MedicareType.SMOKING, companyName, bizNo) ?: emptyList()
                val welfare = welfareMap[date]?.toDaily(MedicareType.WELFARE, companyName, bizNo) ?: emptyList()
                listOf(TaxationDaily(SalesPurchaseCategory.MEDICARE_SALES, nursing + medical + disabled + smoking + welfare))
            }
            return TaxationResult(data)
        }

        private fun List<Long>.toDaily(
            type: MedicareType,
            companyName: String,
            bizNo: String
        ): List<TaxationDaily.TaxationDailyDetail> {
            return this.map {
                TaxationDaily.TaxationDailyDetail(
                    companyName = companyName,
                    bizNo = bizNo,
                    amount = it,
                    businessType = type.name
                )
            }
        }
    }
}