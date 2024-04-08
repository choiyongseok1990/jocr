package com.jeekim.server.jocr.service

import com.jeekim.server.jocr.client.infotech.InfoTechAdapter
import com.jeekim.server.jocr.client.infotech.dto.TaxRequest
import com.jeekim.server.jocr.dto.operation.CredentialCommand
import com.jeekim.server.jocr.dto.operation.TaxType
import com.jeekim.server.jocr.dto.operation.TaxationResult
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.YearMonth

@Service
class TaxService(
    private val infoTechAdapter: InfoTechAdapter
) {
    fun fetch(command: CredentialCommand, yearMonth: YearMonth): Map<LocalDate, List<TaxationResult.TaxationDaily>> {
        // Tax fetching -> 순차적 으로
        val cashSales = infoTechAdapter.fetchCashSales(TaxRequest.of(command, yearMonth, TaxType.CASH_SALES))
        val cashPurchase = infoTechAdapter.fetchCashPurchase(TaxRequest.of(command, yearMonth, TaxType.CASH_PURCHASE))
        val eTaxPurchase = infoTechAdapter.fetchETaxPurchase(TaxRequest.of(command, yearMonth, TaxType.E_TAX_PURCHASE))
        val cardSales = infoTechAdapter.fetchCardSales(TaxRequest.of(command, yearMonth, TaxType.CARD_SALES))
        val cashSalesMap = cashSales.out.getDailyMap(command.businessNumber, command.pharmacyName)
        val cashPurchaseMap = cashPurchase.out.getDailyMap(command.businessNumber, command.pharmacyName)
        val eTaxPurchaseMap = eTaxPurchase.out.getDailyMap(command.businessNumber, command.pharmacyName)
        val cardSalesMap = cardSales.out.getDailyMap(command.businessNumber, command.pharmacyName)

        /**
         *  가장 마지막 으로 (etax 제외)
         */
        val cardPurchase = infoTechAdapter.fetchCardPurchase(TaxRequest.of(command, yearMonth, TaxType.CARD_PURCHASE))
        val cardPurchaseMap = cardPurchase.out.getDailyMap(command.businessNumber, command.pharmacyName)

        return TaxationResult.ofTax(
            cashSalesMap,
            cashPurchaseMap,
            eTaxPurchaseMap,
            cardSalesMap,
            cardPurchaseMap
        ).data
    }
}