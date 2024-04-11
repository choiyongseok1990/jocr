package com.jeekim.server.jocr.client.kims.data

import com.jeekim.server.jocr.dto.prescription.PrescriptionRequest
import com.jeekim.server.jocr.domain.enums.KimsInputType

data class KimsRequest (
    val custID: String,
    val dataType: Int,
    val rxData: RxData
){
    data class RxData(
        val patientNo: String,
        val patientName: String,
        val prescriptions: List<RxPrescription>
    )
    data class RxPrescription(
        val hospNum: String,
        val hospName: String,
        val hospDate: String,
        val pharmaDate: String,
        val drugs: List<RxDrug>,
        val ocrImage: String
    )
    data class RxDrug(
        val rxType: Int,
        val drugCode: String,
        val drugName: String,
        val quantity: Double,
        val frequency: Int,
        val dayCount: Int
    )

    companion object {
      fun of(request: PrescriptionRequest, custID: String, baseUrl: String, bucketName: String): KimsRequest{
          val dataType = KimsInputType.OCR.ordinal
          val rrn = request.patientRrn
          val name = request.patientName
          val nursingInstNumber = request.nursingInstNumber
          val medicalInstName = request.medicalInstName
          val issuanceDate = request.issuanceDate
          val internalDrugs = request.internalPrescriptionContents.map { drug ->
              RxDrug(
                  rxType = 0,
                  drugCode = checkNotNull(drug.drugCode),
                  drugName = checkNotNull(drug.drugName),
                  quantity = drug.oneDose.toDouble(),
                  frequency = drug.dosingPerDay.toInt(),
                  dayCount = drug.totalDosingDays.toInt()
              )
          }
          val injectionDrugs = request.injectionPrescriptionContents.map { drug ->
              RxDrug(
                  rxType = 2,
                  drugCode = checkNotNull(drug.drugCode),
                  drugName = checkNotNull(drug.drugName),
                  quantity = drug.oneDose.toDouble(),
                  frequency = drug.dosingPerDay.toInt(),
                  dayCount = drug.totalDosingDays.toInt()
              )
          }
          return KimsRequest(
              custID = custID,
              dataType = dataType,
              rxData = RxData(
                  patientNo = rrn,
                  patientName = name,
                  prescriptions = listOf(
                      RxPrescription(
                          hospNum = nursingInstNumber,
                          hospName = medicalInstName,
                          hospDate = issuanceDate.toString(),
                          pharmaDate = issuanceDate.toString(),
                          drugs = internalDrugs + injectionDrugs,
                          ocrImage = "$baseUrl/$bucketName/${request.fileKey}"
                      )
                  )
              )
          )
      }
    }
}