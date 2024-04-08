package com.jeekim.server.jocr.dto.operation

enum class TaxType(
    val svcCd: String,
    val orgCd: String,
) {
    CARD_SALES("B0011", "cardsales"),
    CARD_PURCHASE("Z4010", "hometax"),
    CASH_SALES("Z4001", "hometax"),
    CASH_PURCHASE("Z4002", "hometax"),
    E_TAX_PURCHASE("Z0005","hometax");

    fun isHomeTax(): Boolean {
        return orgCd == "hometax"
    }
}