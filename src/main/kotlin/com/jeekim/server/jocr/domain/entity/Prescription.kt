package com.jeekim.server.jocr.domain.entity

import com.jeekim.server.jocr.domain.BaseTimeEntity
import com.jeekim.server.jocr.domain.converter.PrescriptionContentConverter
import com.jeekim.server.jocr.domain.enums.ServiceType
import com.jeekim.server.jocr.domain.model.PrescriptionContent
import java.time.LocalDate
import javax.persistence.Column
import javax.persistence.Convert
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "prescription")
data class Prescription (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    @Column(name = "user_id")
    val userId: String,

    @Column(name = "service_type")
    @Enumerated(EnumType.STRING)
    val serviceType: ServiceType,

    @Column(name = "patient_name")
    val patientName: String,

    @Column(name = "issuance_number")
    val issuanceNumber: String,

    @Column(name = "issuance_date")
    val issuanceDate: LocalDate,

    @Column(name = "patient_rrn_first")
    val patientRrnFirst: String,

    @Column(name = "hospital_name")
    val hospitalName: String,

    @Column(name = "hospital_nursing_number")
    val hospitalNursingNumber: String,

    @Convert(converter = PrescriptionContentConverter::class)
    @Column(name = "content", columnDefinition = "json")
    val content: List<PrescriptionContent>,

    @Column(name = "link")
    private var link: String? = null

    ): BaseTimeEntity(){
        fun updateLink(link: String){
            this.link = link
        }
    }