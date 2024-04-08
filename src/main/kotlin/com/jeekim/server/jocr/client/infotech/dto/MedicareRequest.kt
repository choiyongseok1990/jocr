package com.jeekim.server.jocr.client.infotech.dto

import com.jeekim.server.jocr.dto.operation.CredentialCommand
import com.jeekim.server.jocr.dto.operation.MedicareType
import com.jeekim.server.jocr.utils.CommonUtils
import java.time.YearMonth
import java.util.UUID

data class MedicareRequest(
    val reqCd: String,
    val affix: String,
    val appCd: String,
    val orgCd: String,
    val svcCd: String,
    val signCert: String,
    val signPri: String,
    val signPw: String,
    val fromDate: String,
    val toDate: String,
    val dateGb: String,
    val searchType: String,
    val detailYn: String,
){
    companion object{
        fun of(credential: CredentialCommand, type: MedicareType, yearMonth: YearMonth): MedicareRequest {
            return MedicareRequest(
                reqCd = UUID.randomUUID().toString(),
                affix = credential.crewId.toString(),
                appCd = CommonUtils.APP_CD,
                orgCd = CommonUtils.MEDICARE,
                svcCd = type.code,
                signCert = credential.cert.cert,
                signPri = credential.cert.key,
                signPw = credential.cert.password,
                fromDate = yearMonth.atDay(1).format(CommonUtils.NO_DASH_FORMAT),
                toDate = yearMonth.atEndOfMonth().format(CommonUtils.NO_DASH_FORMAT),
                dateGb = "01",
                searchType = "01",
                detailYn = "Y",
            )
        }
    }
}