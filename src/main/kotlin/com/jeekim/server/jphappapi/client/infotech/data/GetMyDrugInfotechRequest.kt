package com.jeekim.server.jphappapi.client.infotech.data

import com.jeekim.server.jphappapi.data.GetMyDrugHistoriesRequest
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.UUID

data class GetMyDrugInfotechRequest (
    val reqCd: String = UUID.randomUUID().toString(),
    val affix: String,
    val appCd: String = "JPH_APP",
    val orgCd: String = "hira",
    val svcCd: String = "B0001",
    val loginMethod: String = "EASY",
    val step: String,
    val name: String,
    val mobileNo: String,
    val ssn1: String,
    val ssn2: String,
    val sdate: String,
    val edate: String,
){
    companion object{
        fun of(request: GetMyDrugHistoriesRequest): GetMyDrugInfotechRequest{
            val yearMonth = YearMonth.now()
            return GetMyDrugInfotechRequest(
                affix = request.phoneNumber,
                step = request.channel,
                name = request.name,
                mobileNo = request.phoneNumber,
                ssn1 = request.rrnFirst,
                ssn2 = request.rrnSecond,
                sdate = yearMonth.atDay(1).format(DateTimeFormatter.ofPattern("yyyyMMdd")),
                edate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")),
            )
        }
    }
}