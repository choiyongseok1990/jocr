package com.jeekim.server.jocr.client.infotech.dto

import com.jeekim.server.jocr.utils.CommonUtils.NO_DASH_FORMAT
import java.time.LocalDate

private fun String.toDate(): LocalDate = LocalDate.parse(this, NO_DASH_FORMAT)
private fun String.toLongOrZero(): Long = this.toLongOrNull() ?: 0

data class EdiResponse<T>(
    val errYn: String,
    val errMsg: String,
    val outE0001: EdiData<HealthInsurance>?,
    val outE0002: EdiData<NationalPension>?,
    val outE0003: EdiData<EmployeeInsurance>?,
)

data class EdiData<T>(
    val errYn: String,
    val errMsg: String,
    val list: List<EdiList>,
)
interface EdiList {
    fun getName(): String

    fun isValid(): Boolean

    fun getRrn(): String


}

data class NationalPension(
    val privInsName: String, // 근로자 명
    val privInsRrno: String, // 근로자 주민 번호
    val privInsStndIncmMthAmt: String, // 기준 소득 월액
    val privInsEmplPay: String, // 근로자 연금 보험료
    val gookprivInsPrsnPay: String, // 근로자 연금 보험료
): EdiList{

    override fun getRrn(): String {
        return privInsRrno
    }

    override fun getName(): String {
        return privInsName
    }

    override fun isValid(): Boolean {
        return isValidSalary()
    }
    private fun isValidSalary(): Boolean {
        return this.privInsStndIncmMthAmt.toLongOrZero() > 0
    }

    fun merge(other: NationalPension): NationalPension {
        if(this.getRrn() != other.getRrn()){
            return this
        }
        return NationalPension(
            privInsName = this.privInsName,
            privInsRrno = this.privInsRrno,
            privInsStndIncmMthAmt = maxOf(this.privInsStndIncmMthAmt.toLongOrZero(), other.privInsStndIncmMthAmt.toLongOrZero()).toString(),
            privInsEmplPay = (this.privInsEmplPay.toLongOrZero() + other.privInsEmplPay.toLongOrZero()).toString(),
            gookprivInsPrsnPay = (this.gookprivInsPrsnPay.toLongOrZero() + other.gookprivInsPrsnPay.toLongOrZero()).toString(),
        )
    }
}

data class HealthInsurance(
    val korNm: String, // 근로자 명
    val juminNo: String, // 근로자 주민 번호
    val feeMmAmt: String, // 보수 월액
    val tMonthCtrb: String, // 건강 고지 보험료
    val nyTMonthCtrb: String, // 요양 고지 보험료
): EdiList{

    override fun getRrn(): String {
        return juminNo
    }
    override fun getName(): String {
        return korNm
    }
    override fun isValid(): Boolean {
        return isValidSalary()
    }
    private fun isValidSalary(): Boolean {
        return this.feeMmAmt.toLongOrZero() > 0
    }

    fun merge(other: HealthInsurance): HealthInsurance {
        if(this.getRrn() != other.getRrn()){
            return this
        }
        return HealthInsurance(
            korNm = this.korNm,
            juminNo = this.juminNo,
            feeMmAmt = maxOf(this.feeMmAmt.toLongOrZero(), other.feeMmAmt.toLongOrZero()).toString(),
            tMonthCtrb = (this.tMonthCtrb.toLongOrZero() + other.tMonthCtrb.toLongOrZero()).toString(),
            nyTMonthCtrb = (this.nyTMonthCtrb.toLongOrZero() + other.nyTMonthCtrb.toLongOrZero()).toString(),
        )
    }
}

data class EmployeeInsurance(
    val daepyojaNm: String, // 대표자 명
    val geunrojaNm: String, // 근로자 명
    val gyJagyeokChwideukDt: String, // 고용 시작일
    val sjJagyeokChwideukDt: String, // 산재 시작일
    val sjJagyeok_sangsilDt: String, // 산재 퇴직일
    val gyJagyeokSangsilDt: String, // 고용 퇴직일
    val geunrojaFgNm: String, // 근로자 구분명 (일반, 일용 등)
    val geunrojaRgno: String, // 근로자 주민 번호
    val mmAvgBosuPrc: String, // 월평균 보수 월액
    val sgGeunrojaBudamBhr: String, // (실급) 근로자 보험료
    val sgGeJiwonPrc: String, // (실급) 근로자 지원금
    val sgSaeopjaBudamBhr: String, // (실급) 사업주 보험료
    val gajnBhr: String, // (고직) 사업주 보험료
    val sgSeJiwonPrc: String, // (실급) 사업주 지원금
    val gajnJiwonPrc: String, // (고직) 사업주 지원금
    val sjBhr: String, // (산재) 사업주 보험료
): EdiList{

    override fun getRrn(): String {
        return geunrojaRgno
    }
    override fun getName(): String {
        return geunrojaNm
    }
    override fun isValid(): Boolean {
        // 조건 1. 대표자명과 근로자명이 같지 않아야 한다.
        // 조건 2. 산재 퇴직일, 고용 퇴직일이 없어야 한다.
        // 조건 3. 근로자 구분명이 일반이어야 한다.
        // 조건 4. 월평균 보수 월액이 0보다 커야 한다.
        return isNotCeo() && isWorking() && isRegular() && isValidSalary()
    }
    private fun isNotCeo(): Boolean {
        return this.geunrojaNm != this.daepyojaNm
    }
    private fun isWorking(): Boolean {
        return this.sjJagyeok_sangsilDt.isEmpty() && this.gyJagyeokSangsilDt.isEmpty()
    }
    private fun isRegular(): Boolean {
        return this.geunrojaFgNm == "일반"
    }
    private fun isValidSalary(): Boolean {
        return this.mmAvgBosuPrc.toLongOrZero() > 0
    }

    fun merge(other: EmployeeInsurance): EmployeeInsurance {
        if (this.getRrn() != other.getRrn()) {
            return this
        }
        return EmployeeInsurance(
            daepyojaNm = this.daepyojaNm,
            geunrojaNm = this.geunrojaNm,
            gyJagyeokChwideukDt = this.gyJagyeokChwideukDt,
            sjJagyeokChwideukDt = this.sjJagyeokChwideukDt,
            sjJagyeok_sangsilDt = this.sjJagyeok_sangsilDt,
            gyJagyeokSangsilDt = this.gyJagyeokSangsilDt,
            geunrojaFgNm = this.geunrojaFgNm,
            geunrojaRgno = this.geunrojaRgno,
            mmAvgBosuPrc = maxOf(this.mmAvgBosuPrc.toLongOrZero(), other.mmAvgBosuPrc.toLongOrZero()).toString(),
            sgGeunrojaBudamBhr = (this.sgGeunrojaBudamBhr.toLongOrZero() + other.sgGeunrojaBudamBhr.toLongOrZero()).toString(),
            sgGeJiwonPrc = (this.sgGeJiwonPrc.toLongOrZero() + other.sgGeJiwonPrc.toLongOrZero()).toString(),
            sgSaeopjaBudamBhr = (this.sgSaeopjaBudamBhr.toLongOrZero() + other.sgSaeopjaBudamBhr.toLongOrZero()).toString(),
            gajnBhr = (this.gajnBhr.toLongOrZero() + other.gajnBhr.toLongOrZero()).toString(),
            sgSeJiwonPrc = (this.sgSeJiwonPrc.toLongOrZero() + other.sgSeJiwonPrc.toLongOrZero()).toString(),
            gajnJiwonPrc = (this.gajnJiwonPrc.toLongOrZero() + other.gajnJiwonPrc.toLongOrZero()).toString(),
            sjBhr = (this.sjBhr.toLongOrZero() + other.sjBhr.toLongOrZero()).toString(),
        )
    }
}

data class MedicareResponse<T>(
    val errYn: String,
    val errMsg: String,
    val outF0011: MedicareData<Nursing>?,
    val outF0012: MedicareData<Medical>?,
    val outF0013: MedicareData<Disabled>?,
    val outF0014: MedicareData<Smoking>?,
    val outF0015: MedicareData<Welfare>?,
)

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

