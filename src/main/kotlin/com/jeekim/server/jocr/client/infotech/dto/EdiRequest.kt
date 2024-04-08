package com.jeekim.server.jocr.client.infotech.dto

import com.jeekim.server.jocr.dto.operation.CredentialCommand
import com.jeekim.server.jocr.dto.operation.EdiType
import com.jeekim.server.jocr.utils.CommonUtils
import java.time.YearMonth
import java.util.UUID

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
                appCd = CommonUtils.APP_CD,
                orgCd = type.orgCd, // [건강, 국민] / [고용]
                svcCd = type.svcCd,
                signCert = credential.cert.cert,
                signPri = credential.cert.key,
                signPw = credential.cert.password,
                mgmtNo = credential.businessNumber + "0",
                bizNo = credential.businessNumber,
                yyyyMM = yearMonth.format(CommonUtils.DEFAULT_YEAR_MONTH_FORMAT),
            )
        }
    }
}