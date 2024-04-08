package com.jeekim.server.jocr.client.infotech.dto

import com.jeekim.server.jocr.dto.operation.CredentialCommand
import com.jeekim.server.jocr.dto.operation.EdiType
import com.jeekim.server.jocr.dto.operation.MedicareType
import com.jeekim.server.jocr.utils.CommonUtils.APP_CD
import com.jeekim.server.jocr.utils.CommonUtils.DEFAULT_YEAR_MONTH_FORMAT
import com.jeekim.server.jocr.utils.CommonUtils.MEDICARE
import com.jeekim.server.jocr.utils.CommonUtils.NO_DASH_FORMAT
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
                appCd = APP_CD,
                orgCd = MEDICARE,
                svcCd = type.code,
                signCert = credential.cert.cert,
                signPri = credential.cert.key,
                signPw = credential.cert.password,
                fromDate = yearMonth.atDay(1).format(NO_DASH_FORMAT),
                toDate = yearMonth.atEndOfMonth().format(NO_DASH_FORMAT),
                dateGb = "01",
                searchType = "01",
                detailYn = "Y",
            )
        }
    }
}

data class EdiRequest(
    val reqCd: String,
    val affix: String,
    val appCd: String,
    val orgCd: String,
    val svcCd: String,
    val signCert: String,
    val signPri: String,
    val signPw: String,
    val mgmtNo: String,
    val certType: String = "2",
    val bizNo: String,
    val bizType: String = "1",
    val yyyyMM: String
){
    companion object{
        fun of(credential: CredentialCommand, type: EdiType, yearMonth: YearMonth): EdiRequest {
            return EdiRequest(
                reqCd = UUID.randomUUID().toString(),
                affix = credential.crewId.toString(),
                appCd = APP_CD,
                orgCd = MEDICARE,
                svcCd = type.svcCd,
                signCert = credential.cert.cert,
                signPri = credential.cert.key,
                signPw = credential.cert.password,
                mgmtNo = credential.businessNumber + "0",
                bizNo = credential.businessNumber,
                yyyyMM = yearMonth.format(DEFAULT_YEAR_MONTH_FORMAT),
            )
        }
    }
}