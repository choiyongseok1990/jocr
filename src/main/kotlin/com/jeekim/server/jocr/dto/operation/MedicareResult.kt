package com.jeekim.server.jocr.dto.operation

import java.time.LocalDate

data class MedicareResult (
    val data: Map<LocalDate, MedicareDaily>
){
    data class MedicareDaily(
        var total: Long,
        val details: MutableList<MedicareDailyDetail>
    ){
        data class MedicareDailyDetail(
            val amount: Long,
            val businessType: String
        )
        fun add(type: MedicareType, amounts: List<Long>){
            this.total += amounts.sum()
            this.details.addAll(amounts.map { MedicareDailyDetail(it, type.description) })
        }
    }

    companion object{
        fun of(
            nursingMap : Map<LocalDate, List<Long>>,
            medicalMap : Map<LocalDate, List<Long>>,
            disabledMap : Map<LocalDate, List<Long>>,
            smokingMap : Map<LocalDate, List<Long>>,
            welfareMap : Map<LocalDate, List<Long>>
        ): MedicareResult{
            val data = nursingMap.keys.union(medicalMap.keys)
                .union(disabledMap.keys)
                .union(smokingMap.keys)
                .union(welfareMap.keys)
                .associateWith { date ->
                    val daily = MedicareDaily(0, mutableListOf())
                    daily.add(MedicareType.NURSING, nursingMap[date] ?: emptyList())
                    daily.add(MedicareType.MEDICAL, medicalMap[date] ?: emptyList())
                    daily.add(MedicareType.DISABLED, disabledMap[date] ?: emptyList())
                    daily.add(MedicareType.SMOKING, smokingMap[date] ?: emptyList())
                    daily.add(MedicareType.WELFARE, welfareMap[date] ?: emptyList())
                    daily
                }
            return MedicareResult(data)
        }
    }
}