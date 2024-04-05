package com.jeekim.server.jocr.domain.enums

import java.util.Arrays

enum class PrescriptionCode(
    val code: String,
    val keyString: String,
    val abbreviation: String?,
    val needCheckNotEssential: Boolean
) {
    PRESCRIPTION_ID("JK-PID", "prescriptionId", "pid", false),
    ISSUANCE_NUMBER("MD-DN", "교부 번호", "in", false),
    ISSUANCE_DATE("MD-DLD", "교부 연월일", "id", false),
    PERIOD_OF_USE("MD-UP", "사용기간", null, false),
    PATIENT_NAME("MD-PN", "환자 성명", "pn", false),
    PATIENT_RRN("MD-PRN", "환자 주민등록번호", "pin", false),
    PATIENT_DOB("MD-PBD", "환자 생년월일", null, false),
    PATIENT_CATEGORY("MD-PC", "환자 구분", "pc", false),
    DISEASE_CODES("MD-DCC", "질병 코드", "dc", true),
    SELF_PAY_CODE("MD-SBS", "본인부담구분기호", "spc", true),
    MEDICAL_SPECIALTY("MD-MS", "진료과", null, false),
    DISPENSING_INST_NAME("MD-DIN", "조제 기관명", null, false),
    DISPENSING_PHARMACIST_NAME("MD-DPN", "조제 약사 성명", null, false),
    DISPENSING_PHARMACIST_PHONE_NUMBER("MD-DTN", "조제 기관 연락처", null, false),
    DISPENSING_DATE("MD-DPD", "조제 연월일", null, false),
    DOCTOR_NAME("MD-PSN", "처방의 성명", null, false),
    LICENSE_TYPE("MD-LT", "면허종별", null, false),
    LICENSE_NUMBER("MD-LN", "면허번호", "ln", false),
    NURSING_INST_NUMBER("MD-MIB", "요양기관번호", "nin", false),
    MEDICAL_INST_NAME("MD-MIM", "의료기관 명칭", "min", false),
    MEDICAL_INST_PHONE_NUMBER("MD-MIT", "의료기관 전화번호", null, false),
    MEDICAL_INST_FAX_NUMBER("MD-MIF", "의료기관 팩스번호", null, false),
    MEDICAL_INST_EMAIL("MD-MIE", "의료기관 Email 주소", null, false),
    MEDICAL_INST_ADDRESS("MD-MIA", "의료기관 주소", null, false),
    PRESCRIPTION_REF("MD-PR", "처방조제참고사항", "pr", true),
    PATIENT_EXPENSE("pe", "환자부담금", "pe", false),
    DISPENSING_EXPENSE("de", "조제료", "de", false),
    BILLING_EXPENSE("be", "공단청구액", "be", false),

    //테이블
    INTERNAL_PRESCRIPTION_TABLE("MD-PRS-MPB", "내복 처방", null, false),
    INJECTION_PRESCRIPTION_TABLE("MD-PRS-IPB", "주사제 처방", null, false),

    //내복약
    INTERNAL_DRUG_NAME("MD-PRS-MPMN", "(내복) 처방 의약품의 명칭", null, false),
    INTERNAL_ONE_DOSE("MD-PRS-MPPS", "(내복) 1회 투약량", null, false),
    INTERNAL_DOSING_PER_DAY("MD-PRS-MPPD", "(내복) 1일 투여횟수", null, false),
    INTERNAL_TOTAL_DOSING_DAYS("MD-PRS-MPTD", "(내복) 총 투약일수", null, false),
    INTERNAL_SELF_PAY_RATE_CODE("MD-PRS-MPRC", "(내복) 본인부담률구분코드", null, false),

    //주사제
    INJECTION_DRUG_NAME("MD-PRS-IPDN", "(주사) 처방 의약품의 명칭", null, false),
    INJECTION_ONE_DOSE("MD-PRS-IPPS", "(주사) 1회 투약량", null, false),
    INJECTION_DOSING_PER_DAY("MD-PRS-IPPD", "(주사) 1일 투여횟수", null, false),
    INJECTION_TOTAL_DOSING_DAYS("MD-PRS-IPTD", "(주사) 총 투약일수", null, false),
    INJECTION_SELF_PAY_RATE_CODE("MD-PRS-IPRC", "(주사) 본인부담률구분코드", null, false),

    NULL("null", "null", "null", false);

    fun isInjection(): Boolean{
        return this == INJECTION_PRESCRIPTION_TABLE
    }

    companion object {
        fun of(code: String): PrescriptionCode {
            return Arrays.stream(entries.toTypedArray())
                .filter { c: PrescriptionCode -> c.code == code }
                .findFirst()
                .orElse(NULL)
        }
    }
}