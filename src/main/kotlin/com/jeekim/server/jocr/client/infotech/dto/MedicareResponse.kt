package com.jeekim.server.jocr.client.infotech.dto

import com.jeekim.server.jocr.utils.toDate
import com.jeekim.server.jocr.utils.toLongOrZero
import java.time.LocalDate

data class MedicareResponse<T>(
    val errYn: String,
    val errMsg: String,
    val outF0011: MedicareData<Nursing>?,
    val outF0012: MedicareData<Medical>?,
    val outF0013: MedicareData<Disabled>?,
    val outF0014: MedicareData<Smoking>?,
    val outF0015: MedicareData<Welfare>?,
){
    data class MedicareData<T>(
        val errYn: String,
        val errMsg: String,
        val list: List<MedicareList>,
    ){
        fun getDailyMap(): Map<LocalDate, List<Long>> = list.groupBy(MedicareList::getDate)
            .entries.associate {
                it.key to it.value.filterNot { list -> list.isZero()}.map { list -> list.getAmount() }
            }
    }

    interface MedicareList {
        fun getDate(): LocalDate
        fun getAmount(): Long

        fun isZero(): Boolean = getAmount() == 0L
    }

    data class Nursing(
        val payDt: String, // 지급 일자
        val remitAmt: String, // 실 지급액
        val detailObj: DetailObj, // 원천 징수 세액 세액계
    ): MedicareList{
        data class DetailObj(
            val taxSum: String,
        )

        override fun getDate(): LocalDate = payDt.toDate()
        override fun getAmount(): Long = remitAmt.toLongOrZero()
    }

    data class Medical(
        val payDt: String, // 지급 일자
        val realAmt: String, // 지급 금액
    ): MedicareList{
        override fun getDate(): LocalDate = payDt.toDate()
        override fun getAmount(): Long = realAmt.toLongOrZero()
    }
    data class Disabled(
        val payDt: String, // 지급 일자
        val payAmt: String, // 지급 금액
    ): MedicareList{
        override fun getDate(): LocalDate = payDt.toDate()
        override fun getAmount(): Long = payAmt.toLongOrZero()
    }


    data class Smoking(
        val vcPayDt: String, // 지급 일자
        val dPayDcdNba: String, // 지급 금액 (공단 지원금)
        val dPayDcdNtrs: String, // 지급 금액 (국고 지원금)
    ): MedicareList{
        override fun getDate(): LocalDate = vcPayDt.toDate()
        override fun getAmount(): Long = dPayDcdNba.toLongOrZero() + dPayDcdNtrs.toLongOrZero()
    }

    data class Welfare(
        val payDt: String,
        val realAmt: String,
    ): MedicareList{
        override fun getDate(): LocalDate = payDt.toDate()
        override fun getAmount(): Long = realAmt.toLong()
    }

}