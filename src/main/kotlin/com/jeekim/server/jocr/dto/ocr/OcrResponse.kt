package com.jeekim.server.jocr.dto.ocr

import com.jeekim.server.jocr.dto.ocr.Bbox
import com.jeekim.server.jocr.dto.ocr.DiseaseCodes
import com.jeekim.server.jocr.dto.ocr.DoctorName
import com.jeekim.server.jocr.dto.ocr.IssuanceDate
import com.jeekim.server.jocr.dto.ocr.IssuanceNumber
import com.jeekim.server.jocr.dto.ocr.LicenseNumber
import com.jeekim.server.jocr.dto.ocr.MedicalInstName
import com.jeekim.server.jocr.dto.ocr.NursingInstNumber
import com.jeekim.server.jocr.dto.ocr.PatientCategory
import com.jeekim.server.jocr.dto.ocr.PatientName
import com.jeekim.server.jocr.dto.ocr.PatientRrn
import com.jeekim.server.jocr.dto.ocr.PrescriptionContent
import com.jeekim.server.jocr.dto.ocr.PrescriptionRef
import com.jeekim.server.jocr.dto.ocr.SelfPayCode

data class OcrResponse (
    val patientCategory: PatientCategory = PatientCategory(),
    val patientName: PatientName = PatientName(),
    val issuanceDate: IssuanceDate = IssuanceDate(),
    val issuanceNumber: IssuanceNumber = IssuanceNumber(),
    val patientRrn: PatientRrn = PatientRrn(),
    val selfPayCode: SelfPayCode = SelfPayCode(),
    val doctorName: DoctorName = DoctorName(),
    val medicalInstName: MedicalInstName = MedicalInstName(),
    val nursingInstNumber: NursingInstNumber = NursingInstNumber(),
    val licenseNumber: LicenseNumber = LicenseNumber(),
    val diseaseCodes: DiseaseCodes = DiseaseCodes(),
    val prescriptionRef: PrescriptionRef = PrescriptionRef(),
    var internalPrescriptionContents: List<PrescriptionContent> = emptyList(),
    var injectionPrescriptionContents: List<PrescriptionContent> = emptyList(),
    val extraPersonalInfoBboxList: MutableList<Bbox> = mutableListOf(),
    var fileKey: String? = "empty"
)