package com.jeekim.server.jocr.client.infotech.dto

import com.jeekim.server.jocr.dto.operation.CredentialCommand
import com.jeekim.server.jocr.dto.operation.TaxType
import com.jeekim.server.jocr.utils.CommonUtils
import java.time.YearMonth
import java.util.UUID

data class TaxRequest(
    val reqCd: String,
    val affix: String,
    val appCd: String,
    val orgCd: String,
    val svcCd: String,
    val userId: String,
    val userPw: String,
    val bizNo: String,
    val supByr: String = "02",
    val taxGb: String = "01",
    val dtCd: String = "01",
    val loginMethod : String ="ID",
    val inqrDtStrt: String,
    val inqrDtEnd: String,
    val wrtDtStrt: String,
    val wrtDtEnd: String,
    val ddcYn: String = "all",
    val fromDate: String,
    val toDate: String,
){
    companion object{
        fun of(credential: CredentialCommand, yearMonth: YearMonth, taxType: TaxType): TaxRequest {
            val from = yearMonth.atDay(1).format(CommonUtils.NO_DASH_FORMAT)
            val to = yearMonth.atEndOfMonth().format(CommonUtils.NO_DASH_FORMAT)
            val id = if(taxType.isHomeTax()) credential.homeTax.id else credential.crefia.id
            val pw = if(taxType.isHomeTax()) credential.homeTax.password else credential.crefia.password
            return TaxRequest(
                reqCd = UUID.randomUUID().toString(),
                affix = credential.crewId.toString(),
                appCd = CommonUtils.APP_CD,
                orgCd = taxType.orgCd,
                svcCd = taxType.svcCd,
                userId = id,
                userPw = pw,
                bizNo = credential.businessNumber,
                inqrDtStrt = from,
                inqrDtEnd = to,
                wrtDtStrt = from,
                wrtDtEnd = to,
                fromDate = from,
                toDate = to,
            )
        }
    }
}