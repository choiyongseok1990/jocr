package com.jeekim.server.jocr.client.infotech.dto

import com.jeekim.server.jocr.dto.operation.TaxationResult
import com.jeekim.server.jocr.utils.toDate
import com.jeekim.server.jocr.utils.toLongOrZero
import java.time.LocalDate

data class TaxResponse<T> (
    val resCd: String,
    val resMsg: String,
    val out: T
){
    interface TaxOut {
        fun getDailyMap(bizNo:String, companyName: String): Map<LocalDate, List<TaxDetail>>
    }
    data class TaxDetail(
        val companyName: String,
        val businessNumber: String,
        val amount: Long,
        val businessType: String?
    ){
        fun toDaily(): TaxationResult.TaxationDaily.TaxationDailyDetail {
            return TaxationResult.TaxationDaily.TaxationDailyDetail(
                companyName = companyName,
                bizNo = businessNumber,
                amount = amount,
                businessType = businessType
            )
        }
    }


    data class CashSales(
        val errYn: String,
        val errMsg: String,
        val totTrsAmt: String,
        val list: List<Detail>
    ): TaxOut {
        data class Detail(
            val trDt: String,
            val totAmt: String
        )

        override fun getDailyMap(bizNo:String, companyName: String): Map<LocalDate, List<TaxDetail>>{
            return list.groupBy { it.trDt }
                .entries.associate {
                    it.key.toDate() to it.value.map { detail ->
                        TaxDetail(
                            companyName = companyName,
                            businessNumber = bizNo,
                            amount = detail.totAmt.toLongOrZero(),
                            businessType = null
                        )
                    }
                }
        }
    }

    data class CashPurchase(
        val errYn: String,
        val errMsg: String,
        val totTrsAmt: String,
        val list: List<Detail>
    ): TaxOut {
        data class Detail(
            val trDt: String,
            val storeNo: String, // 사업자 번호
            val useStore: String, // 매장명
            val totAmt: String, // 총액
            val tfbNm: String, // 업태
        )

        override fun getDailyMap(bizNo:String, companyName: String): Map<LocalDate, List<TaxDetail>>{
            return list.groupBy { it.trDt }
                .entries.associate {
                    it.key.toDate() to it.value.map { detail ->
                        TaxDetail(
                            companyName = detail.useStore,
                            businessNumber = takeIf { detail.storeNo.isEmpty() }?.let { detail.useStore } ?: detail.storeNo,
                            amount = detail.totAmt.toLongOrZero(),
                            businessType = detail.tfbNm
                        )
                    }
                }
        }
    }

    data class ETaxPurchase(
        val errYn: String,
        val errMsg: String,
        val tSumAmt: String,
        val list: List<Detail>,
    ): TaxOut {
        data class Detail(
            val bizNo: String,
            val bizNm: String,
            val sumAmt: String,
        )

        override fun getDailyMap(bizNo:String, companyName: String): Map<LocalDate, List<TaxDetail>>{
            return list.groupBy { it.bizNo }
                .entries.associate {
                    it.key.toDate() to it.value.map { detail ->
                        TaxDetail(
                            companyName = detail.bizNm,
                            businessNumber = detail.bizNo,
                            amount = detail.sumAmt.toLongOrZero(),
                            businessType = null
                        )
                    }
                }
        }
    }

    data class CardSales(
        val errYn: String,
        val errMsg: String,
        val outB0011: Out,
    ): TaxOut {
        data class Out(
            val listSum: List<Sum>,
            val listDetail: List<Detail>,
        ){
            data class Sum(
                val trDt: String, // 날짜
                val trSumAmt: String, // 총액
            )
            data class Detail(
                val trDt: String, // 날짜
                val trDiv: String, // 승인/취소
                val cardCorp1: String, // 카드사,
                val apprAmt: String, // 금액
            )
        }
        override fun getDailyMap(bizNo:String, companyName: String): Map<LocalDate, List<TaxDetail>>{
            return outB0011.listDetail.groupBy { it.trDt }
                .entries.associate {
                    it.key.toDate() to it.value.map { detail ->
                        TaxDetail(
                            companyName = detail.cardCorp1,
                            businessNumber = detail.cardCorp1,
                            amount = detail.apprAmt.toLongOrZero(),
                            businessType = null
                        )
                    }
                }
        }
    }

    data class CardPurchase(
        val errYn: String,
        val errMsg: String,
        val list: List<Detail>,
    ): TaxOut {
        data class Detail(
            val aprvDt: String,
            val mrntTxprNm: String,
            val mrntTxprDscmNoEncCntn: String, // 사업자번호
            val totaTrsAmt: String,
            val bcNm : String,
        )

        override fun getDailyMap(bizNo:String, companyName: String): Map<LocalDate, List<TaxDetail>>{
            return list.groupBy { it.aprvDt }
                .entries.associate {
                    it.key.toDate() to it.value.map { detail ->
                        TaxDetail(
                            companyName = detail.mrntTxprNm,
                            businessNumber = detail.mrntTxprDscmNoEncCntn.replace("-", ""),
                            amount = detail.totaTrsAmt.toLongOrZero(),
                            businessType = detail.bcNm
                        )
                    }
                }
        }

    }

}