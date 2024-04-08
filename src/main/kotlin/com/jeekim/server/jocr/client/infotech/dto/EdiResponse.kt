package com.jeekim.server.jocr.client.infotech.dto

import com.jeekim.server.jocr.utils.toLongOrZero

data class EdiResponse<T>(
    val errYn: String,
    val errMsg: String,
    val outE0001: EdiData<HealthInsurance>?,
    val outE0002: EdiData<NationalPension>?,
    val outE0003: EdiData<EmployeeInsurance>?,
){
    data class EdiData<T>(
        val errYn: String,
        val errMsg: String,
        val list: List<EdiList>,
    ){
        fun getEmployeeMap(): Map<String, T> {
            return list.groupBy { it.getName() }
                .entries.associate {
                    it.key to it.value.map { detail ->
                        detail as T
                    }.first()
                }
        }
    }

    interface EdiList {
        fun getName(): String

        fun isValid(): Boolean

        fun getRrn(): String

        fun getSalary(): Long
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

        fun getNationalPension(): Long {
            return this.privInsEmplPay.toLongOrZero()
        }

        override fun getSalary(): Long {
            return this.privInsStndIncmMthAmt.toLongOrZero()
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

        override fun getSalary(): Long {
            return this.feeMmAmt.toLongOrZero()
        }
        private fun isValidSalary(): Boolean {
            return this.feeMmAmt.toLongOrZero() > 0
        }

        fun getHealthInsurance(): Long {
            return this.tMonthCtrb.toLongOrZero()
        }
        fun getNursingInsurance(): Long {
            return this.nyTMonthCtrb.toLongOrZero()
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

        override fun getSalary(): Long {
            return this.mmAvgBosuPrc.toLongOrZero()
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

        fun getEmploymentInsurance(): Long {
            return this.sgGeunrojaBudamBhr.toLongOrZero()
        }

        fun getCompanyEmploymentInsurance(): Long {
            return this.sgSaeopjaBudamBhr.toLongOrZero()
        }

        fun getCompanyIndustrialExpense(): Long {
            return this.sjBhr.toLongOrZero()
        }

        fun getWorkStartDate(): String {
            return this.gyJagyeokChwideukDt
        }
    }
}